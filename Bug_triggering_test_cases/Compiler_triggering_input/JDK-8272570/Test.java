public class Test {
    public boolean bo0;
    public boolean bo1;
    public void foo() {
        int sh12 = 61;
        for (int i = 0; i < 50; i++) {
            sh12 *= 34;
        }
        Math.tan(1.0);
        bo0 = true;
        bo1 = true;
    }
    public static void main(String[] args) {
        Test instance = new Test();
        for (int i = 0; i < 7800; i++) {
            instance.foo();
        }
    }
}

