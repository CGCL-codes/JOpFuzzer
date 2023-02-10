public class Test {
    static {
        init();
    }

    public static void init() {
    }

    public void mainTest() {
        int i = 8;
        while ((i -= 3) > 0);
        System.out.println("i" + i);
    }

    public static void main(String[] args) {
        Test _instance = new Test();
        _instance.mainTest();
        _instance.mainTest();
    }
}
