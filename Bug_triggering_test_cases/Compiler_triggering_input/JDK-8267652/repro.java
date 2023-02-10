//run with  -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:UseAVX=2 -XX:LoopMaxUnroll=8 -XX:-UseCompressedOoepro
//look for vpxor in output

import java.util.Scanner;


class repro {

    public static void xor_array(byte[] arr1, byte[] arr2, byte[] result)
    {
	int i;
	for (i = 0 ; i < arr1.length; i++) result[i] = (byte)(arr1[i] ^ arr2[i]);
    }
    public static void main(String[] args) {
        System.out.println("heat up!");
        int i;
        for (i = 0 ; i < 10_000_000; i++)
        {
    	    byte[] arr1 = new byte[i%48+64];
    	    byte[] arr2 = new byte[i%48+64];
    	    byte[] result = new byte[i%48+64];
    	    xor_array(arr1, arr2, result);
        }
        System.out.println("heat up is done");
        Scanner in = new Scanner("16"); //preventing opts by javac
 
        int secret = in.nextInt();
    	    byte[] arr1 = new byte[secret];
    	    byte[] arr2 = new byte[secret];
    	    byte[] result = new byte[secret];
    	    xor_array(arr1, arr2, result);
	System.out.println("created byte array of size " + secret);
    }
}
