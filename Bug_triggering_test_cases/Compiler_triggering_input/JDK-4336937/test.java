import javax.swing.text.*;

class test implements Runnable {

    static int count = 0;
    int id;
    
    public test() {
id = count;
count++;
    }

    public static void main(String args[]) throws Exception {
LayoutQueue q = new LayoutQueue();

for(int i=50; i>0; i--) {
q.addTask(new test());
}
System.exit(0);
    }
    
    public void run() {
System.out.println("Task"+id+".run() called");
    };
    
}