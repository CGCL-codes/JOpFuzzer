import javax.script.*;

public class Test {

  public static void main(String[] args) throws InterruptedException {
   ScriptEngine engine = new
ScriptEngineManager().getEngineByName("nashorn");
   String s = "";
   while (true) {
     try {
       Object o = engine.eval(s);
     } catch (ScriptException e) {
       System.err.println("Error:" + e);
     }
   }
 }
}