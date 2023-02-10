import java.util.Arrays;

import java.security.Security;
import java.security.Provider;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.MGF1ParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class Test {
    private static RSAPrivateKey privateKey;
    private static RSAPublicKey publicKey;
    static Provider cp;

    public static void main(String args[]) throws Exception {
        cp = Security.getProvider("SunJCE");
        System.out.println("Testing provider " + cp.getName() + "...");
        Provider kfp = Security.getProvider("SunRsaSign");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", kfp);
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        privateKey = (RSAPrivateKey)kp.getPrivate();
        publicKey = (RSAPublicKey)kp.getPublic();
        testEncryptDecrypt(new OAEPParameterSpec("SHA-512/256", "MGF1",
                MGF1ParameterSpec.SHA512, PSource.PSpecified.DEFAULT), 1261);

    }

    private static void testEncryptDecrypt(OAEPParameterSpec spec,
            int dataLength) throws Exception {
        
        Cipher c = Cipher.getInstance("RSA/ECB/OAEPPadding", cp);
        c.init(Cipher.ENCRYPT_MODE, publicKey, spec);

        byte[] data = new byte[dataLength];
        byte[] enc = c.doFinal(data);
        c.init(Cipher.DECRYPT_MODE, privateKey, spec);
    }
}
