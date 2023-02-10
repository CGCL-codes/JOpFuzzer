
The jimple file is: ( notice that @parameter0 is of a type java.lang.String )
public class M1433982529S extends java.lang.Object
{
    protected void internalTransform(java.lang.String)
    {
        java.util.Map r0;

        r0 := @parameter0: java.util.Map;   
        staticinvoke <java.lang.Object: boolean getBoolean(java.util.Map)>(r0);

        return;
    }
}

The class file:
  Last modified Jun 16, 2015; size 280 bytes
  MD5 checksum bac669ea75d85888d7af44741aee2cf5
  Compiled from "Jasmin"
public class M1433982529S
  minor version: 0
  major version: 46
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Utf8               java/lang/Object
   #2 = Utf8               SourceFile
   #3 = Utf8               main
   #4 = Methodref          #15.#13        // java/lang/Object.getBoolean:(Ljava/util/Map;)Z
   #5 = Utf8               Code
   #6 = Class              #12            // M1433982529S
   #7 = Utf8               getBoolean
   #8 = Utf8               ([Ljava/lang/String;)V
   #9 = Utf8               Jasmin
  #10 = Utf8               internalTransform
  #11 = Utf8               (Ljava/util/Map;)Z
  #12 = Utf8               M1433982529S
  #13 = NameAndType        #7:#11         // getBoolean:(Ljava/util/Map;)Z
  #14 = Utf8               (Ljava/lang/String;)V
  #15 = Class              #1             // java/lang/Object
{
  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=0, locals=1, args_size=1
         0: return

  protected void internalTransform(java.lang.String);
    descriptor: (Ljava/lang/String;)V
    flags: ACC_PROTECTED
    Code:
      stack=1, locals=2, args_size=2
         0: aload_1
         1: invokestatic  #4                  // Method java/lang/Object.getBoolean:(Ljava/util/Map;)Z
         4: pop
         5: return
}
SourceFile: "Jasmin"

