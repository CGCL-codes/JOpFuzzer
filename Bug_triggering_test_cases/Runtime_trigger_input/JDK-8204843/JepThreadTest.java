
package x.y.java2python;

import java.util.ArrayList;
import java.util.Arrays;
import jep.Jep;
import jep.JepException;

/**
 * @author Francis Wildhaber
 *
 */
public class JepThreadTest implements Runnable
{

	/**
	 * Only the thread that creates the Jep object can access it, otherwise 
	 * a "jep.JepException: Invalid thread access." is thrown by design... 
		at jep.Jep.isValidThread(Jep.java:466)
		at jep.Jep.eval(Jep.java:584)
	 * So a common Jep object cannot be shared by Multiple threads.
	**/

	//private static Jep m_Jep = null;

	/**
	 * @param args
	 * @throws JepException
	 */
	public static void main(String[] args) throws JepException
	{
		JepThreadTest aJtt = new JepThreadTest();
		
	    /*
	     Calling Thread's constructor & passing the object 
	    of this class (JepThreadTest) that  implements the Runnable interface	    
	    & the name of new thread.
	    */
		Thread t1= new Thread(aJtt,"Thread1");
		Thread t2= new Thread(aJtt,"Thread2");
		Thread t3= new Thread(aJtt,"Thread3");					
		t1.start(); //Starting Thread1
		t2.start(); //Starting Thread2
		t3.start(); //Starting Thread3
		
	}

	public JepThreadTest() throws JepException
	{
		initialize();
	}

	private void initialize() throws JepException
	{
		//m_Jep = new Jep(false);
	}

	/**
	 * Shorthand for System.out.printl(String);
	 * 
	 * @param pString
	 */
	private void p(String pString)
	{
		System.out.println(pString);
		return;
	}

	@Override
	public synchronized void run() //throws JepException
	{
		try
		{
			Jep aLocalJep = new Jep(false);
			// Only the thread that creates the Jep object can call eval(), etc. methods.
			// Methods use Jep.isValidThread(); internally.

			for (int i = 0; i < 5; i++)
			{
				String aCurrentThread = Thread.currentThread().getName();
				//p("Current thread, i: "+aCurrentThread+", "+i+"\n");
				aLocalJep.set("x", i);
				aLocalJep.eval("y = 2 * x");
				long y = (Long)aLocalJep.getValue("y");
				p("Current Thread, y-value (2*i) from Python, i, Jep Hash:\n\t"
						+Thread.currentThread().getName()+", "+y+", "+i+", "+aLocalJep.hashCode()+"\n");
				
			}
			p("Current Thread, Jep object hash code:\n\t"+Thread.currentThread().getName()+", "+aLocalJep.hashCode());
			aLocalJep.eval(null);
			aLocalJep.close();
			aLocalJep = null;
			Thread.yield();
			String aFrameString = doPyTimeAlignmentSequence();
			p("Current Thread, Pandas dataframe as String: ");
			p(Thread.currentThread().getName()+"\n"+aFrameString+"\n");
			
			}
			catch (JepException e)
			{
				// TODO Auto-generated catch block				
				e.printStackTrace();			
			}
	}

	private synchronized String doPyTimeAlignmentSequence() throws JepException
	{		
		Jep aJep = new Jep(false);
		ArrayList<String> aPyStatements = new ArrayList<String>(Arrays.asList(
				"import pandas as pd",
				"import numpy as np",
				"param_1 = pd.Series([1,2,3,4,5], index=[0, 10, 20, 30, 40])",
				"param_2 = pd.Series([1,2,3,4,5], index=[0, 5, 10, 15, 20])",
				"df = pd.concat([param_1.rename('param_1'), param_2.rename('param_2')], axis=1)",				
				"dfCols = df.columns.tolist()"
		));
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		
		for(int i = 0; i < aPyStatements.size(); i++)
		{
			aJep.eval(aPyStatements.get(i));
			aJep.eval(null);
		}
		ArrayList<String> aDfCols = new ArrayList<String>();
		aJep.eval(null);
		
		aDfCols = (ArrayList<String>)aJep.getValue("dfCols");
		
		aJep.eval(null);
		aJep.close();
		aJep = null;
		Thread.yield();
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		p("Dataframe Columns from tolist():");
		for(String aColName : aDfCols)
		{
			p(Thread.currentThread().getName()+": "+aColName);
			
		}
		return "Py Sequence Complete";// Intended to return Pandas Dataframe as String.
	}
}

