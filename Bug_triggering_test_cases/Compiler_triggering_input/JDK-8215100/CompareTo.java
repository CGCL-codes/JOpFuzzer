public class CompareTo {
    public static void main(String args[]) {
        final String[] strings = { "e.\u0259.", "y.e." };
        strings[0].compareTo(strings[1]);
        for (String s1 : strings) {
            for (String s2 : strings) {
                System.out.println(s1.compareTo(s2));
            }
        }
    }
}