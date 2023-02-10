public class Iop2 {
    public static void main(String args[]) {
        int shift_bits = 36; //0x24, 0b00100100
        int data = 0x87654321;

        System.out.println(Integer.toHexString(data <<
                shift_bits));
        System.out.println(Integer.toHexString(data >>
                shift_bits));
        System.out.println(Integer.toHexString(data >>>
                shift_bits));
    }
}