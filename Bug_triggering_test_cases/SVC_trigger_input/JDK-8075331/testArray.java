import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.util.List;

public class testArrays {

    public static void main(String[] args) {
        MouseAdapter mouseAdapter1 = new MouseAdapter(){};
        MouseAdapter mouseAdapter2 = new MouseAdapter(){};
        MouseAdapter[] array = new MouseAdapter[]{mouseAdapter1, mouseAdapter2};
        List<MouseAdapter> list = Arrays.asList(array);
        System.out.println("list : " + list);
        System.out.println("list.size() : " + list.size());
        System.out.println("Arrays.asList(array) : " + Arrays.asList(array));
        System.out.println("Arrays.asList(array).size() : " +
                Arrays.asList(array).size());
// breakpoint here, evaluate Arrays.asList(array).size()

    }
}