
import java.util.*;
import java.util.stream.*;

public class InfiniteSum {
    
    public static void yeOldeWaye() {
        double sum = 0;
        for (double n = 1; ; n++) {
            double term = 1 / (n * n);
            if (Math.abs(term) <= 1e-12 * Math.abs(sum)) {
                break;
            }
            sum += term;
        }
        System.out.println(sum);
    }

    public static double infiniteSum1(DoubleStream ds) {
        double sum = 0;
        PrimitiveIterator.OfDouble it = ds.iterator();
        while (true) {
            double term = it.next();
            if (Math.abs(term) <= 1e-12 * Math.abs(sum)) {
                break;
            }
            sum += term;
        }
        return sum;
    }

    private static class DoubleAccumulator {
        public double sum;
        public int count;
        public DoubleAccumulator() {
            sum = 0; count = 0;
        }
    }

    public static double infiniteSum(DoubleStream ds) {
        DoubleAccumulator summer = ds.collect
            (DoubleAccumulator::new,
             (s, d) -> { if (Math.abs(d) <= 1e-12 * Math.abs(s.sum)) { 
                            ds.close();
                         } else {
                            s.sum += d; s.count++;
                            if (s.count % 10000 == 0)
                               System.out.println("count = " + s.count + " term = " + d);
                         }
                       },
             (s1, s2) -> s1.sum += s2.sum);
        return summer.sum;
    }

    public static void main(String[] args) {
        double sum = infiniteSum(IntStream.iterate(1, (i -> i + 1))
                                       .mapToDouble(n -> 1 / ((double)n * n)));
        System.out.println(sum);
    }

}    

