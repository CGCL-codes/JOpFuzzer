package sub1;
public class T1{
    public int xadd(int a,int b){
        int r=a+b+T2.foo;
        System.out.println(r);
        return r;
    }
}