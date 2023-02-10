import java.io.*;
import java.util.*;
import java.lang.*;
public class test1 implements Serializable
{

    private static final String[] SENNIN_IPPAN_GYOUSHU = { "1", "4", "7" };
    private static final String[] SENNIN_TOKUTEI_7_GYOUSHU = { "3", "9" };
    private static final String[] SENNIN_TOKUTEI_IGAI_GYOUSHU = { "2", "5",
            "6", "8", "9" };
    private static final String[] KOKKAN_TOKUTEI_7_GYOUSHU = { "3" };
    private static final String[] KOKKAN_TOKUTEI_IGAI_GYOUSHU = { "2", "5",
            "6", "8" };

    private static final int TOKUTEI_GYOUSHU_DO = ( 1 - 1 );
    private static final int TOKUTEI_GYOUSHU_KEN = ( 2 - 1 );
    private static final int TOKUTEI_GYOUSHU_DEN = ( 8 - 1 );
    private static final int TOKUTEI_GYOUSHU_KAN = ( 9 - 1 );
    private static final int TOKUTEI_GYOUSHU_KOU = ( 11 - 1 );
    private static final int TOKUTEI_GYOUSHU_HO = ( 13 - 1 );
    private static final int TOKUTEI_GYOUSHU_ZOU = ( 23 - 1 );


    public static int[][] check( String[] p_GyoushuEng , String[] test9_str)
    {

        int[][] i_result = new int[5][ p_GyoushuEng.length ];
        int[] count = new int[28];
        String[][] s_Gyoushu = new String[16][28];
        String[] tokuteiTemp = new String[28];
        for( int i = 0; i < i_result.length; i++ ){
            for( int j = 0; j < i_result[i].length; j++){
                i_result[i][j] = 10;
            }
        }

        for( int i = 0; i < count.length; i++ ){
            count[i] = 0;
        }



        //set up s_Gyoush in some fashion here
        int k = 0;
        for( int i = 0; i < test9_str.length; i++ ){
            if( !test9_str[i].equals("") && !test9_str[i].equals(" ") ){
                if( ( foo( test9_str[i] ) ) == null ){
                    i_result[2][i] = 32;
                    s_Gyoushu[k] = null;
                    System.err.println ("s[" + k + "] is null");
                    k++;
                    s_Gyoushu[k] = null;
                    System.err.println ("s[" + k + "] is null");
                    k++;
                }
                else{
                    s_Gyoushu[k] = foo( test9_str[i] );
                    System.err.println ("s[" + k + "] is foo");
                    k++;
                    s_Gyoushu[k] = foo( test9_str[i] );
                    System.err.println ("s[" + k + "] is foo");
                    k++;
                }
            }
            System.err.println("k " + k);

            if( p_GyoushuEng.length == 28 ){
                for( int j = 0; j < p_GyoushuEng.length; j++ ){
                    if( !p_GyoushuEng[j].equals("") &&
                            !p_GyoushuEng[j].equals(" ") &&
                            !p_GyoushuEng[j].equals("0")){

                        for( int z = 0; z < s_Gyoushu.length; z++ ){
                            System.err.println ("> z " + z + " j " + j);
                            if( s_Gyoushu[z] != null ){
                                System.err.println ("< z " + z + " j " + j);
                                //System.err.println(z);
                                //System.err.println(j);
                                if( p_GyoushuEng[j].equals( s_Gyoushu[z][j] ) ){
                                    count[j]++;
                                }
                            }
                        }
                    }
                    else{
                        count[j] = -1;
                    }
                }
                for ( int z = 0; z < count.length; z++ ){
                    if( count[z] == 0 ){
                        i_result[3][z] = 26;
                    }
                }
            }
            else{
                for( int z = 0; z < i_result[1].length; z++){
                    i_result[1][z] = 28;
                }
            }

        }
        return i_result;
    }

    public static String[] foo(String s1)
    {
        String[] temp1={};
        String[] temp2={"", "san", "", "san", "", "", "", "",
                "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", ""
        };
        if(s1.equals("san"))
            return null;
        else
            return temp2;
    }
    public static void main(String[] args)
    {
        int[][] result = new int[5][28];
        String[] test9_str1 = { "san", "san", "sandy", "so", "sarayu",
                "saravan", "sar"};
        String[] p_Gyou = {"", "", "uma", "um", "um2", "ush", "usha", "viji",
                "",
                "", "", "","", "", "",
                "", "", "","", "", "", "saro1",
                "", "", "","", "sandy2","" };
        for(int i =0; i < 100; i++){
            result = check(p_Gyou, test9_str1 );
            if(i % 6 == 0){
                for(int k=0; k < 5; k++)
                    for(int j=0; j< 28; j++) {
                        System.err.print(" " + result[k][j]);
                    }
                System.err.println ("");
            }
        }
    }
};