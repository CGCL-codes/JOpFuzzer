class Test {
    public static void main(String[] k) throws Exception {
        // Execute test in own thread because it contains an infinite loop
        Thread thread = new Thread() {
            public void run() {
                Test m = new Test();
                m.infiniteLoop();
            }
        };
        thread.setDaemon(true);
        thread.start();
        // Give thread some time to do compilation
        Thread.sleep(5000); // should eventually use Utils.adjustTimeout(5000) from jdk.test.lib.Utils
    }

    void infiniteLoop() {
        Helper.doNothing();
        for (int j = 0; ; ) { // endless loop
            doNothing();
        }
    }
    
    void doNothing() {
    }
}

class Helper {
    public static void doNothing() {
    }
}
