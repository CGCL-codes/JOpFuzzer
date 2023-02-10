/*
 * Copyright (c) 2021, Oracle and/or its affiliates. All rights reserved.
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

package compiler.c2.irTests;

import jdk.test.lib.Asserts;
import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8273409
 * @summary TODO
 * @library /test/lib /
 * @run driver compiler.c2.irTests.TestPostParseCallDevirtualization
 */
public class TestPostParseCallDevirtualization {

    public static void main(String[] args) {
        TestFramework framework = new TestFramework();
        // Do not use receiver type profile
        Scenario noTypeProfile = new Scenario(0, "-XX:-UseTypeProfile");
        framework.addScenarios(noTypeProfile).start();
    }

    static interface I {
        public int method();
    }

    static class A implements I {
        @Override
        public int method() { return 0; };
    }

    static class B implements I {
        @Override
        public int method() { return 42; };
    }

    static final B val = new B();

    // Test post-parse call devirtualization
    @Test
    @IR(failOn = {IRNode.DYNAMIC_CALL_OF_METHOD, "method"},
        counts = {IRNode.STATIC_CALL_OF_METHOD, "method", "= 1"})
    public int test1() {
        I recv = new A();

        for (int i = 0; i < 100; i++) {
            if ((i % 2) == 0) {
                recv = val;
            }
        }
        return recv.method();
    }

    @Check(test = "test1")
    public void checkTest1(int returnValue) {
        Asserts.assertEquals(returnValue, 42);
    }
}
