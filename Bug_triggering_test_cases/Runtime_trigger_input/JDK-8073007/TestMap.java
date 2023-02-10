
import java.util.LinkedHashMap;
import java.util.Map;
public class TestMap {
public static void main(String[] args) {
   Map<String, Integer> map = new LinkedHashMap<String, Integer>();
    map.put("a", 12);
    map.put("b", 11);
    map.put("c", 3);
    for (String key : map.keySet()) {
        System.out.println(map.get(key));
    }
}

}
