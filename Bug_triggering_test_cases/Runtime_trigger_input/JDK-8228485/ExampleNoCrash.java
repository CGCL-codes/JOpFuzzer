class ExampleNoCrash {
    public static void main(String[] args) {
        try {
            StaticInit.get();
        } catch (Exception e) {
            // nop
        }
        Example.main(args);
    }
}
