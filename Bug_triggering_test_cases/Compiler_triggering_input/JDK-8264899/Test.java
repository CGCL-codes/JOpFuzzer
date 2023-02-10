class Test {
    public static void main(String[] args) {
        foo();
    }

    static void foo() {
        throw new MyException();
    }
}

class MyException extends RuntimeException {
}
