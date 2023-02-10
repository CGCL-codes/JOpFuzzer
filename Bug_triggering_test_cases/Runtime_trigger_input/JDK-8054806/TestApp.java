



public class testApp {

    public static void main(String[] args) {
        TestFinal tst = new TestFinal(false, false, false, false, false, false, false, false, false, false, false);
        long l1 = System.currentTimeMillis() / 1000;
        for (int i = 0; i < 1000000000; i++) {
            tst.method1();
        }
        long l2 = System.currentTimeMillis() / 1000;
        for (int i = 0; i < 1000000000; i++) {
            tst.method2();
        }
        long l3 = System.currentTimeMillis() / 1000;
        System.out.println("method 1 " + (l2 - l1));
        System.out.println("method 2 " + (l3 - l2));
    }
}


