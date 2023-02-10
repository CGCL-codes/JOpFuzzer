package compiler.jvmci.compilerToVM;

import jdk.internal.jvmci.code.InstalledCode;
import jdk.internal.jvmci.code.InvalidInstalledCodeException;
import jdk.internal.jvmci.hotspot.CompilerToVMHelper;
import jdk.test.lib.Asserts;
import jdk.test.lib.Pair;
import sun.hotspot.code.NMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @test
 * @bug 8136421
 * @library /testlibrary /../../test/lib /
 * @compile ../common/CompilerToVMHelper.java
 * @build sun.hotspot.WhiteBox
 *        compiler.jvmci.compilerToVM.ExecuteInstalledCodeTest
 * @run main ClassFileInstaller sun.hotspot.WhiteBox
 *                              sun.hotspot.WhiteBox$WhiteBoxPermission
 *                              jdk.internal.jvmci.hotspot.CompilerToVMHelper
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI
 *      -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:.
 *      compiler.jvmci.compilerToVM.ExecuteInstalledCodeTest
 */

public class ExecuteInstalledCodeTest {
    private static final Map<Class<?>, Object> RECEIVERS = new HashMap<>();
    static {
        RECEIVERS.put(CompileCodeTestCase.DummyEx.class, new CompileCodeTestCase.DummyEx());
        RECEIVERS.put(CompileCodeTestCase.Dummy.class, new CompileCodeTestCase.Dummy() {
            @Override
            public CompileCodeTestCase.Interface interfaceMethod() {
                throw new AbstractMethodError();
            }

            @Override
            Object abstractMethod(double d) {
                throw new AbstractMethodError();
            }
        });
        RECEIVERS.put(CompileCodeTestCase.Interface.class,
                new CompileCodeTestCase.Interface() {
            @Override
            public CompileCodeTestCase.Interface interfaceMethod() {
                throw new AbstractMethodError();
            }
        });
    }

    public static void main(String[] args) {
        ExecuteInstalledCodeTest test = new ExecuteInstalledCodeTest();
        List<CompileCodeTestCase> testCases = new ArrayList<>();
        testCases.addAll(CompileCodeTestCase.generate(/* bci = */ -1));
        testCases.stream()
                // ignore <init> due to 8137180
                //.filter(e -> !(e.executable instanceof Constructor))
                //.filter(e -> !(e.executable instanceof Constructor && Modifier.isAbstract(e.executable.getDeclaringClass().getModifiers())))
                .forEach(test::checkSanity);
        test.checkNull();
    }

    private void checkNull() {

    }

    private void checkSanity(CompileCodeTestCase testCase) {
        System.out.println(testCase);
        // to have a clean state
        testCase.deoptimize();
        Pair<Object, ? extends Throwable> reflectionResult;
        Object[] args = getArguments(testCase.executable);
        reflectionResult = invoke(testCase, args);
        NMethod nMethod = testCase.compile();
        if (nMethod == null) {
            throw new Error(testCase + " : nmethod is null");
        }
        InstalledCode installedCode = new InstalledCode(
                testCase.executable.getName());
        installedCode.setAddress(nMethod.address);
        Object result = null;
        Throwable expectedException = reflectionResult.second;
        boolean gotException = true;
        try {
            args = addReceiver(testCase, args);
            result = CompilerToVMHelper.executeInstalledCode(
                    args, installedCode);
            gotException = false;
        } catch (InvalidInstalledCodeException e) {
            throw new AssertionError(
                    testCase + " : unexpected InvalidInstalledCodeException", e);
        } catch (Throwable t) {
            if (expectedException == null) {
                throw new AssertionError(testCase
                        + " : got unexpected execption : " + t.getMessage(), t);
            }

            if (expectedException.getClass() != t.getClass()) {
                System.err.println("exception from CompilerToVM:");
                t.printStackTrace();
                System.err.println("exception from reflection:");
                expectedException.printStackTrace();
                throw new AssertionError(String.format(
                        "%s : got unexpected different exceptions : %s != %s",
                        testCase, expectedException.getClass(), t.getClass()));
            }
        }
        Asserts.assertEQ(reflectionResult.first, result, testCase
                + " : different return value");
        if (!gotException) {
            Asserts.assertNull(expectedException, testCase
                    + " : expected exception hasn't been thrown");
        }
    }

    private Object[] addReceiver(CompileCodeTestCase testCase, Object[] args) {
        if (!Modifier.isStatic(testCase.executable.getModifiers())) {
            // add instance as 0th arg
            Object[] newArgs = new Object[args.length + 1];
            if (testCase.executable instanceof Constructor) {
                newArgs[0] = new Object();
            } else  {
                newArgs[0] = getReciever(testCase);
            }
            System.arraycopy(args, 0, newArgs, 1, args.length);
            args = newArgs;
        }
        return args;
    }

    private Object getReciever(CompileCodeTestCase testCase) {
        return RECEIVERS.get(testCase.executable.getDeclaringClass());
    }

    public Pair<Object, ? extends Throwable> invoke(
            CompileCodeTestCase testCase,Object[] args) {
        Executable executable = testCase.executable;
        boolean old = executable.isAccessible();
        executable.setAccessible(true);
        try {
            try {
                if (executable instanceof Method) {
                    Method m = (Method) executable;
                    return new Pair<>(m.invoke(getReciever(testCase), args), null);
                }

                if (executable instanceof Constructor) {
                    Constructor c = (Constructor) executable;
                    return new Pair<>(c.newInstance(args), null);
                }
            } catch (InvocationTargetException e) {
                return new Pair<>(null, e.getCause());
            } catch (Throwable e) {
                return new Pair<>(null, e);
            }
        } finally {
            executable.setAccessible(old);
        }
        throw new Error(executable + " has unsupported type "
                + executable.getClass());
    }

    private Object[] getArguments(Executable method) {
        Class<?>[] params = method.getParameterTypes();
        Object[] result = new Object[params.length];
        int i = 0;
        for (Class<?> aClass : params) {
            result[i++] = getArgument(aClass);
        }
        return result;
    }
    private static Map<Class<?>, Object> DEFAULT_VALUES = new HashMap<>();
    static {
        DEFAULT_VALUES.put(boolean.class, false);
        DEFAULT_VALUES.put(byte.class, (byte) 0);
        DEFAULT_VALUES.put(short.class, (short) 0);
        DEFAULT_VALUES.put(char.class, (char) 0);
        DEFAULT_VALUES.put(int.class, (int) 0);
        DEFAULT_VALUES.put(long.class, (long) 0);
        DEFAULT_VALUES.put(float.class, (float) 0);
        DEFAULT_VALUES.put(double.class, (double) 0);
    }
    private Object getArgument(Class<?> aClass) {
        if (aClass.isPrimitive()) {
            return DEFAULT_VALUES.get(aClass);
        }
        return null;
    }
}
