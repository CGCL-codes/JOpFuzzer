
/** create below two java classes and run the code in debug mode it will fail
* however you run normally it will not fail
 */

package test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.util.concurrent.TimeUnit;

public class AccessViolationError {

	public static void main(String args[]){
		
	//	System.load("c:/path/to/dll/HelloWorld.dll");
	ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("commonCcyAmountFlush"));

	newSingleThreadScheduledExecutor.scheduleAtFixedRate(new Runnable() 
		{ @Override	public void run(){ System.out.println("hi");}} , 120, 120, TimeUnit.SECONDS);
	}	
}




