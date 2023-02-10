
interface A {
    default int getOne() {
        return 1;
    }
}

interface B extends A {
    default int getOne() {
        return 2;
    }
}

abstract class Abstract implements B {
}

class Impl extends Abstract {
    public int getOne() {
        return 3;
    }
}

class Impl2 extends Impl {
    public int getOne() {
        return 4;
    }
}

public class ImplTest {
    public static void main(String[] args) {
        System.loadLibrary("impl");
        Impl2 i = new Impl2();
        test(i);
    }
    static native void test(Impl i);
}
