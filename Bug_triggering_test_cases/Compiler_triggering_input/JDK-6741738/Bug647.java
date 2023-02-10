public class Bug647 {

    private String[] values;
    private int count;

    String foo() {
        int i = Integer.MAX_VALUE-1;
        return values[i];
    }

    public static void main(String[] args) {
        Bug647 t = new Bug647();
        String s = t.foo();
    }
}