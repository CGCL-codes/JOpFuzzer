public class fp {
    public static void main(String[] args) throws InterruptedException {
         double mod = Double.longBitsToDouble(0x7FE0000000000000L) % Double.longBitsToDouble(0x0000000000040000L);
         if (Double.isNaN(mod)) {
            throw new Error("Saw a NaN, fail");
         }
    }
}
