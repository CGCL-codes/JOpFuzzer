
public class TimeSlicing {
	Object gate = new Object();
	volatile long c1;
	volatile long c2;


	public static void main(String args[]) throws InterruptedException {
		new TimeSlicing().go();
	}

	void go() throws InterruptedException {
		new Thread() {
			public void run () {
				while (true) {
					synchronized (gate) {
						c1++;
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}.start();

		new Thread() {
			public void run () {
				while (true) {
					synchronized (gate) {
						c2++;
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}.start();


		while (true) {
			System.out.println("------------");
			System.out.println("c1= " + c1);
			System.out.println("c2= " + c2);
			Thread.sleep(1000);
		}
	}
}

