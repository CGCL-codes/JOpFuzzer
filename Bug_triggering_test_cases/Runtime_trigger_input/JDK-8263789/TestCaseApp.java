public class TestCaseApp {
	public static void main(String args[]) {
		doStuff(new Thread("some thread") {
			public void run() {

				System.out.println("hello");
			}
		});
	}

	public static void doStuff(Thread t) {
		t.start();
	}

}