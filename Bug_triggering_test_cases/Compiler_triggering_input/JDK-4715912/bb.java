class foo {
    foo g;
}

class bar {
    void setf(foo f) {
        if (f == null) {
            System.out.println("null");
        } else {
            f.g = f.g;
        }
    }
}

public class bb {
    public static void main(String args[]) {
        bar b = new bar();
        foo f = new foo();
        b.setf(f);
    }
}

