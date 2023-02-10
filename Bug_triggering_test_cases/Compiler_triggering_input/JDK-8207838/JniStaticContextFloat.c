#include <jni.h>
#include <stdlib.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jfloat JNICALL Java_JniStaticContextFloat_staticMethodFloat1(JNIEnv *env, jclass cl, jfloat j0, jfloat j1, jfloat j2, jfloat j3)
{
    if (j0 != (float) (1) || j1 != (float) (2) || j2 != (float) (4) || j3 != (float)(8))
    {
        printf("JNI: %f %f %f %f\n", j0, j1, j2, j3);
        exit(1);
    }

    return 1;
}