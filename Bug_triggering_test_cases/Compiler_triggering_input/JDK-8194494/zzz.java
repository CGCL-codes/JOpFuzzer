import java.security.MessageDigest;
public class zzz {
    public final static void main(String[]args) {
        try {
            System.out.println("1");
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            System.out.println("2");
            digest.reset();
            System.out.println("3");
            for (int i = 0; i < 1024000; i++) digest.update((byte)123);
            System.out.println("4");
            digest.digest();
            System.out.println("5");
        } catch (Exception e) {
            System.out.println("6");
        }
        System.out.println("7");
    }
} 
