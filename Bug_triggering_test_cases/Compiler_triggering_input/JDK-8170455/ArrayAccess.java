interface ArrayAccessInterface {
    default int [] arrayAccess() {
        int a[] =new int[10];
        return a.clone();
    }
}
class ArrayAccess implements ArrayAccessInterface {
    public static void main(String args[]){
        int i=0;
        while (i++ < 100000) {
            run();
        }
    }
    public static void run() {
        new ArrayAccess().arrayAccess();
    }

}