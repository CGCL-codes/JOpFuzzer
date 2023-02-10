package com.n;
import com.g.Child;
public final class Buggered {
    public static final class Foo { }
    void unused() {
        new Child().setJobName();
    }
    public static void main(String[] args) {
        Class<?> clazz = Buggered.Foo.class;
        clazz.isAnonymousClass();
    }
}
