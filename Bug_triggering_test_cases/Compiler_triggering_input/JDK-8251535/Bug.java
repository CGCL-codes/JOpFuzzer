public class Bug {
    /*
    * Why does this code output:
    * #1 1746077296
    * #2 9296
    * ???
    * */
    public static void main(String[] args) {
        for (int i = 0, j = 0; j < 2; i -= 1000) {
            if ((i > 999) && (i < 10000)) {
                j++;
                System.out.println("#" + j + ' ' + i + ' ' + Integer.MAX_VALUE);
            }
        }
    }
}