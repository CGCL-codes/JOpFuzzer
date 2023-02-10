//File: TestSubClassesOfConstantArray.java
public class TestSubClassesOfConstantArray {

    public abstract static class NumbersHolder {
        public Number[] getNumbers() {
            return null;
        }
    }

    public static class IntegersHolder extends NumbersHolder {
        private final static Integer integers[] = { new Integer(1) };

        public Number[] getNumbers() {
            return integers;
        }
    }

    public static class LongsHolder extends NumbersHolder {
        private final static Long longs[] = { new Long(1) };

        public Number[] getNumbers() {
            return longs;
        }
    }

    public static final void loopNumbers(NumbersHolder numbersHolder) {
        Number[] numbers = numbersHolder.getNumbers();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i].longValue();
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10000; i++) {
            IntegersHolder integersHolder = new IntegersHolder();
            LongsHolder longsHolder = new LongsHolder();
            loopNumbers(integersHolder);
            loopNumbers(longsHolder);
        }
        System.out.println("done!");
        Thread.sleep(1000);
    }
}
