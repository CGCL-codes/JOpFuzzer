@SuppressWarnings("unchecked")
public class BugOpt1 {

    private BNode<Integer,Integer>[] table;

    public static void main(String[] args) {
        BugOpt1 st = new BugOpt1();
        st.table = (BNode<Integer,Integer>[])new BNode[1];
        st.table[0] = new BNode<Integer,Integer>(Integer.valueOf(0), Integer.valueOf(99));
        Object o = st.test6a(Integer.valueOf(0), Integer.valueOf(10));
        if (o == null) {
            throw new AssertionError("return value should be 99");
        }
    }

    Integer test6a(Integer k, Integer v) {
        int index = k.intValue();
        if (index >= 0 && table[index].value != null) {
//            System.out.println();     // With this change, the correct value is returned.
            return table[index].value;
        }
        return v;
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
}
