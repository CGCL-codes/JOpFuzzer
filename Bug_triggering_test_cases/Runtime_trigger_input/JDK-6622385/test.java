
package ab;

public abstract class A{
	protected static void m(){
		System.out.println("A.m()");
	}
}
-----------------------------------------------------------------------
package ab;

public abstract class B extends A{
}
-----------------------------------------------------------------------
package c;

import ab.A;
import ab.B;

public class C extends A{
	public static void c(){
		B.m();
	}
}
------------------------------------------------------------------------
import c.C;

public class D {
	public static void main(String [] argv){
		C.c();
	}
}
------------------------------------------------------------------------
