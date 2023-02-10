import java.lang.module.*;
import java.io.*;
import java.util.*;

public class EnumEquality {

    public static void main(final String[] args) throws Exception {
        String moduleName = "java.sql";
        // load the "java.sql" module from boot layer
        Optional<Module> bootModule = ModuleLayer.boot().findModule(moduleName);
        if (bootModule.isEmpty()) {
            throw new RuntimeException(moduleName + " module is missing in boot layer");
        }
        ModuleDescriptor m1 = bootModule.get().getDescriptor();
        // now recreate the same module using the ModuleDescriptor.read on the module's module-info.class
        ModuleDescriptor m2;
        try (InputStream moduleInfo = bootModule.get().getResourceAsStream("module-info.class")) {
            if (moduleInfo == null) {
                throw new RuntimeException("Could not locate module-info.class in " + moduleName + " module");
            }
            // this will internally use a ModuleDescriptor.Builder to construct the ModuleDescriptor
            m2 = ModuleDescriptor.read(moduleInfo);
        }
        if (!m1.equals(m2)) {
            // root cause - the enums aren't "equal"
            for (ModuleDescriptor.Requires r1 : m1.requires()) {
                if (r1.name().equals("java.base")) {
                    for (ModuleDescriptor.Requires r2 : m2.requires()) {
                        if (r2.name().equals("java.base")) {
                            System.out.println("Modifiers r1 " + r1.modifiers() + " r2 " + r2.modifiers()
                                + " --> equals? " + r1.modifiers().equals(r2.modifiers()));
                        }
                    }
                }
            }

            throw new RuntimeException("ModuleDescriptor(s) aren't equal: \n" + m1 + "\n" + m2);
        }
        System.out.println("Success");
    }
}