class CalcError {
    private double m;
    private double b;

    public static double log10(double x) {
        return Math.log(x) / Math.log(10);
    }

    void calcMapping(double xmin, double xmax, double ymin, double ymax) {
        if ((ymax != ymin) && (xmax != xmin) && (xmax > 0) && (xmin > 0)) {
            m = (ymax - ymin) / (log10(xmax) - log10(xmin));
            b = (log10(xmin) * ymax - log10(xmax) * ymin)
                    / (log10(xmin) - log10(xmax));
        } else {
            m = 1;
            b = 0;
        }
        System.out.println("m=" + m +", b=" +b);
    }

    public static void main(String[] args) {
        final int LOOP = 1600;
        CalcError c = new CalcError();
        for (int i = 0; i < LOOP; i++) {
            System.out.print("[" + i + "]: ");
            c.calcMapping(91, 121, 177, 34);
        }
    }
}