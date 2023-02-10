
public class test
{
	public int intField;
	public short shortField;
	public Short shortObjectField;

	public static void main(String[] params) throws Exception
	{
		java.lang.reflect.Field[] fields = test.class.getFields();
		for(int i=0; i<fields.length; i++)
		{
			System.out.println("Field[" + i + "] " + fields[i]);
			Class c = fields[i].getType();
			System.out.println("IsAssignable: " + Object.class.isAssignableFrom(c));
		}
		
	}
}
