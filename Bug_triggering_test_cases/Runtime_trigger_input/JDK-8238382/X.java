final inline class Y {
    int y1;
    int y2;

    Y(int y1, int y2) {
        this.y1 = y1;
        this.y2 = y2;
    }

    Y() {
        this(1, 2);
    }
}

public final inline class X {

    int x;

    X(int x) {
        this.x = x;
    }

    X() {
        this(42);
    }
   
    int id() {
        return System.identityHashCode(this);
    }

    public static void main(String [] args) {

        X[] xa = { new X(), new X(), new X(10), new X(10), X.default, X.default };

        for (X x : xa) {
            System.out.println("X instance = " + x);
            System.out.println("X hashCode = " + x.hashCode());
            System.out.println("X IDhashCode = " + System.identityHashCode(x));
        }
        System.out.println("X IDhashCode = " + System.identityHashCode(new Y()));
        System.out.println("X IDhashCode = " + System.identityHashCode(new Y(20, 30)));
 
    }
}
