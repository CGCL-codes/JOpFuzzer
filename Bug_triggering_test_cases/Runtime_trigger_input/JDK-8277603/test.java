public class OtherClass {
    static primitive class SecretClass {
byte b = 0;
    }
}
public primitive class AccessCheckTest {
    short s = 0;
    OtherClass.SecretClass sc = new OtherClass.SecretClass();

    void print() {
System.out.println("s="+s+" sc.b="+sc.b);
    }

    public static void main(String[] args) {
AccessCheckTest[] array = new AccessCheckTest[10];
array[0].print();
    }
}