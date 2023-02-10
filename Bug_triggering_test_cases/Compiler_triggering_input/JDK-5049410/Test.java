import cryptix.tools.UnixCrypt;

public class Test {
  public static void main(String args[]) {
     String cryptPass ="";
     String prePass = "";
     String account = "abcdefg";
     String password = "abcdefghi123";
     UnixCrypt jc = new UnixCrypt(account);
     for(int i = 0; i < 10000; i++)
     {
       cryptPass = jc.crypt(password);
       if(i != 0)
       {
           if(!prePass.equals(cryptPass))
           {
              System.out.println("Mismatch");
              break;
           }
        }
        prePass = cryptPass;
     }
  }
}