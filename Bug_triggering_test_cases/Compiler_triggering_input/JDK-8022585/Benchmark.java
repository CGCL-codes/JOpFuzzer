public class Benchmark {

    private static double bench(IMyMessageParser parser) {
        String s1 = " Name=Alice\u0001 " ;
        String s2 = " Name=Bob\u0001 " ;
        String s3 = " Name=Carl\u0001 " ;
        String s4 = " Name=Diane\u0001 " ;
        byte[] s5bytes = (s1 + s2 + s3 + s4).getBytes();

        int numberofmessages = 1000000;
        long t11 = System.nanoTime();
        for (int i = 0; i < numberofmessages / 4; i++) {
            parser.processData(s5bytes);
        }
        long t22 = System.nanoTime();
        return (t22 - t11) / numberofmessages / 1000d;
    }

    public static interface IMyMessageParser {
        public void processData(byte[] buffer);

        public void processData(byte[] buffer, int off, int len);
    }

    public static void main(String[] args) {
        IMyMessageParser parserNew = new IMyMessageParser() {

            @Override
            public void processData(byte[] buffer) {
                processData(buffer, 0, buffer.length);
            }

            @Override
            public void processData(byte[] buffer, int off, int len) {
                long x = System.nanoTime();
                long i = 0;
                while (System.nanoTime() - x < 700) {
                    i++;
                }
                // System.out.println(i);
            }

        };

        int rounds = 20;
        double aNew = 0;
        double timesNew[] = new double[rounds];
        for (int i = -2; i < rounds; i++) {
            double timePerMessage = bench(parserNew);
            if (i < 0)
                continue;
            timesNew[i] = timePerMessage;
        }

        for (int i = 0; i < rounds; i++) {
            System.out.println( " Round: " + i);
            System.out.println( " New parser " );
            double timePerMessage = timesNew[i];
            aNew += timePerMessage;
            System.out.println( " Parsing took(wall clock \u03BCs/msg): " + timePerMessage);
            System.out.println( " New Parser Avg: " + aNew / (i + 1));
        }
    }
}