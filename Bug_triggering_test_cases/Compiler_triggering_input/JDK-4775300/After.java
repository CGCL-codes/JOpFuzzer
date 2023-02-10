import java.util.*;
import java.io.*;

public class After {

    public static long [] ltime = null;
    public static long [] gtime = null;

    public static void main(String[] args) throws Exception {

        try {
            Date ldate = new Date();
            System.out.println("--1--");
            boolean b = ldate.after(null);
            System.out.println("--2--");
            System.out.println ("AfterTest02 Fail");
        } catch (NullPointerException npe) {
            System.out.println ("Expected NullPointerException thrown in AfterTest02 Pass ");
        } catch (Exception e) {
            System.out.println ("Exception thrown in AfterTest02 Fail " + e);
            e.printStackTrace();
        }
    }
}