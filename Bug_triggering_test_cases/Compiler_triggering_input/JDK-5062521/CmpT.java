class CmpT {
    public static void main(String[] args){
        int MAXLOOP;
        if(args.length < 1) MAXLOOP = 20000;
        else MAXLOOP = Integer.parseInt(args[0]);
        int k=-1;
        String s1 = "Hello World";
        String s2 = "World";
        for(int i = 0 ; i < MAXLOOP ; i++){
            k = aaa(s1,s2);
        }
        System.out.println("compareTo = "+k);
    }
    public static int aaa(String s1, String s2){
        return s1.compareTo(s2);
    }
}