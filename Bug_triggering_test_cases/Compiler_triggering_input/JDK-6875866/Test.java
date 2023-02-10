public class Test {

    static int IndexOfTest(String str) {
        return str.indexOf("11111xx1x");
    }

    public static void main(String args[]) {
        String str = "11111xx11111xx1x";
        int idx = IndexOfTest(str);
        System.out.println("IndexOf = " + idx);
    }
}