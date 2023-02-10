/*
 * Ident: @(#)instr_01304m1.java generated from: @(#)instr_01304m.jmpp
 *
 * Copyright (c) 2013,  Oracle and/or its affiliates. All rights reserved.
 */

package javasoft.sqe.tests.vm.classfmt.ins.instr_013.instr_01304m1;

import java.io.PrintStream;
import java.lang.reflect.*;

public class instr_01304m1 {

    public static int run(String argv[], PrintStream out) {

    if (runPositive("instr_01304m10p", argv, out) != 0/*STATUS_PASSED*/)
        return 2/*STATUS_FAILED*/;
    if (instantiateNegative("instr_01304m11n", VerifyError.class, argv, out) != 0/*STATUS_PASSED*/)
        return 2/*STATUS_FAILED*/;

    return 0/*STATUS_PASSED*/;
    }

    public static int runPositive(String testName, String argv[], PrintStream out) {

    Class goodClass = null;
    try {
        goodClass = Class.forName("javasoft.sqe.tests.vm.classfmt.ins.instr_013.instr_01304m1." + testName);
    } catch (ThreadDeath e) {
        throw e;
    } catch (Throwable e) {
        out.println("Failed with loading exception: " + e);
        return 2/*STATUS_FAILED*/;
    }

    Class[] argTypes = {String[].class, PrintStream.class};
    Method runMethod = null;
    try {
        runMethod = goodClass.getDeclaredMethod("run", argTypes);
    } catch (SecurityException e) {
        out.println("SecurityException on looking for run() method in the test class: " + e);
        return 0/*STATUS_PASSED*/;
    } catch (NoSuchMethodException e) {
        out.println("run() not found in the test class " + testName);
        return 2/*STATUS_FAILED*/;
    } catch (ThreadDeath e) {
        throw e;
    } catch (Throwable e) {
        out.println("Unexpected exception on goodClass.getDeclaredMethod() invocation: " + e);
        return 2/*STATUS_FAILED*/;
    }

    Object[] args={argv, out};
    Integer retStatus = new Integer(2); // STATUS_FAILED
    try {
        retStatus = (Integer)runMethod.invoke(null, args);
    } catch (NullPointerException e) {
        out.println("run() must be static in the test class " + testName);
        return 2/*STATUS_FAILED*/;
    } catch (IllegalAccessException e) {
        out.println("run() is not accessible in the test class " + testName);
        return 2/*STATUS_FAILED*/;
    } catch (InvocationTargetException e) {
        Throwable ee = e.getTargetException();
        out.println("Failed with runtime exception: " + ee);
        return 2/*STATUS_FAILED*/;
    } catch (ThreadDeath e) {
        throw e;
    } catch (Throwable e) {
        out.println("Unexpected exception on runMethod.invoke() invocation: " + e);
        return 2/*STATUS_FAILED*/;
    }

    return retStatus.intValue();
    }

    public static int instantiateNegative(String testName, Class expectedException, String argv[], PrintStream out) {

        Class badClass = null;
        try {
            badClass = Class.forName("javasoft.sqe.tests.vm.classfmt.ins.instr_013.instr_01304m1." + testName);
            try {
                Object obj = badClass.newInstance();
            } catch (ThreadDeath e) {
                throw e;
            } catch (Throwable e) {
                if (expectedException.isInstance(e)) {
                    out.println("Passed with runtime exception: " + e);
                    return 0/*STATUS_PASSED*/;
                } else {
                    out.println("Failed with unexpected runtime exception: " + e);
                    return 2/*STATUS_FAILED*/;
                }
            }
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable e) {
            if (expectedException.isInstance(e)) {
                out.println("Passed with loading exception: " + e);
                return 0/*STATUS_PASSED*/;
            } else {
                out.println("Failed with unexpected loading exception: " + e);
                return 2/*STATUS_FAILED*/;
            }
        }
        out.println("Failed to reject invalid class " + testName);
        return 2/*STATUS_FAILED*/;
    }

    public static void main(String args[]) {
        System.exit(run(args, System.out) + 95/*STATUS_TEMP*/);
    }

}
