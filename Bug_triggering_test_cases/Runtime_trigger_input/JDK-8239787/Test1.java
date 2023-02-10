public class Test1 {
  public static void main(String ... str) {

    System.out.println("Begin");

    for (int i = 0 ; i < 100000; i ++) {
      String emptyString = "";
      for(int c=0; c<0xFFFF; c++) {
        int dot = emptyString.indexOf((char)c, -1);
        if (dot != -1) {
          System.out.println("indexOf returned index " + dot);
        }
      }
    }

    System.out.println("End");
  }
}
