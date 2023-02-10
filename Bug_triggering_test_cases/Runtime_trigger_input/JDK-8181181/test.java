
This is my pre-main (that is launched via the PREMAIN manifest thingy for a javaagent)



public class Premain {

  public static boolean DEBUGMODE;

  public static void debug() {
    String debugmode = System.getenv("BYTEAGENT");
    DEBUGMODE = debugmode.equals("true")||debugmode.equals("TRUE");
  }

  public static void preload() {
    ClassLoader cl = ClassLoader.getSystemClassLoader();
    try {
      cl.loadClass("java.util.Optional");
      ///If I preoload some java invoke stuff here it _generally_ works but 
    } catch (Exception e) {
     //won't happend
    }
  }


  public static void premain(String agentArgs, Instrumentation inst) {
    debug();
    preload();
    if(DEBUGMODE) {
      System.out.println("Entered Premain");
    }
    inst.addTransformer( new EntryPoint(agentArgs), true);
    if(DEBUGMODE) {
      System.out.println("Exit Premain");
    }
  }
}
