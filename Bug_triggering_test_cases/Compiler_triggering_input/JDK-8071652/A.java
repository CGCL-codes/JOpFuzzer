public class A {
  public static void main(String[] args) {
    new A().add();
    new AA().add();
    new AAA().add();
  }

  public void add() { }
}

class AA {
  public void add() { }  
}


class AAA {
  public void add() { }

}
