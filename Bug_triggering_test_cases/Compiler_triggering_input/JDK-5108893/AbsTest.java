import java.text.DecimalFormat;
import java.util.Random;

/** Test that show that Math.abs is much slower than a floating point add
 * even though it should have about the same cost
 *
 * @author bjw Bruce Walter, Cornell Program of Computer Graphics 2004
 */
public class AbsTest {
    //target total number of repetitions of the operation
    public static final int opTargetRepetitions = 200000000;
    //size of arrays that are operated on
    public static final int arraySize = 10000;
    //number of times we need to process each array to reach total target count
    public static final int reps = opTargetRepetitions/arraySize;
    //pretty print the output numbers to make them easier to read
    public static final DecimalFormat decForm = new DecimalFormat("###0.0");
    public static final DecimalFormat sciForm = new DecimalFormat("0.00E0");
    //my processor is a 1.7GHz Xeon (actually it is a dual processor, but this test is single threaded)
    public static final double ghzProcSpeed = 1.7; //my processor is 1.7GHz

    public static void runTimingTest(TestOp op, double result[], double src[], boolean print) {
        long time = System.currentTimeMillis();
        for(int i=0;i<reps;i++) {
            op.performOp(result,src);
        }
        time = System.currentTimeMillis() - time;
        double denom = 1000000.0/(reps*src.length);
        if (print) {
            String ps = decForm.format(time*denom);
            while (ps.length()<6) ps = " "+ps;
            ps = "avg "+ps+" ns total "+sciForm.format(time/1000.0)+" s";
            while (ps.length()<32) ps += " ";
            ps = ps+" for "+op.toString();
            while (ps.length()<50) ps += " ";
            System.out.println(ps+"\t(~ "+decForm.format(time*denom*ghzProcSpeed)+" cycles)");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        double src[] = new double[arraySize];
        double result[] = new double[arraySize];
        Random ran = new Random(5232482349538L);
        //set the src array to be random values between -1 and 1 (but excluding zero)
        for(int i=0;i<src.length;i++) {
            do {
                src[i] = 2*ran.nextDouble() - 1.0;
            } while (src[i] == 0);
        }
        TestOp tests[] = { new AssignOp(), new AddOp(), new AbsOp()};
        //warm up hotspot
        for(int i=0;i<tests.length;i++) {
            runTimingTest(tests[i],result,src,false);
        }
        //now run the real tests and print the timings
        for(int i=0;i<tests.length;i++) {
            runTimingTest(tests[i],result,src,true);
        }
        //do it again to show the timings are reasonably consistent
        for(int i=0;i<tests.length;i++) {
            runTimingTest(tests[i],result,src,true);
        }
    }

    public abstract static class TestOp {
        public abstract void performOp(double result[], double src[]);
    }

    public static class AssignOp extends TestOp {
        public String toString() { return "assign"; }
        public void performOp(double result[], double src[]) {
            for(int i=0;i<src.length;i++) {
                result[i] = src[i];
            }
        }
    }

    public static class AddOp extends TestOp {
        public String toString() { return "add"; }
        public void performOp(double result[], double src[]) {
            for(int i=0;i<src.length;i++) {
                result[i] = 0.143+src[i];
            }
        }
    }

    public static class AbsOp extends TestOp {
        public String toString() { return "Math.abs()"; }
        public void performOp(double result[], double src[]) {
            for(int i=0;i<src.length;i++) {
                result[i] = Math.abs(src[i]);
            }
        }
    }

}