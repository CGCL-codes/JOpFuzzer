
import java.io.File;
import java.io.FilenameFilter;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;


public class MemTest {

	private static Runtime rt = java.lang.Runtime.getRuntime();// runtime pointer

	public static void main (String args[])
	{
		long t = System.currentTimeMillis();
		MemTest test = new MemTest();

		for (int i=0; i< 200; i++)
		{
			System.out.println("Iteration # " + i + " " + test.getResult());

			try
			{
				Thread.sleep(100);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			long currentFreeMem = rt.freeMemory();  // get free memory on heap
			long currentTotalMem = rt.totalMemory();// and total heap memory
			long usedMem = currentTotalMem - currentFreeMem;// heap memory in use

			System.out.println("MEMORY_MONITOR_CURRENT_MEMORY_USED = " + usedMem/1048576.0 + " MB");
			System.out.println("MEMORY_MONITOR_FREE_MEMORY_ON_HEAP = " + currentFreeMem/1048576.0 + " MB");
			System.out.println("MEMORY_MONITOR_TOTAL_MEMORY_ON_HEAP = " + currentTotalMem/1048576.0 + " MB");
		}

		System.out.println(" Time taken " + (System.currentTimeMillis() - t) + " milliseconds");
	}

	public String getResult() {

		int id = 0;
		int build = 0;

		PreparedStatement pstmtQ  = null;
		Connection conn = null;
		ResultSet rs = null;

		try {

			conn = getConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				pstmtQ.close();
				rs.close();
				conn.close();
			}
			catch (Exception ex1)
			{
			}
		}
		return "hello";
	}



    private Connection getConnection() throws Exception
    {
        Connection con = null;
        String driverClassName = "oracle.jdbc.driver.OracleDriver";
        String dbURL = "jdbc:oracle:thin:@abcd:1521:xyz";
        String userName = "user";
        String password = "password";

		URLClassLoader urlClassLoader = null;
        Class drvrClass = null;

        URL[] urlList = buildJavaActionClassPathUrls("C:\\testDir", null) ;
        if ((urlList != null) && (urlList.length > 0)) {  // if ClassPath specified
          urlClassLoader = new URLClassLoader(urlList);
          drvrClass = urlClassLoader.loadClass(driverClassName);
        }
        else
        { // if ClassPath not specified
          drvrClass = Class.forName(driverClassName, true, ClassLoader.getSystemClassLoader());
        }
        Driver drvr = null;
        try
        {
          drvr = (Driver) drvrClass.newInstance();
          Properties props = new Properties() ;
          props.put("user", userName) ;
          props.put("password", password) ;
          con = drvr.connect(dbURL, props) ;

        }

        catch (Exception e)
        {
			e.printStackTrace();
        }
        finally
        {
			drvr = null;
			urlClassLoader = null;
			drvrClass = null;
			return con;
		}
    }


    public URL[] buildJavaActionClassPathUrls(String serverRoot, String actionClassPath) {

		ArrayList urls = new ArrayList();
		try
		{
			if ((actionClassPath != null) && (actionClassPath.length() > 0))
				urls.add(new File(actionClassPath).toURL());

			if (serverRoot != null && serverRoot.length() > 0) {
				File libdir = new File(serverRoot, "lib") ;
			 	if (libdir.exists() && libdir.isDirectory()) {
			 		File[] jars = libdir.listFiles(new JarZipFileFilter()) ;
			 		for (int i = 0 ; i < jars.length; i++)
						urls.add(jars[i].toURL()) ;
			 	}
			}
		}
        catch (Exception e)
        {
			e.printStackTrace();
        }
        finally
        {
			return (URL[]) urls.toArray(new URL[urls.size()]) ;
		}
     }


     private class JarZipFileFilter implements FilenameFilter {

       public JarZipFileFilter() {
       }

       public boolean accept(File dir, String name) {
         boolean rslt = false ;
         if (name.toLowerCase().endsWith(".jar") || name.toLowerCase().endsWith(".zip"))
           rslt = true ;
         return rslt ;
       }
     }
}
