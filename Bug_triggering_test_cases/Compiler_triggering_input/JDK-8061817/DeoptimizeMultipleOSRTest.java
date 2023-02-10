/*
 * Copyright (c) 2013, 2014, Oracle and/or its affiliates. All rights reserved.
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

import sun.hotspot.WhiteBox;
import java.lang.reflect.Method;

/*
 * @test DeoptimizeMultipleOSRTest
 * @bug 8061817
 * @library /testlibrary /testlibrary/whitebox
 * @build DeoptimizeMultipleOSRTest
 * @run main ClassFileInstaller sun.hotspot.WhiteBox
 *                              sun.hotspot.WhiteBox$WhiteBoxPermission
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:CompileCommand=compileonly,DeoptimizeMultipleOSRTest::triggerOSR DeoptimizeMultipleOSRTest
 * @summary testing of WB::deoptimizeMethod()
 */
public class DeoptimizeMultipleOSRTest {
    protected static final WhiteBox WHITE_BOX = WhiteBox.getWhiteBox();
    private static Method method;
    private static int counter = 0;
    
    public static void main(String[] args) throws Exception {
        method = DeoptimizeMultipleOSRTest.class.getDeclaredMethod("triggerOSR", boolean.class, long.class);
        // Warmup
        for (int i = 0; i < 1000; ++i) {
            triggerOSR(true, 1);
            triggerOSR(false, 1);
        }
        // Trigger to OSR compiled versions
        triggerOSR(true, 150000);
        triggerOSR(false, 150000);
        // Wait for compilation
        Thread.sleep(1000);
        // Deoptimize
        WHITE_BOX.deoptimizeMethod(method, true);
        if (WHITE_BOX.isMethodCompiled(method, true)) {
          throw new RuntimeException("Not all OSR compiled versions were deoptimized");
        }
    }
    
    /**
     * Triggers OSR compilations by executing loops.
     * 
     * @param first Determines which loop to execute
     * @param limit The number of loop iterations
     */
    public static void triggerOSR(boolean first, long limit) {
        if (first) {
            // Trigger OSR compilation 1
            for (int i = 0; i < limit; ++i) {
                counter++;
            }
        } else {
            // Trigger OSR compilation 2
            for (int i = 0; i < limit; ++i) {
                counter++;
            }
        }
    }
}
