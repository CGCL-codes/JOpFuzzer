
public class UseStringCacheBug {

    class Task implements Runnable {

        Task(String name) {
            this.objName = name;
        }

        String objName;
        public void run() {

            while(true) {
                final String threadName = Thread.currentThread().getName();
                final int len = threadName.length();
                final String objNameLocal = objName;
                final int len2 = objNameLocal.length();
                Thread.yield();
                char lastChar2 = objNameLocal.charAt(len2-1);
                char lastChar = threadName.charAt(len-1);
            }
        }
    }

    public void reproduceBug() {
        Thread one = new Thread(new Task( " FirstThread " ),  " FirstThread " );
        Thread two = new Thread(new Task( " SecondThread " ),  " SecondThread " );
        Thread three = new Thread(new Task( " ThirdThread " ),  " ThirdThread " );

        one.start();
        two.start();
        three.start();
    }


    public static void main(String[] args) {
        UseStringCacheBug test = new UseStringCacheBug();
        test.reproduceBug();
    }
}

