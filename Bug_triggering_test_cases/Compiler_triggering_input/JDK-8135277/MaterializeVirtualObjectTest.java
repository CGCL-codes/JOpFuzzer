/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package compiler.jvmci;

import jdk.internal.jvmci.hotspot.CompilerToVM;
import jdk.internal.jvmci.hotspot.HotSpotJVMCIRuntime;
import jdk.internal.jvmci.hotspot.HotSpotResolvedJavaMethodImpl;
import jdk.internal.jvmci.hotspot.HotSpotStackFrameReference;
import jdk.test.lib.Asserts;
import jdk.test.lib.Utils;
import sun.hotspot.WhiteBox;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
 * @test
 * @library / /testlibrary /../../test/lib
 * @build sun.hotspot.WhiteBox MaterializeVirtualObjectTest
 * @run main ClassFileInstaller sun.hotspot.WhiteBox
 *                              sun.hotspot.WhiteBox$WhiteBoxPermission
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *     -XX:+WhiteBoxAPI -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI
 *     -XX:-UseJVMCIClassLoader -XX:CompileCommand=exclude,*::check
 *     compiler.jvmci.MaterializeVirtualObjectTest
 */
public class MaterializeVirtualObjectTest {
    private static final CompilerToVM C2VM = HotSpotJVMCIRuntime.runtime()
            .getCompilerToVM();
    private static final WhiteBox WB = WhiteBox.getWhiteBox();
    private static final int INVOCATIONS = 100_000;
    private final Method executable;
    private final HotSpotResolvedJavaMethodImpl testMethod;
    private boolean invalidate;

    public MaterializeVirtualObjectTest() {
        try {
            executable = this.getClass().getDeclaredMethod("testFrame",
                    String.class, Integer.class);
        } catch (NoSuchMethodException e) {
            throw new Error("Can't get executable for test method", e);
        }
        testMethod = get(executable);
    }

    public static void main(String[] args) {
        MaterializeVirtualObjectTest virtualObjectTest
                = new MaterializeVirtualObjectTest();
        virtualObjectTest.test(true);
        virtualObjectTest.test(false);
    }

    public static HotSpotResolvedJavaMethodImpl get(Executable executable) {
        if (!(executable instanceof Method)
                && !(executable instanceof Constructor)) {
            throw new Error("TEST BUG: wrong executable type");
        }
        Field slotField;
        int slotNum;
        try {
            slotField = executable.getClass().getDeclaredField("slot");
            slotField.setAccessible(true);
            slotNum = (int) slotField.get(executable);
        } catch (ReflectiveOperationException e) {
            throw new Error("TEST BUG: unable to get slot of the given method");
        }
        return C2VM.getResolvedJavaMethodAtSlot(executable.getDeclaringClass(),
                slotNum);
    }

    private void test(boolean invalidate) {
        System.out.printf("--Starting test: (invalidate=%b)%n", invalidate);
        this.invalidate = invalidate;
        testFrame("one", 1);
        for (int i = 1; i < INVOCATIONS; i++) {
            testFrame("two", i);
        }
        Utils.waitForCondition(() -> WB.isMethodCompiled(executable), 100L);
        Asserts.assertTrue(WB.isMethodCompiled(executable));
        testFrame("three", INVOCATIONS);
    }

    private void testFrame(String str, Integer i) {
        Helper s = new Helper(str, i);
        try {
            check(s.getS(), s.getI());
        } finally {
            assert (i != null) && (str != null) && (this != null)
                    && (s != null);
        }
    }

    private void check(Object obj, int iter) {
        // Materialize virtual objects on last invocation
        if (iter == INVOCATIONS) {
            HotSpotStackFrameReference hsFrame = C2VM.getNextStackFrame(null,
                    new HotSpotResolvedJavaMethodImpl[] {testMethod}, 0);
            Asserts.assertNotNull(hsFrame);
            printFrame(hsFrame);
            Asserts.assertTrue(WB.isMethodCompiled(executable),
                    "Compiled frame expected");
            C2VM.materializeVirtualObjects(hsFrame, invalidate);
            System.out.println("After materialize of virtual objects");
            printFrame(hsFrame);
            Asserts.assertFalse(hsFrame.hasVirtualObjects());
            Asserts.assertEQ(WB.isMethodCompiled(executable), !invalidate);
        }
    }

    private void printFrame(HotSpotStackFrameReference hsFrame) {
        System.out.println(hsFrame);
        System.out.printf("Has virtual: %b%n", hsFrame.hasVirtualObjects());
        System.out.printf("Is method compiled: %b, level: %d%n%n",
                WB.isMethodCompiled(executable),
                WB.getMethodCompilationLevel(executable));
    }

    private class Helper {
        private String s;
        private int i;

        public Helper(String s, int i) {
            this.s = s;
            this.i = i;
        }

        public String getS() {
            return s;
        }

        public int getI() {
            return i;
        }

        @Override
        public String toString() {
            Asserts.assertNotNull(s);
            Asserts.assertNE(i, 0);
            return "Helper(" + s + ", " + i + ")";
        }
    }
}
