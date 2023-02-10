public class Test {

    public static void main (String[] args) {
    Mythread thread1, thread2;
    thread1 = new Mythread();
    thread2 = new Mythread();
    thread1.start();
    thread2.start();
    }
    }
    
    class Mythread extends Thread {
    int i=0;
    
    public void run() {
    while (true) {
    i++;
    }
    }
    }