public class test{
public static void main(String... args) {
    int a = 5;
    int b = 10;
    System.out.printf("a: %s, b: %s\n", a, b);
    swap(addrressOf(a), addressOf(b), Integer.BYTES);
    System.out.printf("a: %s, b: %s\n", a, b);
}

@SuppressWarnings("unchecked") public static <T> long addressOf(T t) throws NoSuchFieldException, IllegalAccessException {
        T[] arr = (T[])  new Object[]{t};
        Unsafe unsafe = getUnsafe();
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(arr, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(arr, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }

        return(objectAddress);
    }

public static void swap(long addr_1, long addr_2, long bytes) throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = getUnsafe();
        if (unsafe == null) {
            System.out.println("Cannot swap variables!");
            return;
        }
        long allocateMemory_var1 = unsafe.allocateMemory(bytes);

        unsafe.copyMemory(addr_1, allocateMemory_var1, bytes);

        unsafe.copyMemory(addr_2, addr_1, bytes);

        unsafe.copyMemory(allocateMemory_var1, addr_2, bytes);

    }

private static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe)f.get(null);
    }
}