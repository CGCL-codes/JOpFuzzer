import java.util.*;

public class VectorIntegerTest {
    static Random rnd = new Random();
    public static void main(String[] args) throws Exception {
        List<Integer> list1 = new Vector<Integer>();
        AddRandoms(list1, 400);
        List<Integer> list2 = new Vector<Integer>();
        AddRandoms(list2, 400);

        List<Integer> copyofs2 = new Vector<Integer>();
        copyofs2.addAll(list2);

        if (!list2.equals(copyofs2))
            throw new Exception("Exception");

        list1.clear();
        list1.addAll(0,list2);

        if (!(list1.equals(list2) && list2.equals(list1)))
            throw new Exception ("Exception");

        if (!(list1.equals(list2) && list2.equals(list1)))
            throw new Exception("Exception");

        List<Integer> l = new Vector<Integer>();
        AddRandoms(l,400);
        Integer [] ia = l.toArray(new Integer[0]);
        if (!l.equals(Arrays.asList(ia)))
            throw new Exception("Exception");
    }

    static void AddRandoms(List<Integer> s, int n) throws Exception {
        for (int i=0; i<n; i++) {
            int r = rnd.nextInt() % n;
            Integer e = new Integer(r < 0 ? -r : r);
            s.add(e);
        }
    }
}