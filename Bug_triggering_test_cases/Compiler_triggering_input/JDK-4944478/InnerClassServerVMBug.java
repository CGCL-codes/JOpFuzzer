public class InnerClassServerVMBug {
    private int _value;
    public InnerClassServerVMBug() {
        _value = 0;
    }
    public void calculate() {
        if (false) {
            new Runnable() {
                public void run() {
                    internalCalculate();
                }
            }.run();
        } else
            internalCalculate();
    }
    private void internalCalculate() {
        setValue(new Data(0).getData());
    }
    private void setValue(int result) {
        _value = result;
    }
    public int getValue() {
        return _value;
    }

    private class Data {
        int _data;

        private Data(int data) {
            _data = data;
        }

        public int getData() {
            return _data;
        }
    }
    public static void main(String[] args) throws Exception {
        final InnerClassServerVMBug p = new InnerClassServerVMBug();
        long sum = 0;
        int idx = 0;

        while (sum < 10000000L) {
            p.calculate();
            sum += p.getValue();
            System.out.print(".");
            if (++idx >= 100) {
                System.out.println();
                idx = 0;
            }
            System.out.flush();
        }
        System.out.println("sum = " + sum);
    }
}