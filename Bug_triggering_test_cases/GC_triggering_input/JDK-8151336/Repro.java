public class Repro {

    public static Object o;

    public static void main(String args[]) throws Exception {
        Thread t = new Thread(()->{
                while(true) {
                    o = new byte[8*1024*1024];
                }
            });
        t.setDaemon(true);
        t.start();

        Thread t2 = new Thread(()->{
                System.out.println("Exiting");
                System.exit(1);
            });

        t2.setDaemon(true);
        t2.start();
    }
}

D:\java_tools\jdk-10.0.2\bin\java.exe  -cp "D:\repository\CFSynthesis\sootOutput\HotspotTests-Java\lib\junit-4.13.1.jar;D:\repository\CFSynthesis\sootOutput\HotspotTests-Java\lib\testng-6.14.3.jar;D:\repository\CFSynthesis\sootOutput\HotspotTests-Java\lib\tools.jar;D:\repository\CFSynthesis\sootOutput\HotspotTests-Java\out\production\HotspotTests-Java" compiler.c2.Test6661247 