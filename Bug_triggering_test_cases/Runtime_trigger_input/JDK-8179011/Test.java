

public class Test
{
        public static void main(String [] argv)
        {
                new Thread("name for test"){
                        public void run()
                        {
                                while(true){
                                try
                                {
                                        for(int i = 0; i < 10000000; i ++);
                                        Thread.sleep(1000);
                                }
                                catch(Exception e)
                                {
                                }
                                }
                        }
                }.start();
        }
}
