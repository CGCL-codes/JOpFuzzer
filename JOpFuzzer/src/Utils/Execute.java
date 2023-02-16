package Utils;

import java.io.*;

public class Execute {
    public static int execute(String cmd) throws IOException {
        String osName = System.getProperty("os.name");
        String[] command;
        if (osName.toLowerCase().contains("windows")) {
            command = new String[]{"cmd", "/C", cmd + " 2>&1"};
        } else {
            command = new String[]{"/bin/sh", "-c", cmd + " 2>&1"};
        }
        Process p = Runtime.getRuntime().exec(command);
//        Process p = Runtime.getRuntime().exec(cmd);
        InputStream fis = p.getInputStream();
        InputStreamReader isr = new InputStreamReader(fis);

        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        while (line != null) {
            line = br.readLine();
        }
        int exitValue;
        try {
            exitValue = p.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return exitValue;
    }

    public static int execute(String cmd, String file) throws IOException {
        String osName = System.getProperty("os.name");
        String[] command;
        if (osName.toLowerCase().contains("windows")) {
            command = new String[]{"cmd", "/C", cmd + " 2>&1"};
        } else {
            command = new String[]{"/bin/sh", "-c", cmd + " 2>&1"};
        }
        Process p = Runtime.getRuntime().exec(command, null, new File(file));
        InputStream fis = p.getInputStream();
        InputStreamReader isr = new InputStreamReader(fis);

        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        while (line != null) {
            line = br.readLine();
        }
        int exitValue;
        try {
            exitValue = p.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return exitValue;
    }
}