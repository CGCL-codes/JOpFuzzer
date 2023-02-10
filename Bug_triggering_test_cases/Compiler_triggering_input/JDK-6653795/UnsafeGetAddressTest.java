import sun.misc.Unsafe;
public class UnsafeGetAddressTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        long address = unsafe.allocateMemory(unsafe.addressSize());
        unsafe.putAddress(address, 0x0000000080000000L);
        // from sun.misc.Unsafe.getAddress' documentation:
        // "If the native pointer is less than 64 bits wide, it is
        // extended as an unsigned number to a Java long."
        result = unsafe.getAddress(address);
        System.out.printf("1: was 0x%x, expected 0x%x\n", result,
                0x0000000080000000L);
        for (int i = 0; i < 1000000; i++) {
            result = unsafe.getAddress(address);
        }
        System.out.printf("2: was 0x%x, expected 0x%x\n", result,
                0x0000000080000000L);
    }
    static volatile long result;
}