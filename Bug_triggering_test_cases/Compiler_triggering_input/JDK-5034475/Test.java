//
// Crash the VM in a TableSwitch interpreter codelet
// java -d64 -XX:+PrintCompilation Test prime
//
// To NOT crash, run without -d64
// or without the "prime" flag to avoid hotspotting the getInt method
//
//


class Test {

    private int someInt = -1;

    private int getInt() {

        return someInt;

    }

    private void doTest(int delay) {
        int i = 0xffffffff;

        if (delay==0)
            return;

        i = getInt();
        System.out.println("i= " + i);

        switch (getInt()) {
            case -1:
                System.out.println("was -1");
                break;

            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                System.out.println("was NOT -1");
                break;

            default:
                System.out.println("was NOT -1");
                break;

        } // end switch

        System.out.println("done switch");



    }

    Test(boolean prime) {
        int i=0;

        if (prime) {
            System.out.println("Priming...");
            for (int j=0; j<50000;j++) {
                i = getInt();
                doTest(0);
            }

            delay(2000);
        }

        System.out.println("doing test...");
        doTest(5000);
    }



    public static void main(String args[]) {

        Test t;

        if (args.length>0) {
            t = new Test(true);
        } else {
            t = new Test(false);
        }

    }

    private void delay(int time) {
        try { Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Interrupted!");
            e.printStackTrace();
        }
    }

}
