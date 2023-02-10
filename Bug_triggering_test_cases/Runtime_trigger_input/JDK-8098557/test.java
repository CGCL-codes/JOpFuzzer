public class IntfInit {
    interface I {
        int i = IntfInit.out("I::i", 1);
        default void method() { } // causes initialization!
    }
    interface J extends I {
        int j = IntfInit.out("J::j", 2);
    }
    interface K extends J {
        int k = IntfInit.out("K::k", 3);
    }

    public static void main(String[] args) {
        System.out.println(K.k);
    }

    static int out(String s, int i) {
        System.out.println(s + "=" + i);
        return i;
    }
}
