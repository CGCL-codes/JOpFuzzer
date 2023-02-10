class d { int i = 2; }

class c extends d {
int init() { i += i; return i; }
int i1 = init();
}

class b extends c {
int i2 = init();
}

class a extends b {
int i3 = init();
}

public class test {
  public static void main(String argv[]) {
  
a f = new a();

System.out.println("i = " + f.i);
System.out.println("i1 = " + f.i1);
System.out.println("i2 = " + f.i2);
System.out.println("i3 = " + f.i3);
  }
}