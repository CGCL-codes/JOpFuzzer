/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.lang.reflect.Field;
import jdk.internal.vm.annotation.Stable;
import jdk.vm.ci.hotspot.HotSpotResolvedJavaField;
import jdk.vm.ci.meta.MetaAccessProvider;
import jdk.vm.ci.meta.ResolvedJavaField;
import jdk.vm.ci.runtime.JVMCI;

/**
 *
 * @author kshefov
 */
public class StableFieldTest {

    @Stable static int myField = 5;
    
    public static int getMyField() {
        return myField;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("myFeild before = " + getMyField());
        HotSpotResolvedJavaField rf = (HotSpotResolvedJavaField) getReslovedJavaField(StableFieldTest.class, "myField");
        myField = 6;
        System.out.println("myFeild after = " + getMyField());
        System.out.println(rf.getAnnotation(Stable.class));
        System.out.println(rf.isStable());
    }
    
    static ResolvedJavaField getReslovedJavaField(Class clazz, String fieldName) {
        Field reflectionField = null;
        try {
            reflectionField = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            throw new Error("Test bug: Invalid field name", ex);
        } catch (SecurityException ex) {
            throw new Error("Unexpected error", ex);
        }
        MetaAccessProvider metaAccess = JVMCI.getRuntime().getHostJVMCIBackend().getMetaAccess();
        return metaAccess.lookupJavaField(reflectionField);
    }
    
}
