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
 */

package compiler.jvmci.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import jdk.internal.jvmci.hotspot.CompilerToVMHelper;
import jdk.internal.jvmci.hotspot.HotSpotResolvedJavaMethod;

public class CTVMUtilities {
    /*
     * A method to return HotSpotResolvedJavaMethod object using class object
     * and method name as input
     */
    public static HotSpotResolvedJavaMethod getResolvedMethod(Class<?> cls,
            Method method) {
        Field slotField;
        int slot;
        try {
            slotField = Method.class.getDeclaredField("slot");
            slotField.setAccessible(true);
            slot = slotField.getInt(method);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error("TEST BUG: Can't get slot field", e);
        }
        return CompilerToVMHelper.getResolvedJavaMethodAtSlot(cls, slot);
    }

    /*
     * A method returns jvm-style class name of provided object
     * @return String with class name
     */
    public static String getJvmClassName(Object obj) {
        return getJvmClassName(obj.getClass());
    }

    /*
     * A method returns jvm-style class name of provided Class<?> instance
     * @return String with class name
     */
    public static String getJvmClassName(Class<?> clazz) {
        return String.format("L%s;", clazz.getName().replace(".", "/"));
    }
}
