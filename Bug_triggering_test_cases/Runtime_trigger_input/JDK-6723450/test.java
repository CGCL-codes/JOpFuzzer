
class LocalRefs {
public native byte run();
public static void main(String[] args) {
new LocalRefs().run();
}
}

JNIEXPORT jbyte JNICALL Java_LocalRefs_run
  (JNIEnv *env, jobject obj);

JNIEXPORT jbyte JNICALL Java_LocalRefs_run
  (JNIEnv *env, jobject obj)
{
jclass LocalRunnable;
for (int i = 0; i < 16; i  )
         jclass LocalRunnable = env->FindClass("java/lang/Runnable");
}
