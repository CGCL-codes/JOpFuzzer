public class Normalize {

    public static void main(String[] args) {
        System.out.println("normalize(-0.0f): " + normalize(-0.0f));
        for (int i = 0; i < 1000000; i++) {
            normalize(-0.0f);
        }
        System.out.println("normalize(-0.0f): " + normalize(-0.0f));
    }

    public static float normalize(float v) {
        if (v == 0.0f) v = 0.0f;
        return v;
    }
}
