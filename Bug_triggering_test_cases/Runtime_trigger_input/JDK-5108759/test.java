
import java.security.AccessController;
import java.security.PrivilegedAction;

public class CallStackTest {

	public static void main(String[] args) {

		AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				Class[] stack = csr.getClassContext();

				for (int i = 0 ; i < stack.length ; i++) {
					System.out.println(stack[i].getName());
				}

				new Exception().printStackTrace();

				return null;
			}
		});
	}

	private static class CallStackResolver extends SecurityManager {

		private CallStackResolver() {
			super();
		}

		protected Class[] getClassContext() {
			return super.getClassContext();
		}
	}

	private static final CallStackResolver csr = new CallStackResolver();
}

