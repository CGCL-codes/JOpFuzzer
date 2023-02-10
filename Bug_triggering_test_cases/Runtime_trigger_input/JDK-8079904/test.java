
/*
I try these simple code to find problem, and same symptom is occurs.
*/

public class Main
{
	public static void main(String[] args) throws Exception
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("Oracle Driver load finished.");
	}
}
