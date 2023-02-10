
public class t {
    public static void main(String[] args) {
        int[] newArray=new int[1073741824];
        newArray[1073741823]=12345;
        System.out.println("Size of newArray is "+newArray.length+
                           " [it should be 1073741824]");
        System.out.println(newArray[1073741823]);
    }
}
