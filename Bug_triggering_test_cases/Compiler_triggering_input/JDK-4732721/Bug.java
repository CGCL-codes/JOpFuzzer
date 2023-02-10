public class Bug {
    public static void main(String[] args) {
        Bug n = new Bug();

        for(int i = 0; i < 1600; i++) {
            System.out.println(i);
            System.out.println(n.get());
        }

    }

    public float get() {
        int z0 = (int)Math.sqrt(3.3f);
        int z1 = z0 + 1;
        int inde3000 = 1+ width1*29 + z0*h1w1;
        int inde2910 = width1 + z0;
        int inde2901 = width1*29;

        float b010=0.76950276f;
        float b110=0.06863946f;
        float b101=0.025501072f;

        float c010=0.017590702f;
        float c110=0.8319669f;
        float c101=0.5642315f;

        float dd010 = -(b010 + c010*z0);
        float dd110 = -(b110 + c110*z0);
        float dd101 = -(b101 + c101*z1);
        float b_ = b110 + b101 + b010;
        float c_ = c110 + c101 + c010;
        return -(z1 + 0.58f);
    }

    private int h1w1;
    private int width1;
}