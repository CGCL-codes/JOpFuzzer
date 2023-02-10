class MyValue1 {
    int[] array = new int[1];
}

class Test {

    void test1(MyValue1[] array) {
        array[0].array[0] = 0;
/*
       2: aaload
       3: getfield
       6: iconst_0
       7: iconst_0
       8: iastore
*/
    }

    public static void main(String[] args) {
        Test t = new Test();
        MyValue1[] array = {new MyValue1()};
        for (int i = 0; i < 10_000; ++i)
            t.test1(array);
    }
}