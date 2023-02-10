
public class Test{

public static void main(String... murx) throws Throwable
	{
				
		Class c = HashMap.class;
		c = c.getClass();
		Method m = c.getDeclaredMethod("getConstantPool", null);
		System.out.println(m);
		m.setAccessible(true);
		Object o = m.invoke(c, null);
		System.out.println(o);
		Class cc = o.getClass();
		System.out.println(cc);
		
		for (Field f : cc.getDeclaredFields())
		{
			f.setAccessible(true);
			System.out.println(f.get(o) );   // comment it. Works fine.
		}
		
		System.out.println();
		
		for (Method mm : cc.getDeclaredMethods() )
		{
			System.out.println(mm);
		}
	}
}
