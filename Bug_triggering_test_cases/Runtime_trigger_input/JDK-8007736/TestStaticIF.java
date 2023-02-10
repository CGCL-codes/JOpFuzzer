/**
 *
 * @author rriggs
 */
public class TestStaticIF implements StaticMethodInInterface {

    public static void main(String[] args) {
        System.out.printf("main: %s%n", StaticMethodInInterface.get());
    }
}
