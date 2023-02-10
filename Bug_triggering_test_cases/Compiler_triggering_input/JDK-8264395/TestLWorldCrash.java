/*
 * Copyright (c) 2019, 2021, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.Arrays;

import jdk.test.lib.Asserts;
import jdk.test.lib.hotspot.ir_framework.*;
import static compiler.valhalla.inlinetypes.InlineTypes.rI;
import static compiler.valhalla.inlinetypes.InlineTypes.rL;
import static compiler.valhalla.inlinetypes.InlineTypes.rD;
import static compiler.valhalla.inlinetypes.InlineTypes.IRNode.*;

import test.java.lang.invoke.lib.InstructionHelper;

/*
 * @test
 * @key randomness
 * @summary Test inline types in LWorld.
 * @library /test/lib /test/jdk/lib/testlibrary/bytecode /test/jdk/java/lang/invoke/common
 * @requires (os.simpleArch == "x64" | os.simpleArch == "aarch64")
 * @build jdk.experimental.bytecode.BasicClassBuilder Test.java.lang.invoke.lib.InstructionHelper
 * @compile InlineTypes.java
 * @run driver compiler.valhalla.inlinetypes.TestLWorldCrash
 */

public class TestLWorldCrash {
    static final TestFramework testFramework = InlineTypes.getFramework();

    public static void main(String[] args) {

        Scenario[] scenarios = InlineTypes.DEFAULT_SCENARIOS;
        scenarios[2].addFlags("-DVerifyIR=false");
        scenarios[3].addFlags("-XX:-MonomorphicArrayCheck", "-XX:FlatArrayElementMaxSize=-1");
        scenarios[4].addFlags("-XX:-MonomorphicArrayCheck");

        testFramework.addScenarios(scenarios)
                .addHelperClasses(MyValue1.class,
                        MyValue2.class,
                        MyValue2Inline.class,
                        MyValue3.class,
                        MyValue3Inline.class)
                .start();
    }

    // Helper methods

    private static final MyValue1 testValue1 = MyValue1.createWithFieldsInline(rI, rL);
    private static final MyValue2 testValue2 = MyValue2.createWithFieldsInline(rI, rD);

    Object objectField1 = null;
    Object objectField2 = null;
    Object objectField3 = null;
    Object objectField4 = null;
    Object objectField5 = null;
    Object objectField6 = null;

    MyValue1 valueField1 = testValue1;
    MyValue1 valueField2 = testValue1;
    MyValue1.ref valueField3 = testValue1;
    MyValue1 valueField4;
    MyValue1.ref valueField5;

    static MyValue1.ref staticValueField1 = testValue1;
    static MyValue1 staticValueField2 = testValue1;
    static MyValue1 staticValueField3;
    static MyValue1.ref staticValueField4;

    private static final MyValue1[] testValue1Array = new MyValue1[]{testValue1,
            testValue1,
            testValue1};

    private static final MyValue1[][] testValue1Array2 = new MyValue1[][]{testValue1Array,
            testValue1Array,
            testValue1Array};

    private static final MyValue2[] testValue2Array = new MyValue2[]{testValue2,
            testValue2,
            testValue2};

    private static final Integer[] testIntegerArray = new Integer[42];

    protected long hash() {
        return testValue1.hash();
    }

    private void rerun_and_recompile_for(Method m, int num, Runnable test) {
        for (int i = 1; i < num; i++) {
            test.run();

            if (TestFramework.isCompiled(m)) {
                TestFramework.compile(m, CompLevel.C2);
            }
        }
    }


    // Escape analysis tests

    static interface WrapperInterface {
        long value();

        final static WrapperInterface ZERO = new LongWrapper(0);

        static WrapperInterface wrap(long val) {
            return (val == 0L) ? ZERO : new LongWrapper(val);
        }
    }

    @ForceCompileClassInitializer
    static primitive class LongWrapper implements WrapperInterface {
        final static LongWrapper ZERO = new LongWrapper(0);
        private long val;

        LongWrapper(long val) {
            this.val = val;
        }

        static LongWrapper wrap(long val) {
            return (val == 0L) ? ZERO : new LongWrapper(val);
        }

        public long value() {
            return val;
        }
    }

    @ForceCompileClassInitializer
    static class InterfaceBox {
        WrapperInterface content;

        InterfaceBox(WrapperInterface content) {
            this.content = content;
        }

        static InterfaceBox box_sharp(long val) {
            return new InterfaceBox(LongWrapper.wrap(val));
        }

        static InterfaceBox box(long val) {
            return new InterfaceBox(WrapperInterface.wrap(val));
        }
    }

    @ForceCompileClassInitializer
    static class ObjectBox {
        Object content;

        ObjectBox(Object content) {
            this.content = content;
        }

        static ObjectBox box_sharp(long val) {
            return new ObjectBox(LongWrapper.wrap(val));
        }

        static ObjectBox box(long val) {
            return new ObjectBox(WrapperInterface.wrap(val));
        }
    }

    @ForceCompileClassInitializer
    static class RefBox {
        LongWrapper.ref content;

        RefBox(LongWrapper.ref content) {
            this.content = content;
        }

        static RefBox box_sharp(long val) {
            return new RefBox(LongWrapper.wrap(val));
        }

        static RefBox box(long val) {
            return new RefBox((LongWrapper.ref) WrapperInterface.wrap(val));
        }
    }

    @ForceCompileClassInitializer
    static class InlineBox {
        LongWrapper content;

        InlineBox(long val) {
            this.content = LongWrapper.wrap(val);
        }

        static InlineBox box(long val) {
            return new InlineBox(val);
        }
    }

    @ForceCompileClassInitializer
    static class GenericBox<T> {
        T content;

        static GenericBox<LongWrapper.ref> box_sharp(long val) {
            GenericBox<LongWrapper.ref> res = new GenericBox<>();
            res.content = LongWrapper.wrap(val);
            return res;
        }

        static GenericBox<WrapperInterface> box(long val) {
            GenericBox<WrapperInterface> res = new GenericBox<>();
            res.content = WrapperInterface.wrap(val);
            return res;
        }
    }

    long[] lArr = {0L, rL, 0L, rL, 0L, rL, 0L, rL, 0L, rL};

    // Test removal of allocations when inline type instance is wrapped into box object
    @Test
    @IR(failOn = {ALLOC_G, MEMBAR},
        counts = {PREDICATE_TRAP, "= 1"})
    public long test109() {
        long res = 0;
        for (int i = 0; i < lArr.length; i++) {
            res += InterfaceBox.box(lArr[i]).content.value();
        }
        return res;
    }

    @Run(test = "test109")
    @Warmup(10000) // Make sure interface calls are inlined
    public void test109_verifier() {
        long res = test109();
        Asserts.assertEquals(res, 5 * rL);
    }
}
