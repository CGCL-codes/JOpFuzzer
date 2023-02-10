import java.lang.reflect.Constructor;

public class Test {
    public static void main(String[] args) throws Exception {
        Constructor<String> c = String.class.getDeclaredConstructor(byte[].class, byte.class);
        c.setAccessible(true);

        byte[] bytes = new byte[] {
                'Y', 'm', '_', 'l', 'V', 'n', 'W', 'S', 'w', 'm', 'W', 'S'
        };

        String s1 = c.newInstance(bytes, (byte) 0);
        String s2 = c.newInstance(bytes, (byte) 1);
        System.out.println(s1.compareTo(s2));
    }
}