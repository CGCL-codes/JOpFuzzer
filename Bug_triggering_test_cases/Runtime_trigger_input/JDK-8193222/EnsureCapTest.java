public class EnsureCapTest {
    static final String LIBNAME="EnsureCapTest";

    public static void main(String[] args) {
        String mappedName = System.mapLibraryName(LIBNAME);
        System.out.println("Mapped lib name: " + mappedName);
        System.loadLibrary(LIBNAME);
        System.out.println("Call JNI ensureCapTest()");
        new EnsureCapTest().ensureCapTest();
    }

    native void ensureCapTest();
}

