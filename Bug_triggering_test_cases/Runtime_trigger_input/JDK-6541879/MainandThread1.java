
//*****************************************************\import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainAndThread1 {
	public static void main(String[] args) {
		LockableObject lo = new LockableObject();
		Date d1 = new Date();
		new Thread(new Thread2(lo)).start();
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch(InterruptedException ignore) {}
		lo.method1();
		Date d2 = new Date();
		System.out.println("It took: " + (d2.getTime() - d1.getTime()) + " milliseconds");
		System.exit(0);
	}
}

class Thread2 implements Runnable{
	private LockableObject lo;
	public Thread2(LockableObject lo) {
		this.lo = lo;
	}
	public void run() {
		while (true) {
			lo.method2();
		}
	}
}

class LockableObject {
	public synchronized void method1() {
		System.out.println("method1");
	}
	public synchronized void method2() {
		System.out.println("method2");
//-----------------------------------------------------------------------------------\		try {
			TimeUnit.NANOSECONDS.sleep(5);
		} catch(InterruptedException ignore) {}
//-----------------------------------------------------------------------------------\	}
}
//***************************************************************\