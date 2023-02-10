import java.util.ArrayList;
import java.util.Arrays;

public class AV64bit {

    private static AV64bit oFactoryInstance = new AV64bit();
    public static StringBuffer acquire(int requested) {
        return (StringBuffer) oFactoryInstance.acquireObject(requested);
    }

    private final int[] mPoolCapacities = { 32 + 1, 64 + 1, 128 + 1, 256 + 1, 1024 + 1, 2 * 1024 + 1, 4 * 1024 + 1, 6 * 1024 + 1, 10 * 1024 + 1, 32 * 1024 + 1, 60 * 1024 + 1, 100 * 1024 + 1 };
    private final int[] mPerCapacityPoolSize = { 10, 10, 50, 100, 100, 100, 10, 100, 100, 50, 10, 10 };

    @SuppressWarnings("unchecked")
    private ArrayList[] mPoolLists = new ArrayList[mPoolCapacities.length];
    private long[] mCreateCount = new long[mPoolCapacities.length];
    private long[] mReuseCount = new long[mPoolCapacities.length];
    private long[] mTAL = new long[mPoolCapacities.length];
    private int iOverAlloc;
    private AV64bit() {
        Arrays.fill(mTAL, System.currentTimeMillis());
    }
    @SuppressWarnings("unchecked")
    private Object acquireObject(int requested) {
        int which = getWhich(requested);

        int capacity;
        if (which > -1) {
            capacity = mPoolCapacities[which];

            ArrayList list = null;
            synchronized (mPoolLists) {
                if (mPoolLists[which] == null) {
                    mPoolLists[which] = new ArrayList(mPerCapacityPoolSize[which]);
                }
                list = mPoolLists[which];
            }

// remove a Product from the allocations list,
            synchronized (list) {
                int size = list.size();
                if (size > 0) {
                    mReuseCount[which]++;
                    return list.remove(--size);
                }
            }
        } else {
            capacity = requested;
        }

// count allocations as we go for reporting
        if (which == -1)
            iOverAlloc++;
        else
            mCreateCount[which]++;

        return newProduct(capacity);
    }
    private int getWhich(int capacity) {
        if (capacity == 0)
            return 0; // return FIRST bucket (zero-based)

        for (int i = 0; i < mPoolCapacities.length; i++)
            if (capacity <= mPoolCapacities[i])
                return i;

        return -1;
    }
    private Object newProduct(int capacity) {
        return new StringBuffer(capacity);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            StringBuffer buffer = AV64bit.acquire(1024);
            System.out.printf("Running test %5d -> %JniStaticContextFloat.c\n", i, buffer.hashCode());
        }
    }

}
