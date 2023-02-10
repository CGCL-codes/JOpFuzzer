
test.java:
----------------

public class test {
    public static void main(String[] args) {
        System.loadLibrary("test");
        doStuff();
    }

    private static native void doStuff();
}

test.c:
----------

#include <jni.h>
#include <GL/glx.h>
#include "test.h"

JNIEXPORT void JNICALL Java_test_doStuff(JNIEnv * env, jclass clazz) {
    void *address = glXGetProcAddress((GLubyte*)"glMultiTexCoord1f");
printf("Address: %p\n", address);
}

run script:
-----------------
$JAVA_HOME/bin/javac test.java
$JAVA_HOME/bin/javah test
gcc -I$JAVA_HOME/include -I$JAVA_HOME/include/linux -Wall -shared -o libtest.so -fPIC -lGL test.c
$JAVA_HOME/bin/java -Djava.library.path=. test

