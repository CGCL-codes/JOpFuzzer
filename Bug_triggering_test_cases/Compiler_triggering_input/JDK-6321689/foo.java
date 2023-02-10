class foo {
    public static void main(String argv[]) {
        System.out.println("Warmup");
        foo F = new foo();
        char [] C = new char[100];
        for( int i=0; i<100000; i++ ) {
            String s = Integer.toString(i);
            s.getChars(0,s.length(),C,0);
            F.insert(C);
        }
        System.out.println("Done ");
    }

    private final Node ROOT = new Node();
    private static class Node {
        final Node _next[] = new Node[128];
    }

    public Node insert(char [] C) {
        int len = C.length;
        Node node = ROOT;
        int j = 0;

        for(; j < len; j++) {
            Node node1 = node._next[C[j]];
            if( node1 == null ) break;
            node = node1;
        }

        for(; j < len; j++) {
            Node node2 = new Node();
            node._next[C[j]] = node2;
            node = node2;
        }

        return node;
    }
}
