
import sun.misc.Unsafe; 

import java.lang.reflect.Field; 

public class Test { 

    public static void main(String[] args) throws Exception { 
        Unsafe unsafe = getUnsafe(); 
        // 10000 pass 
        // 100000 jvm crash 
        // 1000000 fail 
        int count = 100000; 
        long size = count * 8L; 
        long baseAddress = unsafe.allocateMemory(size); 

        for (int i = 0; i < count; i++) { 
                long address = baseAddress + (i * 8L); 

                long expected = i; 
                unsafe.putLong(address, expected); 

                long actual = unsafe.getLong(address); 

                if (expected != actual) { 
                    throw new AssertionError("Expected: " + expected + ", Actual: " + actual); 
                } 
        } 
    } 

    private static Unsafe getUnsafe() { 
        try { 
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe"); 
            theUnsafe.setAccessible(true); 
            Unsafe unsafe = (Unsafe) theUnsafe.get(null); 
            return unsafe; 
        } catch (Exception e) { 
            throw new RuntimeException(e); 
        } 
    } 
