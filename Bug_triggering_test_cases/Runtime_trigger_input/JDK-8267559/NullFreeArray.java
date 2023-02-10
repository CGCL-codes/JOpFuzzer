
public class NullFreeArray {
    static primitive class Point {
int x = 0, y = 0;
    }

    static void setNull(Object[] a) {
try {
a[0] = null;
} catch(Throwable t) {
System.out.println(t);
}
    }
    
    public static void main(String[] args) {
Point[] a = new Point[10];
setNull(a);
    }
}