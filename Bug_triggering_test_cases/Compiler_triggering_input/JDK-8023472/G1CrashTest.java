class G1CrashTest {
    static Object[] set = new Object[11];

    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < Integer.getInteger("count"); j++) {
            Object key = new Object();
            insertKey(key);
            if (j > set.length / 2) {
                Object[] oldKeys = set;
                set = new Object[2 * set.length - 1];
                for (Object o : oldKeys) {
                    if (o != null)
                        insertKey(o);
                }
            }
        }
    }

    static void insertKey(Object key) {
        int hash = key.hashCode() & 0x7fffffff;
        int index = hash % set.length;
        Object cur = set[index];
        if (cur == null)
            set[index] = key;
        else
            insertKeyRehash(key, index, hash, cur);
    }

    static void insertKeyRehash(Object key, int index, int hash, Object cur) {
        int loopIndex = index;
        int firstRemoved = -1;
        do {
            if (cur == "dead")
                firstRemoved = 1;
            index--;
            if (index < 0)
                index += set.length;
            cur = set[index];
            if (cur == null) {
                if (firstRemoved != -1)
                    set[firstRemoved] = "dead";
                else
                    set[index] = key;
                return;
            }
        } while (index != loopIndex);
        if (firstRemoved != -1)
            set[firstRemoved] = null;
    }
}
