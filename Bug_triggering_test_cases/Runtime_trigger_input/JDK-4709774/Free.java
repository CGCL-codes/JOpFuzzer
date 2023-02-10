
public class Free {
        private Vector v;
        private Free otherFree;
        private Free otherFree2;
        private int foo[] = new int[1000000];

        public Free() { }
        public Free woops() {
            this.otherFree = new Free();
            this.otherFree2 = new Free();

            this.otherFree.otherFree = this;
            this.otherFree.otherFree2 = this.otherFree2;

            this.otherFree2.otherFree = this;
            this.otherFree2.otherFree2 = this.otherFree;

            return this.otherFree;
        }
        protected void finalize() throws Throwable {
            System.out.print(".");
        }
        public static void main (String args[]) throws Exception {
                Free root = null;
                Free f = null;
                for (int i = 0 ; i < 1000 ; ++i) {
                    f = new Free();
                    root = f.woops();
                    System.gc();
                    System.runFinalization();
                    System.gc();
                }
                root = null;
                f = null;
                System.out.println("");
                System.out.println("sleep");
                Thread.sleep(100000);
        }

}

