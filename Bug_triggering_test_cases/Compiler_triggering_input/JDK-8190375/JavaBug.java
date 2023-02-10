public class JavaBug { 
  public static void main(String[] args) { 
    for (int i = 0; i < 1000000; i++) { 
      System.out.println(i); 
      String foo = formatPos(i); 
      } 
    } 

    private static String formatPos( int p ) 
    { 
      boolean negative = p < 0; 
      if ( negative ) 
        p = -p; 
      char[] ac = new char[12]; 
      int i = 11; 
      while (p != 0 || i > 3) 
      { 
        ac[i--] = (char) ( '0' + ( p % 10 ) ); 
        p /= 10; 
        if ( i == 5 ) 
          ac[i--] = '.'; 
      } 
      if ( negative ) 
        ac[i--] = '-'; 
      return new String( ac, i + 1, 11 - i ); 
  } 
} 