/*
 * Copyright (c) 2013, 2018, Oracle and/or its affiliates. All rights reserved.
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

/**
 * @test TieredLevelsTest
 * @summary Verify that all levels &lt; 'TieredStopAtLevel' can be used
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build sun.hotspot.WhiteBox
 * @run driver ClassFileInstaller sun.hotspot.WhiteBox
 *                                sun.hotspot.WhiteBox$WhiteBoxPermission
 * @run main/othervm -Xbootclasspath/a:. -XX:+TieredCompilation
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:-UseCounterDecay
 *                   -XX:CompileCommand=compileonly,compiler.whitebox.SimpleTestCaseHelper::*
 *                   -XX:CompileCommand=print,compiler.whitebox.SimpleTestCaseHelper::*
 *                   compiler.tiered.Level2RecompilationTest
 */

package compiler.tiered;

import compiler.whitebox.CompilerWhiteBoxTest;
import jtreg.SkippedException;

public class Level2RecompilationTest extends CompLevelsTest {
    public static void main(String[] args) throws Throwable {
        if (CompilerWhiteBoxTest.skipOnTieredCompilation(false)) {
            throw new SkippedException("Test isn't applicable for non-tiered mode");
        }
        String[] testcases = {"METHOD_TEST", "OSR_STATIC_TEST"};
        CompilerWhiteBoxTest.main(Level2RecompilationTest::new, testcases);
    }

    protected Level2RecompilationTest(TestCase testCase) {
        super(testCase);
        // to prevent inlining of #method
        WHITE_BOX.testSetDontInlineMethod(method, true);
    }

    @Override
    protected void test() throws Exception {
        if (skipXcompOSR()) {
          return;
        }

        checkNotCompiled();
        int bci = WHITE_BOX.getMethodEntryBci(method);
        WHITE_BOX.markMethodProfiled(method);
        if (testCase.isOsr()) {
 /*
        static compiler.whitebox.SimpleTestCaseHelper::osrStaticMethod(J)I
  interpreter_invocation_count:     5000
  invocation_counter:               5000
  backedge_counter:                15000
  mdo size: 528 bytes

0 iconst_0
1 istore_2
2 lload_0
3 lconst_1
4 lcmp
5 ifeq 15
  0   bci: 5    BranchData          taken(0) displacement(48)
                                    not taken(0)
8 getstatic 24 <compiler/whitebox/SimpleTestCaseHelper.OSR_STATIC/Ljava/lang/reflect/Method;>
11 invokestatic 25 <compiler/whitebox/SimpleTestCaseHelper.warmup(Ljava/lang/reflect/Method;)I>
  32  bci: 11   CounterData         count(0)
14 istore_2
15 lconst_0
16 lstore_3
17 lload_3
18 lload_0
19 lcmp
20 ifge 36
  48  bci: 20   BranchData          taken(0) displacement(72)
                                    not taken(0)
23 iload_2
24 invokestatic 26 <compiler/whitebox/SimpleTestCaseHelper.staticMethod()I>
  80  bci: 24   CounterData         count(0)
27 iadd
28 istore_2
29 lload_3
30 lconst_1
31 ladd
32 lstore_3
33 goto 17
  96  bci: 33   JumpData            taken(0) displacement(-48)
36 iload_2
37 ireturn
*/
            bci = 15;
        }

        WHITE_BOX.enqueueMethodForCompilation(method, COMP_LEVEL_FULL_PROFILE, bci);
        checkCompiled();
        checkLevel(COMP_LEVEL_LIMITED_PROFILE, getCompLevel());

        for (int i=0; i<1_000; ++i) {
            WHITE_BOX.enqueueMethodForCompilation(method, COMP_LEVEL_FULL_PROFILE, bci);
            //waitBackgroundCompilation();
            checkLevel(COMP_LEVEL_LIMITED_PROFILE, getCompLevel());
        }
    }

    @Override
    protected void checkLevel(int expected, int actual) {
        if (expected == COMP_LEVEL_FULL_PROFILE
                && actual == COMP_LEVEL_LIMITED_PROFILE) {
            // for simple method full_profile may be replaced by limited_profile
            if (IS_VERBOSE) {
                System.out.printf("Level check: full profiling was replaced "
                        + "by limited profiling. Expected: %d, actual:%d\n",
                        expected, actual);
            }
            return;
        }
        super.checkLevel(expected, actual);
    }
}

