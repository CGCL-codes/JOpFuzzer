/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

// Run with -XX:-TieredCompilation -XX:CompileThresholdScaling=0.001 -XX:CICompilerCount=1
// or       -XX:TieredCompileTaskTimeout=1000 -XX:CompileThresholdScaling=0.001 -XX:CICompilerCount=2
public class Test {

    // Some methods to fill up the compile queue
    public static void test0() { int i = 0; while(i < 1) { i = 1; } }
    public static void test1() { int i = 0; while(i < 1) { i = 1; } }
    public static void test2() { int i = 0; while(i < 1) { i = 1; } }
    public static void test3() { int i = 0; while(i < 1) { i = 1; } }
    public static void test4() { int i = 0; while(i < 1) { i = 1; } }
    public static void test5() { int i = 0; while(i < 1) { i = 1; } }
    public static void test6() { int i = 0; while(i < 1) { i = 1; } }
    public static void test7() { int i = 0; while(i < 1) { i = 1; } }
    public static void test8() { int i = 0; while(i < 1) { i = 1; } }
    public static void test9() { int i = 0; while(i < 1) { i = 1; } }
    public static void test10() { int i = 0; while(i < 1) { i = 1; } }
    public static void test11() { int i = 0; while(i < 1) { i = 1; } }
    public static void test12() { int i = 0; while(i < 1) { i = 1; } }
    public static void test13() { int i = 0; while(i < 1) { i = 1; } }
    public static void test14() { int i = 0; while(i < 1) { i = 1; } }
    public static void test15() { int i = 0; while(i < 1) { i = 1; } }
    public static void test16() { int i = 0; while(i < 1) { i = 1; } }
    public static void test17() { int i = 0; while(i < 1) { i = 1; } }
    public static void test18() { int i = 0; while(i < 1) { i = 1; } }
    public static void test19() { int i = 0; while(i < 1) { i = 1; } }

    public static void main(String[] args) throws Throwable {
        ClassLoader loader = Test.class.getClassLoader();
        URL classesRootDir = Test.class.getProtectionDomain().getCodeSource().getLocation();
        for (int i = 0; i < 100_000; ++i) {
            URLClassLoader myLoader = URLClassLoader.newInstance(new URL[] {classesRootDir}, loader.getParent());
            Class<?> classObject = Class.forName("Test", true, myLoader);

            for (int j = 1; j < 20; ++j) {
                Method method = classObject.getDeclaredMethod("test" + j);
                for (int k = 0; k < 10; ++k) {
                    method.invoke(null);
                }
            }
        }
    }
}
