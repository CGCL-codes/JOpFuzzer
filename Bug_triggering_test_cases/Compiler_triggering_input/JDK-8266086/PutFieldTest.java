public class PutFieldTest {
    static primitive class Point {
        int x,y;
        public Point() {
            x = 0; y = 0;
        }
        public Point(int x, int y) {
            this.x = x; this.y = y;
        }
    }

    Point p;

    static void test() {
        Point p = new Point(4,5);
        PutFieldTest test = new PutFieldTest();
        assert test.p.x == 0;
        assert test.p.y == 0;
        test.p = p;
        System.out.println(test.p);
        assert test.p.x == 4;
        assert test.p.y == 5;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test();
        }
    }
}