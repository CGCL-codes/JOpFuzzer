public class DivIdealizeAndMinus32FollowedByDivConst2Bug {

    public static void main(String[] args) {
        long iteration = 0;
        while (true) {
            iteration++;
            int result = andMinus32FollowedByDivConst2(255);
            if (result != 112) {
                System.out.println("expected 112, but got " + result + " after iteration " + iteration);
                System.exit(-1);
            }
        }
    }

    private static int andMinus32FollowedByDivConst2(int x) {
        return (x & -32) / 2;
    }

}