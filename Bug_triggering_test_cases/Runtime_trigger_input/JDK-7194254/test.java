
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class SelfJStack {
	public static void main(String[] args) throws IOException {
		RuntimeMXBean runtimebean = ManagementFactory.getRuntimeMXBean();
		String pid = getPid(runtimebean);
		System.out.println(pid);
		ThreadGroup group = new ThreadGroup("Priority Threads");
		final boolean[] done = new boolean[1];
		for (int i = Thread.MIN_PRIORITY, ii = Thread.MAX_PRIORITY; i <= ii; ++i) {
			final int priority = i;
			new Thread(group, "Priority=" + priority) {
				{
					setPriority(priority);
				}
				public void run() {
					synchronized (done) {
						while (!done[0]) {
							try {
								done.wait();
							} catch (InterruptedException exc) {
								// ignore
							}
						}
					}
				}
			}.start();
		}
		String jstack = System.getProperty("java.home") + "/../bin/jstack";
		Process process = new ProcessBuilder(jstack, pid).redirectErrorStream(true).start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		try {
			String line;
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} finally {
			reader.close();
		}
		if (Boolean.getBoolean("pause")) System.in.read();
		synchronized (done) {
			done[0] = true;
			done.notifyAll();
		}
	}

	static String getPid(RuntimeMXBean runtimebean) {
		String vmname = runtimebean.getName();
		int i = vmname.indexOf('@');
		if (i != -1) {
			vmname = vmname.substring(0, i);
		}
		return vmname;
	}
}

