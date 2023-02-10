import java.util.HashSet;
import java.util.Set;

/**
 * @author Jan Tietjens (jtietjen)
 */
public class MathRandomTest extends Thread {
    double [] randomNumbers;

    public MathRandomTest(int numberOfRandomNumbers) {
        randomNumbers = new double[numberOfRandomNumbers];
    }

    public void run() {
        synchronized(this){

            for (int i = 0; i < randomNumbers.length; i++) {
                //System.out.println("getName() = " + getName() + " generating RandomNumber");
                randomNumbers[i] = (1000 * Math.random());
            }
        }
    }

    private double[] getRandomNumbers() {
        return randomNumbers;
    }

    public static void main(String[] args) throws InterruptedException {
        while(true){

            int numberOfRandomNumbers = 1000;
            if (args.length > 0) {
                numberOfRandomNumbers = Integer.parseInt(args[0]);
            }

            MathRandomTest firstMathRandomTest = new MathRandomTest(numberOfRandomNumbers);
            firstMathRandomTest.setName("FirstMathRandomTest");
            firstMathRandomTest.start();

            MathRandomTest secondMathRandomTest = new MathRandomTest(numberOfRandomNumbers);
            secondMathRandomTest.setName("SecondMathRandomTest");
            secondMathRandomTest.start();

            firstMathRandomTest.join();
            secondMathRandomTest.join();

            Set set= new HashSet();
            addResultArrayToSet(firstMathRandomTest, set);
            addResultArrayToSet(secondMathRandomTest, set);
        }

    }

    private static void addResultArrayToSet(MathRandomTest firstMathRandomTest, Set set) {
        double[] randomNumbers = firstMathRandomTest.getRandomNumbers();
        for (int i = 0; i < randomNumbers.length; i++) {
            double randomNumber = randomNumbers[i];
            Double aDouble = new Double(randomNumber);
            if(!set.add(aDouble)){
                System.out.println("Double (" + aDouble+ ") already exists in the set!!!");
            };
        }
    }
}