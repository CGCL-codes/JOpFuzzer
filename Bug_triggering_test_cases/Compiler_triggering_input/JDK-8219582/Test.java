class Test {
    static class LoadLast {
        final long value = System.nanoTime();
    }

    static Object load() {
        return new LoadLast();
    }

    static Object work() {
        LoadLast o = (LoadLast) load();
        return o;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(((LoadLast) work()).value);
    }
}
