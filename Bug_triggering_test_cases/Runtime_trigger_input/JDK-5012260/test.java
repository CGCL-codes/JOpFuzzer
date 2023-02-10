
import java.lang.reflect.*;
import java.util.*;

public class IdentityBug {
    static IdentityHashMap map = new IdentityHashMap();

    static void traverse(Object obj) throws Exception {
        if (obj != null && map.get(obj) == null) {
            try {
                map.put(obj, obj);
            } catch (Exception x) {}
            if (obj instanceof Object[]) {
                Object[] arr = (Object[])obj;
                for (int i = 0; i < arr.length; i++) {
                    traverse(arr[i]);
                }
            } else {
                Class cls = obj.getClass();
                do {
                    Field[] fields = cls.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        Field f = fields[i];
                        Class type = f.getType();
                        if (!type.isPrimitive()) {
                            f.setAccessible(true);
                            traverse(f.get(obj));
                        }
                    }
                } while ((cls = cls.getSuperclass()) != null);
            }
        }
    }

    static void traverseClass(Class cls) throws Exception {
        do {
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                if ((f.getModifiers() & Modifier.STATIC) != 0) {
                    Class type = f.getType();
                    if (!type.isPrimitive()) {
                        f.setAccessible(true);
                        traverse(f.get(null));
                    }
                }
            }
        } while ((cls = cls.getSuperclass()) != null);
    }

    public static void main(String[] arg) throws Exception {
        traverseClass(IdentityBug.class);
    }
}
