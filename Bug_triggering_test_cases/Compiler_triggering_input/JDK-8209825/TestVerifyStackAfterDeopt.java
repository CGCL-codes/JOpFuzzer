/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
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


/*
 * @test TestVerifyStackAfterDeopt
 * @bug 8148871
 * @summary Checks VerifyStack after deoptimization of array allocation slow call
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:TieredStopAtLevel=1
 *                   -XX:MinTLABSize=1k -XX:TLABSize=1k
 *                   -XX:+DeoptimizeALot -XX:+VerifyStack
 *                   compiler.interpreter.TestVerifyStackAfterDeopt
 */

package compiler.interpreter;

import java.util.Collection;
import java.util.ArrayList;

public class TestVerifyStackAfterDeopt {

    private void method(Object[] a) {

    }

    private void test() {
        // For the array allocation, C1 emits a slow call into the runtime
        // that deoptimizes the caller frame due to -XX:+DeoptimizeALot.
        // The VerifyStack code then gets confused because the following
        // bytecode instruction is an invoke and the interpreter oop map
        // generator reports the oop map after execution of that invoke.
        method(new Object[0]);
    }

    static class Register {}

    private Register[] registers;

    private void test2(Collection<Register> registers) {
        // From package jdk.vm.ci.code.RegisterArray.<init>(Collection<Register> registers) 
        this.registers = registers.toArray(new Register[registers.size()]);
    }

    public static void main(String[] args) {
        TestVerifyStackAfterDeopt t = new TestVerifyStackAfterDeopt();
        ArrayList<Register> coll = new ArrayList<Register>();
        for (int i = 0; i < 1000; ++i) {
            coll.add(new Register());
        }
        // Run long enough for C1 compilation to trigger and TLAB to fill up
        for (int i = 0; i < 100_000; ++i) {
            t.test();
            t.test2(coll);
        }
    }
}
