
public class ClassLoaderHang {
  public static void main(String args[]){
    Thread thread1 =
            new Thread(
                    new Runnable(){
                      public void run(){
                        ChildA a = new ChildA();
                      }
                    });

    Thread thread2 =
            new Thread(
                    new Runnable(){
                      public void run(){
                        ChildB b = new ChildB();
                      }
                    });

    thread1.start();
    thread2.start();
  }
}

class Parent {
  static{
    Class bClass = ChildB.class;
  }
}

class ChildA extends Parent {
  static{
    Class bClass = ChildB.class;
  }
}

class ChildB extends Parent {
  static{
    Class parentClass = Parent.class;
  }
}

