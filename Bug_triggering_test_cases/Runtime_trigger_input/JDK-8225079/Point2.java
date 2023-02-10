import java.lang.reflect.Constructor;

public inline class Point2 {
    public static void main(String[] args) throws Exception {
        Constructor<?> c = Point2.class.getConstructor();
        c.newInstance();
    }

    public int x;
    public int y;
    public Point2 () {
        x = 10;
        y = 20;
    }
}