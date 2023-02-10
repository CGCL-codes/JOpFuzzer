
class Test {
        public static void main(String[] args)
        {
                long x = 0;
                while(true)
                {
                        if( x % 10 == 0 ) System.out.println( "Hello world!" );
                        try { Thread.sleep(1000); } catch (InterruptedException e) {}
                        x++;
                }
        }
}

