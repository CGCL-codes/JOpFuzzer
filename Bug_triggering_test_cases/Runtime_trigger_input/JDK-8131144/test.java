
public class JNIFoo {    
    public native String nativeFoo();    

    static {
        System.loadLibrary("foo");
    }        

    public void print () {
    String str = nativeFoo();
    System.out.println(str);
    }
    
    public static void main(String[] args) {
    (new JNIFoo()).print();
    return;
    }
}

**********

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include "JNIFoo.h"

JNIEXPORT jstring JNICALL Java_JNIFoo_nativeFoo (JNIEnv  *env, jobject obj)
{
  int i;
  int ds_ret;

  char *newstring;

  jstring ret = 0;

  newstring = (char *)malloc(30);

  if(newstring == NULL)
  {     
      return ret;
  }

  memset(newstring, 0, 30); 
 
  newstring = "foo: Test program of JNI.\\n";
  

  ret = (*env)->NewStringUTF(env, newstring);

  free(newstring);

  return ret;
}
