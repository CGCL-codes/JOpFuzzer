
public class StackOverflow { 
    static int level; 
     
    public static void main(String[] args) { 
        try { 
            new StackOverflow().recurse(); 
        } catch (StackOverflowError e) { 
            System.out.println("Final Level "+level+" for JDK "+System.getProperty("java.version")); 
        } 
         
    } 
     
    void recurse() { 
        level++; 
        if (level%100 == 0) System.out.println("Level "+level); 
        recurse(); 
    } 
     
} 
