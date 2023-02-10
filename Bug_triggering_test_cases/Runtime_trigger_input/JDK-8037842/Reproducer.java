/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.List;
import java.util.ArrayList;

public class Reproducer {
  static final int NUM_FIELDS_IN_CLASSES = 32;

  public static void main(String[] args) throws Exception {
    List<Class> classes = new ArrayList<>();

    int id = 0;
    try {
      while (true) {
        String className = "Foo" + id;

        StringBuilder sourceCode = new StringBuilder();
        sourceCode.append("public class ").append(className).append(" {\n");
        for (int i = 0; i < NUM_FIELDS_IN_CLASSES; i++) {
          sourceCode.append("public int bar").append(i).append(";\n");
        }
        sourceCode.append("}");

        byte[] byteCode = InMemoryJavaCompiler.compile(className, sourceCode.toString());
        classes.add(ByteCodeLoader.load(className, byteCode));

        id++;
      }
    } catch (OutOfMemoryError e) {
      classes = null;
      System.gc();
    }
  }
}



