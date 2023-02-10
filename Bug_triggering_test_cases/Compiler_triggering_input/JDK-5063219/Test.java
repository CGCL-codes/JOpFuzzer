class Test{

    static void goToHeaven(double arg){

      StringBuffer buf = new StringBuffer();

      if (arg < 0.0d){
         buf.append('-');
         arg = -arg;
      }

      int i3 = (int)(arg * 2.0d);

      buf.append(i3);

    }

    public static void main(String[] args){

    // force compilation
      (new StringBuffer()).append(1);

    // Now, go to heaven
       goToHeaven(0.5);

    }

}
