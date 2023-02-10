import java.util.ArrayList;
import java.util.List;


public class Hs {
    void foo(A a, A[] as) {
        for (A a1 : as) {
            B[] filtered = a.c(a1);
            for (B b : filtered)
                if (b == null) {
                    System.out.println("bug");
                }
        }
    }

    public static void main(String[] args) {
        List<A> as = new ArrayList<A>();
        for (int i = 0; i < 5000; i++) {
            List<B> bs = new ArrayList<B>();
            for (int j = i; j < i + 1000; j++)
                bs.add(new B(j));
            as.add(new A(bs.toArray(new B[0])));
        }
        new Hs().foo(as.get(0), as.subList(1, as.size()).toArray(new A[0]));
    }
}

class A {
    final B[] bs;

    public A(B[] bs) {
        this.bs = bs;
    }

    final B[] c(final A a) {
        return new BoxedArray<B>(bs).filter(new Function<B, Boolean>() {
            public Boolean apply(B arg) {
                for (B b : a.bs) {
                    if (b.d == arg.d)
                        return true;
                }
                return false;
            }
        });
    }
}

class BoxedArray<T> {

    private final T[] array;

    BoxedArray(T[] array) {
        this.array = array;
    }

    public T[] filter(Function<T, Boolean> function) {
        boolean[] include = new boolean[array.length];
        int len = 0;
        int i = 0;
        while (i < array.length) {
            if (function.apply(array[i])) {
                include[i] = true;
                len += 1;
            }
            i += 1;
        }
        T[] result = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), len);
        len = 0;
        i = 0;
        while (len < result.length) {
            if (include[i]) {
                result[len] = array[i];
                len += 1;
            }
            i += 1;
        }
        return result;
    }
}

interface Function<T, R> {
    R apply(T arg);
}

class B {
    final int d;
    public B(int d) {
        this.d = d;
    }
}