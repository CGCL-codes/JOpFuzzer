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

package compiler.jvmci.CompilerToVM;

import compiler.jvmci.CompilerToVM.testcases.AbstractClass;
import compiler.jvmci.CompilerToVM.testcases.DoNotExtendClass;
import compiler.jvmci.CompilerToVM.testcases.MultipleAbstractImplementer;
import compiler.jvmci.CompilerToVM.testcases.MultipleImplementersInterface;
import compiler.jvmci.CompilerToVM.testcases.MultipleImplementersInterfaceExtender;
import compiler.jvmci.CompilerToVM.testcases.SingleImplementer;
import compiler.jvmci.CompilerToVM.testcases.SingleImplementerInterface;
import compiler.jvmci.CompilerToVM.testcases.SingleSubclass;
import compiler.jvmci.CompilerToVM.testcases.SingleSubclassedClass;
import compiler.jvmci.common.CTVMUtilities;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import jdk.internal.jvmci.hotspot.CompilerToVMHelper;
import jdk.internal.jvmci.hotspot.HotSpotResolvedJavaMethod;
import jdk.internal.jvmci.hotspot.HotSpotResolvedJavaMethodImpl;
import jdk.internal.jvmci.hotspot.HotSpotResolvedObjectTypeImpl;
import jdk.test.lib.Asserts;

/*
 * @test
 * @library / /testlibrary /../../test/lib
 * @build compiler.jvmci.CompilerToVM.GetVtableIndexForInterfaceTest
 * @run main ClassFileInstaller jdk.internal.jvmci.hotspot.CompilerToVMHelper
 * @run main/othervm -Xbootclasspath/a:.
 *     -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI
 *     compiler.jvmci.CompilerToVM.GetVtableIndexForInterfaceTest
 */
public class GetVtableIndexForInterfaceTest {
    private static final int INVALID_VTABLE_INDEX = -4; // see method.hpp: VtableIndexFlag

    public static void main(String args[]) {
        GetVtableIndexForInterfaceTest test
                = new GetVtableIndexForInterfaceTest();
        try {
            for (TestCase tcase : createTestCases()) {
                test.runTest(tcase);
            }
        } catch (NoSuchMethodException e) {
            throw new Error("TEST BUG: can't find requested method", e);
        }
    }

    private static Set<TestCase> createTestCases() {
        Set<TestCase> result = new HashSet<>();
        // non iface method
        SingleImplementerInterface justToLoad = new SingleImplementer();
        result.add(new TestCase(SingleImplementer.class, SingleImplementer.class,
                "nonInterfaceMethod", false));
        // iface method w/o default implementation
        result.add(new TestCase(SingleImplementer.class, SingleImplementerInterface.class,
                "interfaceMethod", true)); // returns invalid index. a bug?
        /* another iface which provides default implementation for the
           original iface*/
        result.add(new TestCase(MultipleImplementersInterfaceExtender.class,
                MultipleImplementersInterface.class, "testMethod", false,
                InternalError.class));
        // iface method w/ default implementation
        result.add(new TestCase(SingleImplementer.class, SingleImplementerInterface.class,
                "defaultMethod", true));
        // non iface class
        SingleSubclassedClass justToLoad2 = new SingleSubclass();
        result.add(new TestCase(SingleSubclass.class, SingleSubclassedClass.class,
                "inheritedMethod", false));
        // class not implementing iface
        DoNotExtendClass justToLoad3 = new DoNotExtendClass();
        result.add(new TestCase(DoNotExtendClass.class, SingleImplementerInterface.class,
                "defaultMethod", false));
        // abstract class which doesn't implement iface
        AbstractClass justToLoad4 = new AbstractClass() {
            public void abstractMethod() {
                // empty
            }
        };
        result.add(new TestCase(AbstractClass.class, SingleImplementerInterface.class,
                "defaultMethod", false));
        // abstract class which implements iface
        MultipleAbstractImplementer justToLoad5 = new MultipleAbstractImplementer() {
            @Override
            public void abstractMethod() {
                // empty
            }

            @Override
            public void testMethod() {
                // empty
            }
        };
        result.add(new TestCase(MultipleAbstractImplementer.class,
                MultipleImplementersInterface.class, "defaultMethod", true));
        return result;
    }

    private void runTest(TestCase tcase) throws NoSuchMethodException{
        Method method = tcase.iface.getDeclaredMethod(tcase.methodName);
        System.out.printf("CASE: class=%s, iface=%s, method=%s%n",
                tcase.clazz.getName(), tcase.iface.getName(), method.getName());
        HotSpotResolvedObjectTypeImpl metaspaceKlass = CompilerToVMHelper
                .lookupType(CTVMUtilities.getJvmClassName(tcase.clazz),
                        getClass(), true);
        HotSpotResolvedJavaMethod metaspaceMethod = CTVMUtilities
                .getResolvedMethod(tcase.iface, method);
System.out.println("DEBUG: " + metaspaceMethod.getName() + " | " + metaspaceMethod.getDeclaringClass().getName());
        int index = 0;
        try {
            index = CompilerToVMHelper.getVtableIndexForInterface(metaspaceKlass,
                (HotSpotResolvedJavaMethodImpl)metaspaceMethod);
        } catch (Throwable t) {
            System.out.println("INFO: caught exception: " + t);
            if (tcase.isPositive || tcase.expectedException == null) {
                throw new Error("Caught unexpected exception " + t);
            }
            if (!tcase.expectedException.equals(t.getClass())) {
                throw new Error(String.format("Caught %s while expected %s",
                        t.getClass().getName(), tcase.expectedException.getName()));
            }
            // passed
        }
System.out.println("DEBUG1: index=" + index);
        if (tcase.expectedException == null) {
            Asserts.assertTrue(!(index != INVALID_VTABLE_INDEX ^ tcase.isPositive),
                    "Unexpected index: " + index);
        }
    }

    private static class TestCase {
        public final Class<?> clazz;
        public final Class<?> iface;
        public final String methodName;
        public final boolean isPositive;
        public final Class<?> expectedException;

        public TestCase(Class<?> clazz, Class<?> iface, String methodName,
                boolean isPositive, Class<?> expectedException) {
            this.clazz = clazz;
            this.iface = iface;
            this.methodName = methodName;
            this.isPositive = isPositive;
            this.expectedException = expectedException;
        }

        public TestCase(Class<?> clazz, Class<?> iface, String methodName,
                boolean isPositive) {
            this.clazz = clazz;
            this.iface = iface;
            this.methodName = methodName;
            this.isPositive = isPositive;
            this.expectedException = null;
        }        
    }
}