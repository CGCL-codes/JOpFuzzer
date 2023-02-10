
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authenticator;

/**
 *
 * @author owtee
 */
public class Authenticator {

    /**
     * @param args the command line arguments
     */
    
    static {
        System.loadLibrary("CppAuthenticator");
    }
    
    public native boolean authenticate(String username, String password);
    
    public static void main(String[] args) {
        Authenticator authenticator = new Authenticator();
        boolean valid = authenticator.authenticate("user", "pass");
        
        System.out.println("valid : " + valid);
        if(!valid) {
            System.err.println("Not valid");
            System.exit(1);
        }
        System.out.println("Valid");
    }
    
}

