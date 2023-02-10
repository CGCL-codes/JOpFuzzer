

import java.text.DecimalFormat;
import java.util.Random;

/** Test that show a dramatic slowdown for programs that attempt to use
 * floating point math after a JNI call.
 *
 * @author  bjw  Bruce Walter, Cornell Program of Computer Graphics 2004
 */
public class JNIOpsTest {
  //target total number of repetitions of the operation
  public static final int opTargetRepetitions = 20000000;
  //size of arrays that are operated on
  public static final int arraySize = 10000;
  //number of times we need to process each array to reach total target count
  public static final int reps = opTargetRepetitions/arraySize;
  //pretty print the output numbers to make them easier to read
  public static final DecimalFormat decForm = new DecimalFormat("###0.0");
  public static final DecimalFormat sciForm = new DecimalFormat("0.00E0");
  //my processor is a 1.7GHz Xeon (actually it is a dual processor, but this test is single threaded)
  public static final double ghzProcSpeed = 1.7; //my processor is 1.7GHz
  
  //native function that simply always returns the value 1.0
  public static native float funcStaticVoid();
  
  static {
    System.load("C:/IntelCode/JNITest/JNITest/Release/JNITest.dll");
  }
  
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
      ps = "avg "+ps+" ns   total "+sciForm.format(time/1000.0)+" s";
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
    //set the src array to be random values between zero and one exclusive
    for(int i=0;i<src.length;i++) {
      do {
        src[i] = ran.nextDouble();
      } while (src[i] == 0);
    }
    TestOp tests[] = { new AssignOp(), new MultOp(), new JNIOp(), new JNIMulOp()};
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
  
  public static class MultOp extends TestOp {
    public String toString() { return "mult"; }
    public void performOp(double result[], double src[]) {
      for(int i=0;i<src.length;i++) {
        result[i] = 0.143*src[i];
      }
    }
  }
  
  public static class JNIOp extends TestOp {
    public String toString() { return "JNI"; }
    public void performOp(double result[], double src[]) {
      for(int i=0;i<src.length;i++) {
        JNIOpsTest.funcStaticVoid();
      }
    }
  }
    
  public static class JNIMulOp extends TestOp {
    public String toString() { return "JNI and mult"; }
    public void performOp(double result[], double src[]) {
      for(int i=0;i<src.length;i++) {
        JNIOpsTest.funcStaticVoid();
        result[i] = 0.143*src[i];
      }
    }
  }
 
}

