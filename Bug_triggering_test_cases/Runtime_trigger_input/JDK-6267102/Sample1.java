
package test;

public class Sample1 {
    public static void main(String[] args) {
        String hello = "Hello", lo = "lo";
        System.out.println(hello == "Hello");
        System.out.println(Other.hello == hello) ;
        System.out.println(test.Other.hello == hello);
        System.out.println(hello == ("Hel"+"lo"));
        System.out.println(hello == ("Hel"+lo));
        System.out.println(hello == ("Hel"+lo).intern());
    }
}

class Other {
    static String hello = "Hello";
}


In another package.i.e. other:

package other;

public class Other {
    static String hello = "Hello";
}
