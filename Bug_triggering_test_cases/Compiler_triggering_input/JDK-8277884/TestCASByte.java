import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

// run with:
// build/linux-riscv64-server-release/images/jdk/bin/java -ea -XX:-TieredCompilation -XX:CompileCommand=compileonly,jdk.internal.misc.Unsafe::compareAndSetByte TestCASByte
// assert will fail.
// if we add '-XX:+UnlockDiagnosticVMOptions -XX:DisableIntrinsic=_compareAndExchangeByte' this test will pass.

public class TestCASByte {

    byte[] array = new byte[1];

    static final VarHandle vh;
    static {
        try {
            vh = MethodHandles.arrayElementVarHandle(byte[].class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void test() {
        for (int i = 0; i < 100_000; i++) { // To level 4
            // clear
            array[0] = 0;

            byte res1 = (byte)vh.getAndSet(array, 0, (byte)-1);
            byte res2 = (byte)vh.getAndSet(array, 0, (byte)2);

            assert res1 == 0 && res2 == -1;
        }
    }

    public static void main(String[] args) {
        new TestCASByte().test();
    }

}