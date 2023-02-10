
package mypackage;
class MyPackagePrivateBaseClass {

    public void doSomething(String input) {
        System.out.println(input);
    }
}

package mypackage;
public class MyPublicClass extends MyPackagePrivateBaseClass {
}

import java.util.Arrays;
import java.util.List;

import mypackage.MyPublicClass;

public class Test {
    public static void main(String[] args) {
        doesWork();
        doesNotWork();
    }

    public static void doesNotWork() {
        MyPublicClass victim = new MyPublicClass();
        List<String> items = Arrays.asList("first", "second", "third");
        items.forEach(victim::doSomething);
    }

    public static void doesWork() {
        MyPublicClass victim = new MyPublicClass();
        List<String> items = Arrays.asList("first", "second", "third");
        for (String item : items) {
            victim.doSomething(item);
        }
    }
}
