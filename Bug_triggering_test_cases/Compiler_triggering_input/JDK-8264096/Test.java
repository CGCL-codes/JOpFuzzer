class Test {

    public static String concatStringConstU(String a) {
        return new StringBuilder().append(stringSmallU).append(a).append(stringU).toString();
    }

    static final String stringU = "\u0f21\u0f22\u0f23\u0f24\u0f25\u0f26\u0f27\u0f28";
    static final String stringSmallU = "\u0f21\u0f22\u0f23";

    public static void main(String args[]){
        String a = "ABC";
        Test t = new Test();
        for (int i = 0; i < 100_000; i++){
            t.concatStringConstU(a);
        }
    }

}