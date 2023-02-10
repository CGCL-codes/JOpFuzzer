import java.util.*;
import java.util.concurrent.*;

public class CopyOnWriteArrayListTest {
    public static void main(String argv[]) {
        CopyOnWriteArrayListTest test = new CopyOnWriteArrayListTest();
        test.testRemove1_IndexOutOfBounds();
        test.testAddAll1_IndexOutOfBoundsException();
    }

    public void testRemove1_IndexOutOfBounds() {
        CopyOnWriteArrayList c = new CopyOnWriteArrayList();
    }

    public void testAddAll1_IndexOutOfBoundsException() {
        try {
            CopyOnWriteArrayList c = new CopyOnWriteArrayList();
            c.addAll(-1, new LinkedList()); // (*)
        } catch (IndexOutOfBoundsException e) {
        }
    }
}