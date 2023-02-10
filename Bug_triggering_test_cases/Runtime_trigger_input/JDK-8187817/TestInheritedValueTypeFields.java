/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
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

package runtime.valhalla.valuetypes;

import jdk.test.lib.Asserts;

/*
 * @test ValueTypeGetField
 * @summary TODO
 * @library /test/lib
 * @compile -XDenableValueTypes Point.java TestInheritedValueTypeFields.java
 * @run main/othervm -Xint -XX:+EnableValhalla runtime.valhalla.valuetypes.TestInheritedValueTypeFields
 */

class A {
    Point p;
}

class B extends A {

}

public class TestInheritedValueTypeFields {

    public static void main(String[] args) {
        B b = new B();
        Asserts.assertEquals(b.p.x, 0);
        Asserts.assertEquals(b.p.y, 0);
    }
}
