
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UseMemory {

        public static void main(String[] args) throws Exception {
                System.out.println("usage: <number_of_megs>");
                System.out.println();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("availableProcessors:"+Runtime.getRuntime().availableProcessors());
                long free = Runtime.getRuntime().freeMemory();
                long total = Runtime.getRuntime().totalMemory();
                long max = Runtime.getRuntime().maxMemory();
                System.out.println("freeMemory:"+1.0*free/1024/1024+" megs ("+free+" bytes)");
                System.out.println("totalMemory:"+1.0*total/1024/1024+" megs ("+total+" bytes)");
                System.out.println("maxMemory:"+1.0*max/1024/1024+" megs ("+max+" bytes)");

                int megs = Integer.parseInt(args[0]);
                int bsize = args.length>1 ? Integer.parseInt(args[1]) : 1024*1024;

                System.out.println();
                System.out.println("hit enter to start allocating "+megs+" x "+bsize+" bytes");
                br.readLine();

                ArrayList list = new ArrayList();

                int i=0;
                for(; i<megs; i++ ) {
                        try {
                                list.add(new byte[bsize]);
                                System.out.print(".");
                        } catch(Throwable e) {
                                System.out.println(e);
                                break;
                        }
                }

                System.out.println();
                long alloc = ((long)list.size())*bsize;
                System.out.println("allocated "+(1.0*alloc/1024/1024)+" megs ("+alloc+" bytes)");

                System.out.println("hit enter to exit");
                br.readLine();

        }

}

