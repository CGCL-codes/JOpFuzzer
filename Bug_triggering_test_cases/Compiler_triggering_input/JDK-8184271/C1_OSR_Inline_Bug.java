public class C1_OSR_Inline_Bug { 

    public static void main(String[] args) { 
        for (int i = 0; i < 100_000; i++) { 
            timeInvocation(); 
        } 
    } 

    private static void timeInvocation() { 
        final long start = System.nanoTime(); 
        final long end = System.nanoTime(); 
        checkResult(end - start); 
    } 

    private static void checkResult(final long l) { 
        if (l < 0) { 
            System.out.println("should not get here " + l); // removing reference to l parameter here "fixes" the bug 
            System.exit(0); 
        } 
    } 
} 