class Reduced {
    static class Int {
        int val;
    }
    static Int[] global = {new Int()};
    static void foo(Int a, Int b) {
        global[0].val = a.val + b.val * 31;
        global[0].val = 0;
        return;
    }
    public static void main(String[] args) {
        Int v = new Int();
        while (true) {
            foo(v, v);
        }
    }
}
