
public class IntCmp {

    static int REPS =1000000000;
    public static void main(String[] args) {
        long t = System.nanoTime();
        for(int i=0;i++<REPS;) {
            if(i>REPS) {
                System.exit(1);
            }
        }
        t = System.nanoTime()-t;
        System.out.println(t + " nanoseconds");
    }
}
