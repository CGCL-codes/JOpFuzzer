public class BugOpt2 {

    public static void main(String[] args) {
        BNode<Integer, Integer>[] table = new BNode[0x40000000];
        Object k = table[0].key;
        hash(k);
    }

    /**
     * Basic hash bin node.
     */
    static inline class BNode<K,V> {
        final K key;
        final V value;

        BNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}

