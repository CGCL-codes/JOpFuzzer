
import java.lang.reflect.Field;

public class TransientTest {
  public static void main(String[] args) {
    Field[] declaredFields = TestClass.class.getDeclaredFields();

    for (int i = 0; i < declaredFields.length; i++) {
      Field declaredField = declaredFields[i];
      System.out.println("declaredField = " + declaredField);
    }

    System.out.println("-----------------------------------");

    Class aClass = Throwable.class;
    declaredFields = aClass.getDeclaredFields();
    for (int i = 0; i < declaredFields.length; i++) {
      Field declaredField = declaredFields[i];
      System.out.println("declaredField = " + declaredField);
    }
  }
}




