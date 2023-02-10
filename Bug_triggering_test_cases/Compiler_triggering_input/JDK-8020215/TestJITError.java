import java.util.ArrayList;
import java.util.List;

public class TestJITError {
    public static class NamedObject {
        public int id;
        public String name;
        public NamedObject(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static class NamedObjectList {
        public List<NamedObject> namedObjectList = new ArrayList<NamedObject>();

        public NamedObject getBest(int id) {
            NamedObject bestObject = null;
            for (NamedObject o : namedObjectList) {
                bestObject = id==o.id ? getBetter(bestObject, o) : bestObject;
            }
            return bestObject != null ? bestObject : null;
        }

        private static NamedObject getBetter(NamedObject p1, NamedObject p2) {
            return p1 == null ? p2 : (p2 == null) ? p1 : p2.name.compareTo(p1.name)>=0 ? p2 : p1;
        }
    }

    public static void main(String[] args) {
// setup
        NamedObjectList b = new NamedObjectList();
        for (int i = 0; i < 10000; i++) {
            b.namedObjectList.add(new NamedObject(1, " 2012-12-31 " ));
        }
        b.namedObjectList.add(new NamedObject(2, " 2013-12-31 " ));

// execution
        for (int i = 0; i < 100000; i++) {
            NamedObject x = b.getBest(2);
// test
            if (x == null) {
                throw new RuntimeException( " x should never be null here!! (i= " + i + " ) " );
            }
        }
        System.out.println( " finished " );
    }
}
