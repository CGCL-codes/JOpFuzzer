import java.lang.System;

class TestArrayCopy {
    static void test1() {
        long a0[] = { 0, 0 };
        long a1[] = { 1, 1 };
        System.arraycopy(a1, 0, a0, 0, 2); // should be inlined
        System.out.print(a0[1] == 1 ? "OK\n" : "FAIL\n");
    }

    static void test2() {
        int a10[] = { 0, 0, 0 };
        int a11[] = { 1, 1, 1 };
        System.arraycopy(a11, 0, a10, 0, 3); // should be inlined
        System.out.print(a10[2] == 1 ? "OK\n" : "FAIL\n");
    }

    public static void main(String[] args) {
        int a0[] = { 0 };
        int a1[] = { 0 };
        System.arraycopy(a1, 0, a0, 0, 1); // loads class

// System class should be loaded at the moment,
// test methods should not be inlined
        test1();
        test2();
    }
}
