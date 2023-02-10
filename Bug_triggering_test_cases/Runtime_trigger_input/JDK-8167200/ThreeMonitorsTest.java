interface Thingy {
    public boolean equals(Object o);
    public int serial();
}

public class ThreeMonitorsTest {

    static int nextSerial;

    static class ThingyImpl implements Thingy{
        private int serial;

        public int serial() { return serial; }

        ThingyImpl() {
            serial = nextSerial++;
        }

        public boolean equals(Object o) {
            if (o instanceof Thingy)
                return serial() == ((Thingy)o).serial();
            else
                return false;
        }
    }

    public void foo(Thingy thing) {
        synchronized(thing1) {
            synchronized(thing2) {
                synchronized(thing3) {
                    if (thing1.equals(thing)) {
                        throw new RuntimeException();
                    }
                }
            }
        }
    }

    Thingy thing1, thing2, thing3;

    public void run() {
        thing1 = new ThingyImpl();
        thing2 = new ThingyImpl();
        thing3 = new ThingyImpl();
        Thingy thing = new ThingyImpl();
        foo(thing);
    }


    public static void main(String[] args) {
        new ThreeMonitorsTest().run();
    }
}
