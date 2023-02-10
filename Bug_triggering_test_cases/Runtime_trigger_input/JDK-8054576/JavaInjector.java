
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.sun.tools.attach.*;

public class JavaInjector {

	public static void addLibraryPath(String pathToAdd) throws Exception{
		final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
		usrPathsField.setAccessible(true);

		//get array of paths
		final String[] paths = (String[])usrPathsField.get(null);

		//check if the path to add is already present
		for(String path : paths) {
			if(path.equals(pathToAdd)) {
				return;
			}
		}

		//add the new path
		final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
		newPaths[newPaths.length-1] = pathToAdd;
		usrPathsField.set(null, newPaths);
	}

	public static void main(String[] args) {
		try
		{
			addLibraryPath("C:\\Program Files\\Java\\jdk1.8.0_05\\jre\\bin");
		}
		catch(Exception e)
		{
			System.out.println("library path add failed: "+e.getMessage());
		}
		try
		{
			System.load("C:\\Program Files\\Java\\jdk1.8.0_05\\jre\\bin\\attach.dll");
		}
		catch(Exception e)
		{
			System.out.println("library add failed: "+e.getMessage());
		}
		List<VirtualMachineDescriptor> vms = VirtualMachine.list();  
		for (VirtualMachineDescriptor vmd : vms) 
		{  
			if ( (!vmd.displayName().equals("com.amazon.inspector.javainjector.JavaInjector")) &&
					(!vmd.displayName().contains("eclipse.equinox.launcher_1.3.0.v20140415-2008.jar"))
					)
			{
				System.out.println("[+] " + vmd.id() + " " + vmd.displayName());  
				VirtualMachine vm;
				
				try 
				{
					vm = VirtualMachine.attach(vmd);
					vm.loadAgent("c:\\temp\\InjectorAgent.jar");  
					System.out.println("[+] Injected agent into process " + vmd);  
					vm.detach();  
				} catch (AttachNotSupportedException | IOException | AgentLoadException | AgentInitializationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}

		}  
	}

}




