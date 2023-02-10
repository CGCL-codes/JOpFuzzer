import java.util.concurrent.atomic.AtomicReference;

public class TestUnsafe extends AtomicReference<Node>{
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            Node n1 = new Node(i);
            Node n2 = new Node(i+1);
            TestUnsafe t = new TestUnsafe(n1);
            Node old = t.foo(n2);

            if(old.next.v > 20000) {
                System.out.println("not enter here" + old.next.v);
            }
        }
    }
    TestUnsafe(Node n) {super(n);}

    public Node foo(Node n) {
        Node old;
        old = this.getAndSet(n); // inline sun.misc.Unsafe::getAndSetObject here
        old.next = n;
        return old;
    }
}
class Node
{
    int v;
    Node next;
    Node(int i) {v = i; next = null;}
}