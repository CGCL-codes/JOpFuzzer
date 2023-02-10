/*
 * Ident: @(#)invokespecial01106m1.java generated from: %Z%%M% %I% %E%
 *
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 */

package javasoft.sqe.tests.vm.instr.invokespecial.invokespecial011.invokespecial01106m1;

import java.io.PrintStream;

public class invokespecial01106m1 {

    public static int run(String argv[], PrintStream out) {

    if (instantiateNegative("invokespecial01106m10n", NoSuchMethodError.class, argv, out) != 0/*STATUS_PASSED*/)
        return 2/*STATUS_FAILED*/;

    return 0/*STATUS_PASSED*/;
    }

    public static int instantiateNegative(String testName, Class expectedException, String argv[], PrintStream out) {

        Class badClass = null;
        try {
            badClass = Class.forName("javasoft.sqe.tests.vm.instr.invokespecial.invokespecial011.invokespecial01106m1." + testName);
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
