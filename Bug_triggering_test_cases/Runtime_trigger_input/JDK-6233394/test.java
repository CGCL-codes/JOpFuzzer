

public class PrimitiveLoader extends ClassLoader {

	protected Class findClass(String name)
		throws ClassNotFoundException
	{
		if( name.equals("int") ) return Integer.TYPE;
		throw new ClassNotFoundException(name);
	}

	public static void main(String[] argv)
		throws ClassNotFoundException
	{
		PrimitiveLoader pl = new PrimitiveLoader();

		Class c1 = pl.loadClass("int");
		System.out.println("Load: " + c1);
		Class c2 = Class.forName("int",false,pl);
		System.out.println("ForName: " + c2);
	}
}

