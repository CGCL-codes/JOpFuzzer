import java.rmi.server.UID;

public class Uidtest
{
    public static final UID u = new UID();
    public static void main(String args[])
    {
        try{
            for(int i=0; i<10000; i++){
                System.out.println(u.toString());


            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

}
