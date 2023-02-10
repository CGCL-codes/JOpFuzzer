
package com.db.listener.sample.pojo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by movaaja on 3/24/2017.
 */
public class Test14 {
    public static Unsafe unsafe;
    public static void main(String args[]) throws InstantiationException {
         unsafe = getUnsafe();

        A a = new A();
        a.value = 10;
        long sizeofA = sizeOf(a);
        long newa = unsafe.allocateMemory(sizeofA);
        long aadress = toAddress(a);
        unsafe.copyMemory(aadress,newa,sizeofA);
        A b = (A) fromAddress(newa);
        System.out.println(b.value);
        a=null;
        char tmp[]=null;
        try {
            tmp = new char[500000];
        }catch (Exception e){
            e.printStackTrace();
        }
        Arrays.fill(tmp,'a');
        System.out.println(b.value);
//        System.out.println(Arrays.toString(b.d));


    }

    public static Unsafe getUnsafe()  {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
            return unsafe;
        }catch(IllegalAccessException | NoSuchFieldException e){
            e.printStackTrace();
        }
        return null;
    }
    public static long sizeOf(Object o) {
        Unsafe u = unsafe;
        HashSet<Field> fields = new HashSet<Field>();
        Class c = o.getClass();
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }

        // get offset
        long maxSize = 0;
        for (Field f : fields) {
            long offset = u.objectFieldOffset(f);
            if (offset > maxSize) {
                maxSize = offset;
            }
        }

        return ((maxSize/8) + 1) * 8;   // padding
    }
    public static long sizeOf2(Object object){
        return unsafe.getAddress(
                normalize(unsafe.getInt(object, 4L)) + 12L);
    }
    private static long normalize(int value) {
        if(value >= 0) return value;
        return (~0L >>> 32) & value;
    }

    static long toAddress(Object obj) {
        Object[] array = new Object[] {obj};
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        return normalize(unsafe.getInt(array, baseOffset));
    }

    static Object fromAddress(long address) {
        Object[] array = new Object[] {null};
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        unsafe.putLong(array, baseOffset, address);
        return array[0];
    }

    public static class A {
        public long value;
        public char[] d;

    }
}

