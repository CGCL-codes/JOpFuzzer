package org.simonis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.sun.tools.attach.VirtualMachine;

public class DefineClass {

    private static Instrumentation instrumentation;

    public void getID(CountDownLatch stop) {
        String id = "AAAAAAAA";
        System.out.println(id);
        try {
            stop.await();
            //Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(id);
        return;
    }

    private static class MyThread extends Thread {
        private DefineClass dc;
        private CountDownLatch stop;

        public MyThread(DefineClass dc, CountDownLatch stop) {
            this.dc = dc;
            this.stop = stop;
        }

        public void run() {
            dc.getID(stop);
        }
    }

    static private class MyClassLoader extends ClassLoader {
        public Class<?> myDefineClass(String name, byte[] b, int off, int len) throws ClassFormatError {
            return defineClass(name, b, off, len, null);
        }
    }

    public static void agentmain(String args, Instrumentation inst) {
        System.out.println("Loading Java Agent.");
        instrumentation = inst;
    }


    private static void loadInstrumentationAgent(String myName, byte[] buf) throws Exception {
        // Create agent jar file on the fly
        Manifest m = new Manifest();
        m.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        m.getMainAttributes().put(new Attributes.Name("Agent-Class"), myName);
        m.getMainAttributes().put(new Attributes.Name("Can-Redefine-Classes"), "true");
        File jarFile = File.createTempFile("agent", ".jar");
        jarFile.deleteOnExit();
        JarOutputStream jar = new JarOutputStream(new FileOutputStream(jarFile), m);
        jar.putNextEntry(new JarEntry(myName.replace('.', '/') + ".class"));
        jar.write(buf);
        jar.close();
        //System.out.println(jarFile);
        String self = ManagementFactory.getRuntimeMXBean().getName();
        String pid = self.substring(0, self.indexOf('@'));
        System.out.println("Our pid is = " + pid);
        VirtualMachine vm = VirtualMachine.attach(pid);
        //System.out.println(jarFile.getAbsolutePath());
        vm.loadAgent(jarFile.getAbsolutePath());
    }

    private static byte[] getBytecodes(String myName) throws Exception {
        InputStream is = DefineClass.class.getResourceAsStream(myName + ".class");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int len;
        while ((len = is.read(buf)) != -1) baos.write(buf, 0, len);
        buf = baos.toByteArray();
        System.out.println("sizeof(" + myName + ".class) == " + buf.length);
        return buf;
    }

    private static int getStringIndex(String needle, byte[] buf) {
        return getStringIndex(needle, buf, 0);
    }

    private static int getStringIndex(String needle, byte[] buf, int offset) {
        outer:
        for (int i = offset; i < buf.length - offset - needle.length(); i++) {
            for (int j = 0; j < needle.length(); j++) {
                if (buf[i + j] != (byte)needle.charAt(j)) continue outer;
            }
            return i;
        }
        return 0;
    }

    private static void replaceString(byte[] buf, String name, int index) {
        for (int i = index; i < index + name.length(); i++) {
            buf[i] = (byte)name.charAt(i - index);
        }
    }

    private static MBeanServer mbserver = ManagementFactory.getPlatformMBeanServer();

    private static int getClassStats(String pattern) {
        try {
            ObjectName diagCmd = new ObjectName("com.sun.management:type=DiagnosticCommand");

            String result = (String)mbserver.invoke(diagCmd , "gcClassStats" , new Object[] { null }, new String[] {String[].class.getName()});
            Scanner s = new Scanner(result);
            if (s.hasNextLine()) {
                System.out.println(s.nextLine());
            }
            int count = 0;
            while (s.hasNextLine()) {
                String l = s.nextLine();
                if (l.endsWith(pattern)) {
                    count++;
                    System.out.println(l);
                }
            }
            s.close();
            return count;
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getCause());
            e.getCause().printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        String myName = DefineClass.class.getName();
        byte[] buf = getBytecodes(myName.substring(myName.lastIndexOf(".") + 1));

        if (args.length == 0 || "defineClass".equals(args[0])) {
            MyClassLoader cl = new MyClassLoader();
            for (int i = 0; i < (args.length > 1 ? Integer.parseInt(args[1]) : 10); i++) {
                try {
                    @SuppressWarnings("unchecked")
                    Class<DefineClass> dc = (Class<DefineClass>) cl.myDefineClass(myName, buf, 0, buf.length);
                    System.out.println(dc);
                }
                catch (LinkageError jle) {
                    // Can only define once!
                    if (i == 0) throw new Exception("Should succeed the first time.");
                }
            }
            int count = getClassStats("org.simonis.DefineClass");
            // We expect to have two instances of DefineClass here: the initial version in which we are
            // executing and one another version which was loaded into our own classloader 'MyClassLoader'.
            // All the subsequent attempts to reload DefineClass into our 'MyClassLoader' should have failed.
            System.out.println("Should have 2 DefineClass instances and we have: " + count);
            System.gc();
            System.out.println("System.gc()");
            count = getClassStats("org.simonis.DefineClass");
            // At least after System.gc() the failed loading attempts should leave no instances around!
            System.out.println("Should have 2 DefineClass instances and we have: " + count);
        }
        else if ("defineSystemClass".equals(args[0])) {
            MyClassLoader cl = new MyClassLoader();
            int index = getStringIndex("org/simonis/DefineClass", buf);
            replaceString(buf, "java/simoni/DefineClass", index);
            while ((index = getStringIndex("Lorg/simonis/DefineClass;", buf, index + 1)) != 0) {
                replaceString(buf, "Ljava/simoni/DefineClass;", index);
            }
            index = getStringIndex("org.simonis.DefineClass", buf);
            replaceString(buf, "java.simoni.DefineClass", index);

            for (int i = 0; i < (args.length > 1 ? Integer.parseInt(args[1]) : 10); i++) {
                try {
                    @SuppressWarnings("unchecked")
                    Class<DefineClass> dc = (Class<DefineClass>) cl.myDefineClass(null, buf, 0, buf.length);
                    System.out.println(dc);
                }
                catch (java.lang.SecurityException jlse) {
                    // Expected
                }
            }
            int count = getClassStats("org.simonis.DefineClass");
            // We expect to have two instances of DefineClass here: the initial version in which we are
            // executing and one another version which was loaded into our own classloader 'MyClassLoader'.
            // All the subsequent attempts to reload DefineClass into our 'MyClassLoader' should have failed.
            System.out.println("Should have 1 DefineClass instances and we have: " + count);
            System.gc();
            System.out.println("System.gc()");
            count = getClassStats("org.simonis.DefineClass");
            // At least after System.gc() the failed loading attempts should leave no instances around!
            System.out.println("Should have 1 DefineClass instances and we have: " + count);
        }
        else if ("redefineClass".equals(args[0])) {
            loadInstrumentationAgent(myName, buf);
            int index = getStringIndex("AAAAAAAA", buf);

            CountDownLatch stop = new CountDownLatch(1);
            int length = args.length > 1 ? Integer.parseInt(args[1]) : 10;
            Thread[] threads = new Thread[length];
            for (int i = 0; i < length; i++) {
                buf[index] = (byte) ('A' + i + 1); // Change string constant in getID() which is legal in redefinition
                instrumentation.redefineClasses(new ClassDefinition(DefineClass.class, buf));
                DefineClass dc = DefineClass.class.newInstance();
                (threads[i] = new MyThread(dc, stop)).start();
                Thread.sleep(100); // Give the new thread a chance to start
            }
            int count = getClassStats("org.simonis.DefineClass");
            // We expect to have one instance for each redefinition because they are all kept alive by an activation
            // plus the intial version which is kept active by this main method.
            System.out.println("Should have " + (length + 1) + " DefineClass instances and we have: " + count);
            stop.countDown(); // let all threads leave the DefineClass.getID() activation..
            // ..and wait until really all of them returned from DefineClass.getID()
            for (int i = 0; i < length; i++) {
                threads[i].join();
            }
            System.gc();
            System.out.println("System.gc()");
            count = getClassStats("org.simonis.DefineClass");
            // After System.gc() we expect to remain with two instances: one is the initial version which is
            // kept alive by this main methidf and another one which is the latest redefined version.
            System.out.println("Should have 2 DefineClass instances and we have: " + count);
            //System.in.read();
        }
        else if ("redefineClassWithError".equals(args[0])) {
            loadInstrumentationAgent(myName, buf);
            int index = getStringIndex("getID", buf);

            for (int i = 0; i < (args.length > 1 ? Integer.parseInt(args[1]) : 10); i++) {
                buf[index] = (byte) 'X'; // Change getID() to XetID() which is illegal in redefinition
                try {
                    instrumentation.redefineClasses(new ClassDefinition(DefineClass.class, buf));
                }
                catch (UnsupportedOperationException uoe) {
                    // Expected
                }
            }
            int count = getClassStats("org.simonis.DefineClass");
            // We expect just a single DefineClass instance because failed redefinitions should
            // leave no garbage around.
            System.out.println("Should have 1 DefineClass instances and we have: " + count);
            System.gc();
            System.out.println("System.gc()");
            count = getClassStats("org.simonis.DefineClass");
            // At least after a System.gc() we should definitely stay with a single instance!
            System.out.println("Should have 1 DefineClass instances and we have: " + count);
            //System.in.read();
        }
    }
}
/*

SystemDictionary::parseStream() is called by VM_RedefineClasses::load_new_class_versions.
 It parses the new class from the ClassFileParser stream but doesn't add it to the SystemDictionary.

SystemDictionary::resolve_from_stream() is called from JVM_DefineClass/jni_DefineClass.
 It does the same thing, but also adds the new class to the SystemDictionary in the case of success
 (by calling SystemDictionary::define_instance_class(instanceKlassHandle k))
*/