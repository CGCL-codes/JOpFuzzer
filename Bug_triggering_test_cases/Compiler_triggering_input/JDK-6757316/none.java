public class none {
    public static void main(String[] args) {
        long[] arr = {
                0x11111111aaaaaaaaL,
                0xaaaaaaaa11111111L,
                0x11111111aaaaaaaaL,
                0xaaaaaaaa11111111L
        };
        System.out.println(Long.toHexString(arr[1]));
    }
}