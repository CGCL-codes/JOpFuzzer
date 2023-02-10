// $ javac -d classes ArraysCopyOf
// $ java -Xbootclasspath/a:classes/ -Xcomp -XX:CompileCommand=compileonly,ArraysCopyOf.test -XX:-TieredCompilation -Xbatch
public class ArraysCopyOf {
    public static void main(String[] args) throws Throwable {
        test(String[].class);
    }

    static void test(Class<?> arrayType) {
        Class<? extends Object[]> objArrayType = arrayType.asSubclass(Object[].class);
        Object[] example = java.util.Arrays.copyOf(new Object[] {}, 0, objArrayType);

        System.out.println(example.getClass().getName());
        if (example.getClass() != String[].class) {
            throw new AssertionError(example.getClass().getName() + " != " + String[].class.getName());
        }

        Inner.test(example);
    }

    static class Inner {
        public static void test(Object[] array) {
            System.out.println(array.getClass().getName());
            if (array.getClass() != String[].class) {
                throw new AssertionError(array.getClass().getName() + " != " + String[].class.getName());
            }
        }
    }
}
