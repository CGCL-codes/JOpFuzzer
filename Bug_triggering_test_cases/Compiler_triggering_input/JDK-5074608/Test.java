class Test {
    static int val = 0;

    public static int foo(int val) {
       return val + 1000;
    }

    public static void test() {
       for (int i = 0; i < 100; i++) {
          val = i;
          if (val == 50) {
             val = foo(val);
          }
       }
    }

    public static void main(String argv[]) {
       for (int i=0; i < 15000; i++)
         test();
       System.out.println("value = " + val);
    }

 }