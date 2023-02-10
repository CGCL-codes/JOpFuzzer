// Bug: JDK-8274983
// Author: xxinliu@amazon.com
//
// Description:
// An example to show invokeinterface and its target is a private method.
// The receivers(C1 and C2, ... ) all implement the same interface. 
// Even though the callsite is megamorphic, the target is fixed.
//
// After the hidden class(JDK-8238358), the LambdaMetaFactory generates invokeinterface
// instead of invokespecial. C1 can't recognize the new code pattern, therefore, it generates 
// IC(InlineCache) call.
//
// $java -ea -XX:+TraceCallFixup -XX:TieredStopAtLevel=1 -XX:+PrintC1Statistics -Xbatch InvokePrivateInterfaceMethod
// C1 Runtime statistics:
//  _resolve_invoke_virtual_cnt:     3940
//  _resolve_invoke_opt_virtual_cnt: 150
//  _resolve_invoke_static_cnt:      38
//  _handle_wrong_method_cnt:        3873
//  _ic_miss_cnt:                    31
public class InvokePrivateInterfaceMethod {
    static int counter;

    interface I {
        private void bar() {
            counter++;
        }

        default void foo() {
            bar();
        }
    }

    static class C1 implements I {}
    static class C2 implements I {}

    public static void main(String[] args) {
        I o1 = new C1();
        I o2 = new C2();

        for (int i = 0; i < 4_000; ++i) {
            o1.foo();
            o2.foo();
        }

        assert counter == 4_000 * 2;
    }
}
