/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
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
 * @test
 * @bug 8160425
 * @summary Test vectorization with a signalling NaN.
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-OptimizeFill TestNaNVector
 */
public class TestNaNVector {
    private char[] array;  
    private static final int LEN = 1024;

    public static void main(String args[]) {
        TestNaNVector test = new TestNaNVector();
        for (int i = 0; i < 10_000; ++i) {
          test.vectorizeNaN();
        }
        test.checkResult();
    }

    public TestNaNVector() {
        array = new char[LEN];    
    }

    public void vectorizeNaN() {
        // This loop will be vectorized and the array store will be replaced by
        // a 64-bit vector store to four subsequent array elements. The vector
        // should look like this '0xfff7fff7fff7fff7' and is read from the constant
        // table. However, in floating point arithmetic this is a signalling NaN
        // which may be converted to a quiet NaN when processed by the x87 FPU.
        // If the signalling bit is set, the vector ends up in the constant table
        // as '0xfffffff7fff7fff7' which leads to an incorrect result.
        for (int i = 0; i < LEN; ++i) {
            array[i] = (char)0xfff7;
        }
    }

    public void checkResult() {
        for (int i = 0; i < LEN; ++i) {
            if (array[i] != (char)0xfff7) {
                throw new RuntimeException("Invalid result: array[" + i + "] = " + (int)array[i] + " != -9");
            }
        }
    }
}

