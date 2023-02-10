
Jimple file:
public class M1433584778S extends java.lang.Object
{
    private void <init>()
    {
        M1433584778S r0;

        r0 := @this: M1433584778S;
        specialinvoke r0.<java.lang.Object: void <init>()>();
        return;
    }

    public static void encodeObject()
    {
        java.lang.String r1, r2;
        java.lang.Throwable r3;
        
     label0: 
        r1 = new java.lang.String;   
        
     label1:      
        return;

     label2:
        r3 := @caughtexception; 
        goto label1;
  
        catch java.lang.RuntimeException from label0 to label1 with label2;       
    }
    
        public static void main(java.lang.String[])
    {
        return;
    }
}

class file:
public class M1433584778S
  minor version: 0
  major version: 46
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Utf8               ([Ljava/lang/String;)V
   #2 = Utf8               java/lang/Object
   #3 = Utf8               <init>
   #4 = Class              #2             // java/lang/Object
   #5 = NameAndType        #3:#7          // "<init>":()V
   #6 = Class              #14            // java/lang/String
   #7 = Utf8               ()V
   #8 = Utf8               M1433584778S
   #9 = Utf8               Code
  #10 = Utf8               main
  #11 = Utf8               SourceFile
  #12 = Class              #8             // M1433584778S
  #13 = Class              #15            // java/lang/RuntimeException
  #14 = Utf8               java/lang/String
  #15 = Utf8               java/lang/RuntimeException
  #16 = Utf8               Jasmin
  #17 = Methodref          #4.#5          // java/lang/Object."<init>":()V
  #18 = Utf8               encodeObject
{
  public static void encodeObject();
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=1, args_size=0
         0: new           #6                  // class java/lang/String
         3: astore_0
         4: return
         5: astore_0
         6: goto          4
      Exception table:
         from    to  target type
             0     4     5   Class java/lang/RuntimeException

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=0, locals=1, args_size=1
         0: return
}
SourceFile: "Jasmin"

