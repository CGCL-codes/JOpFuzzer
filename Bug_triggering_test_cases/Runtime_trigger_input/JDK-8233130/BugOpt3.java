
@SuppressWarnings("unchecked")
public class BugOpt3 {

    public static void main(String[] args) {
        BNode<Integer, Integer>[] table = new BNode[0x3fff_ffff];
    }

    /**
     * Basic hash bin node.
     */
    static inline class BNode<K,V> {
        final K key;
        final V value;
	final int hash;

        BNode(K key, V value) {
            this.key = key;
            this.value = value;
	    this.hash = 0;
        }
    }
}
