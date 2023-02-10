/*
 * Copyright (c) 2017, 2021, Oracle and/or its affiliates. All rights reserved.
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

package compiler.valhalla.inlinetypes;

import jdk.test.lib.Asserts;

import java.lang.invoke.*;
import java.lang.reflect.Method;

import jdk.test.lib.hotspot.ir_framework.*;
import static compiler.valhalla.inlinetypes.InlineTypes.rI;
import static compiler.valhalla.inlinetypes.InlineTypes.rL;
import static compiler.valhalla.inlinetypes.InlineTypes.rD;
import static compiler.valhalla.inlinetypes.InlineTypes.IRNode.*;

/*
 * @test
 * @key randomness
 * @summary Test inline type calling convention optimizations
 * @library /test/lib
 * @requires (os.simpleArch == "x64" | os.simpleArch == "aarch64")
 * @compile InlineTypes.java
 * @run driver/timeout=300 compiler.valhalla.inlinetypes.TestCallingConventionBug
 */
public class TestCallingConventionBug {

    static final TestFramework testFramework = InlineTypes.getFramework();

    public static void main(String[] args) {

        final Scenario[] scenarios = {
                new Scenario(0,
                        "-DVerifyIR=false",
                        "-XX:+AlwaysIncrementalInline",
                        "-XX:FlatArrayElementMaxOops=0",
                        "-XX:FlatArrayElementMaxSize=0",
                        "-XX:InlineFieldMaxFlatSize=0",
                        "-XX:+InlineTypePassFieldsAsArgs",
                        "-XX:+InlineTypeReturnedAsFields",
                        "-XX:FlatArrayElementMaxSize=0")
        };

        testFramework.addScenarios(scenarios)
                .addHelperClasses(MyValue1.class,
                                  MyValue2.class,
                                  MyValue2Inline.class,
                                  MyValue3.class,
                                  MyValue3Inline.class,
                                  MyValue4.class)
                .start();
    }

    // Helper methods and classes

    @ForceCompileClassInitializer
    primitive class EmptyContainer {
        private MyValueEmpty empty;

        EmptyContainer(MyValueEmpty empty) {
            this.empty = empty;
        }

        @ForceInline
        MyValueEmpty getInline() { return empty; }

        @DontInline
        MyValueEmpty getNoInline() { return empty; }
    }

    @ForceCompileClassInitializer
    primitive class MixedContainer {
        public int val;
        private EmptyContainer empty;

        MixedContainer(int val, EmptyContainer empty) {
            this.val = val;
            this.empty = empty;
        }

        @ForceInline
        EmptyContainer getInline() { return empty; }

        @DontInline
        EmptyContainer getNoInline() { return empty; }
    }


    // Empty inline type container (mixed) return
    @Test
    @IR(failOn = {ALLOC, LOAD, STORE, TRAP})
    public MixedContainer test44() {
        MixedContainer c = new MixedContainer(rI, EmptyContainer.default);
        c = new MixedContainer(rI, c.getInline());
        return c;
    }

    @Run(test = "test44")
    public void test44_verifier(RunInfo info) {
        MixedContainer c = test44();
        Asserts.assertEquals(c, new MixedContainer(rI, EmptyContainer.default));
    }
}
