public class Test1{

    public static void main(String [] args){
        if (args.length==0){

            System.err.println("You must specify the amount of iterations to perform");
            return;
        }


        int iterationsCount;
        try{
            iterationsCount = Integer.parseInt(args[0]);
        } catch(NumberFormatException e){
            System.err.println("Unparseable argument: "+args[0]+",must be an integer");
            return;
        }

        System.out.println("JVM vendor:"+System.getProperty("java.vendor"));
        System.out.println("JVM version:"+System.getProperty("java.version"));
        System.out.println();

        // Warm up loop

        System.out.println("Warm up:");
        for (int i=0;i<5;i++){
            long startTime = System.currentTimeMillis();
            int result = method(iterationsCount);
            long endTime = System.currentTimeMillis();
            System.out.println((i+1)+") Result: "+result+" Took: "+(endTime-startTime)+"ms.");
        }

        // The real thing
        System.out.println();

        System.out.println("The real thing:");
        for (int i=0;i<5;i++){
            long startTime =
                    System.currentTimeMillis();
            int result = method(iterationsCount);
            long endTime = System.currentTimeMillis();
            System.out.println((i+1)+") Result: "+result+" Took: "+(endTime-startTime)+"ms.");
        }

    }



    public static int method(int iterationsCount){
        int sum = 0;
        for (int i=0;i<iterationsCount;i++){
            sum += (i*5-1)%4;

        }
        return sum;
    }

}