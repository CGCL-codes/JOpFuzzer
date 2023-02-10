class ExceptionDeoptTarg {
    int aField;

    public static void main(String[] args) {
        try {
            for (int i = 0; true; ++i) {
                if (i > 30000) {
                    thrower();
                }
            }
        } catch (Throwable exc) {
            System.out.println("Got exception: " + exc);
        }
        System.out.println("Goodbye from ExceptionDeoptTarg!");
    }
    static void thrower() throws Exception {
        throw new NumberFormatException("Too much");
    }
}