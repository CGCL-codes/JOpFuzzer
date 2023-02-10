
public class Main
{
	public static class Runner implements Runnable
	{
		int count = 0;
		String name;
		
		public Runner(String name)
		{
			this.name = name;
		}
		
		public void run()
		{
			try
			{
				recurse();
			}
			catch(Throwable t)
			{
				System.out.println(name + " Frames: " + count);
			}
			synchronized(this)
			{
				notify();
			}
		}
		
		public void recurse()
		{
			count++;
			recurse();
		}
	}
	
	public static void run(String name, long bytes)
	{
		Runner runner = new Runner(name);
		new Thread(null, runner, "Test Thread", bytes).start();
		synchronized(runner)
		{
			try
			{
				runner.wait();
			}
			catch(Exception e)
			{}
		}
	}
	
	public static void main(String[] args)
	{
		run("5MM bytes", 5000000);
		run("2M bytes", 2000);
	}
}

