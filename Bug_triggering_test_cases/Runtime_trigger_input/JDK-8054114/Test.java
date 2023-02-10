
package test;

public class A {
	public void print() {
		System.out.println("A");
	}
}
========================================
package test;

public class B extends A{
	@Override
	public void print() {
		System.out.println("B");
	}
}
========================================
package test;

public class C {
	public void print() {
		new B().print();
	}
}
=========================================
package test;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

	public static void main(String[] args) throws Exception {
		
		MyClassLoader myClassLoader = new MyClassLoader();
		
		Class cClass = myClassLoader.loadClass("test.C");
		Object cObject = cClass.newInstance();
		Method printMethod = cClass.getMethod("print");
		
		testImplicitClassLoad(cObject, printMethod);
		
		testExplicitClassLoad(myClassLoader);
	}
	
	private static void testImplicitClassLoad(Object cObject, Method printMethod) {
		for (int i = 0; i < 3; i++) {
			System.out.println("==============testImplicitClassLoad=====================");
			try {
				printMethod.invoke(cObject);
			} catch (Exception e) {
				System.out.println("implicit class load failed. " + e.getCause());
			}
		}
	}
	
	private static void testExplicitClassLoad(ClassLoader classLoader) throws ClassNotFoundException {
		System.out.println("==============testExplicitClassLoad=====================");
		System.out.println("test.B loaded..." + classLoader.loadClass("test.B"));
		System.out.println("test.A loaded..." + classLoader.loadClass("test.A"));
	}
}

class MyClassLoader extends URLClassLoader {
	
	private AtomicInteger count = new AtomicInteger(0);
	
	public MyClassLoader() {
		super(getMyURLs(), null);
	}
	
	private static URL[] getMyURLs() {
		URL[] urls = {Thread.currentThread().getContextClassLoader().getResource("")};
		return urls;
	}
	
	@Override
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		//System.out.println(name);
		
		if (name.contains("A") && count.getAndIncrement() == 0)
		{
			throw new ClassNotFoundException("A");
		}
		
		return super.loadClass(name, resolve);
	}
}


