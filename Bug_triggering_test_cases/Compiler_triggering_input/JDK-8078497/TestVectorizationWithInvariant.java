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
 *
 */

import com.oracle.java.testlibrary.*;
import sun.misc.Unsafe;
import java.lang.reflect.Field;

/**
 * @test
 * @bug 8078497
 * @summary TODO
 * @library /testlibrary
 * @run main/othervm TestVectorizationWithInvariant
 */
public class TestVectorizationWithInvariant {

    private static Unsafe unsafe;
    private static final long BYTE_ARRAY_OFFSET;
    private static final long CHAR_ARRAY_OFFSET;

    static {
        unsafe = Utils.getUnsafe();
        BYTE_ARRAY_OFFSET = unsafe.arrayBaseOffset(byte[].class);
        CHAR_ARRAY_OFFSET = unsafe.arrayBaseOffset(char[].class);
    }

    public static void main(String[] args) throws Exception {
        byte[] byte_array1 = new byte[1000];
        byte[] byte_array2 = new byte[1000];
        char[] char_array = new char[1000];
        
        for (int i = 0; i < 1_000_000; ++i) {
            copyCharToByte(char_array, byte_array1, 1);
            copyByteToChar(byte_array1, byte_array2, char_array, 1);
        }
    }

    public static void copyCharToByte(char[] src, byte[] dst, int off) {
        off = (int) BYTE_ARRAY_OFFSET + (off << 1);
        for (int i = 0; i < 100; i = i + 8) {
            // Copy 8 chars from src to dst
            unsafe.putChar(dst, off, src[i]);
            unsafe.putChar(dst, off + 2, src[i + 1]);
            unsafe.putChar(dst, off + 4, src[i + 2]);
            unsafe.putChar(dst, off + 6, src[i + 3]);
            unsafe.putChar(dst, off + 8, src[i + 4]);
            unsafe.putChar(dst, off + 10, src[i + 5]);
            unsafe.putChar(dst, off + 12, src[i + 6]);
            unsafe.putChar(dst, off + 14, src[i + 7]);
        }
    }

    public static void copyByteToChar(byte[] src1, byte[] src2, char[] dst, int off) {
        off = (int) BYTE_ARRAY_OFFSET + (off << 1);
        byte[] src = src1;
        for (int i = (int) CHAR_ARRAY_OFFSET; i < 100; i = i + 8) {
            // Copy 8 chars from src to dst
            unsafe.putChar(dst, i, unsafe.getChar(src, off));
            unsafe.putChar(dst, i + 2, unsafe.getChar(src, off + 2));
            unsafe.putChar(dst, i + 4, unsafe.getChar(src, off + 4));
            unsafe.putChar(dst, i + 6, unsafe.getChar(src, off + 6));
            unsafe.putChar(dst, i + 8, unsafe.getChar(src, off + 8));
            unsafe.putChar(dst, i + 10, unsafe.getChar(src, off + 10));
            unsafe.putChar(dst, i + 12, unsafe.getChar(src, off + 12));
            unsafe.putChar(dst, i + 14, unsafe.getChar(src, off + 14));

            // Prevent loop invariant code motion of char read.
            src = (src == src1) ? src2 : src1;
        }
    }
}
