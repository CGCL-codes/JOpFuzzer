public class Test {
    public static void main(String[] args) {
        String str1 = "/home/pengfei/backup/Android/projects/ActionBarSherlock/tree/master/actionbarsherlock/res/";
        String str2 = "/home/pengfei/backup/Android/projects/ActionBarSherlock/tree/master/actionbarsherlock/res/\u6d4b\u8bd5.png";
        System.out.println(str1.compareTo(str2));
    }
}