public class ServerBug {

    private static final int MAX_VALUE = 1000;

    private static final int ITER_NUMBER = 1000000;

    private static float[] funcValues = new float[0];

    public static float sum(int maxValue, int iterNumber) {
        float sum;

        sum = 0;
        for (int i = 0; i < iterNumber; i++) {
            for (int v = 0; v < maxValue; v++) {
                sum += funcValues[v];
            }
        }
        return sum;
    }

    public static void main(String[] args) {

        funcValues = new float[MAX_VALUE];
        for (int i = 0; i < funcValues.length; i++) {
            funcValues[i] = (float) Math.sqrt(i);
        }

        System.out.println("sum = " + sum(MAX_VALUE, ITER_NUMBER));
    }
}