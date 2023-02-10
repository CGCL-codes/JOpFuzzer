
/**
 * This snippet crashes with
 *   - Java(TM) SE Runtime Environment (8.0_101-b13) (build 1.8.0_101-b13)
 */
public class JvmCrash {

	interface Base {
		static final Object CONST = new Target(){}.someMethod();
		
		default void important() {
			// Super interfaces with default methods get initialized (JLS 12.4.1)
		}
	}
	
	interface Target extends Base {
		default Object someMethod() {
			throw new RuntimeException();
		}
	}

	public static void main(String[] args) {
		new Target() {};
	}

}

