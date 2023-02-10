import java.util.*;

public class T {
    static TreeSet<T> m = new TreeSet<>();
    public static void main(String args[]) {
        int i = 0;
        while(++i<15000)test();
    }
    static T test(){
        return new T();
    }
    protected void finalize() { m.add(this);}
}
