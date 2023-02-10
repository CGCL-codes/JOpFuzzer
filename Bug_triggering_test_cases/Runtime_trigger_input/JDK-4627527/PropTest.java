
public class PropTest {
public static void main(String[] args) {
    while ( true) {
      java.util.Properties props = new java.util.Properties(
System.getProperties() );
      System.setProperties( props );
      props = null;
    }
  }
}
