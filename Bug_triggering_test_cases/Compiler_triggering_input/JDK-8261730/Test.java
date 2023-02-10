class Test {

    MyInteger own;

    void foo(MyInteger arg) {
        for (int i = 0; i < 1; i++) {
            own.val = -arg.val;
            for (int k = 0; k < 10; k++) {
                own.val = 0;
            }
        }
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.own = new MyInteger();
        t.foo(new MyInteger());
    }

}

class MyInteger {
    int val;
}
