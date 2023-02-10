
public class Search
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Utf8               Search
   #2 = Class              #1             // Search
   #3 = Utf8               java/lang/Object
   #4 = Class              #3             // java/lang/Object
   #5 = Utf8               <clinit>
   #6 = Utf8               ()V
   #7 = Utf8               <init>
   #8 = NameAndType        #7:#6          // "<init>":()V
   #9 = Methodref          #4.#8          // java/lang/Object."<init>":()V
  #10 = Utf8               main
  #11 = Utf8               ([Ljava/lang/String;)V
  #12 = Utf8               java/lang/Exception
  #13 = Class              #12            // java/lang/Exception
  #14 = Utf8               Code
  #15 = Utf8               Exceptions
{
  public static {};
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=0, locals=0, args_size=0
         0: return

  public Search();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #9                  // Method java/lang/Object."<init>":()V
         4: return

  public void main(java.lang.String[]) throws java.lang.Exception;
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=2, args_size=2
         0: new           #2                  // class Search
         3: dup
         4: aload_0
         5: monitorenter
         6: monitorenter
         7: monitorexit
         8: aload_0
         9: monitorexit
        10: return
    Exceptions:
      throws java.lang.Exception
}
