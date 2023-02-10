class TSB {
    public static void main (String[] args) {
        while(true) {
            System.out.println(stringmakerBUG());
        }
    }

    public static String stringmakerBUG() {
        try {
            return new StringBuffer(null).toString();
        } catch (NullPointerException e) {
            return "NPE";
        }
    }
}