package Mutation;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static Utils.Execute.execute;

public class SeedPool {
    int seedNumber;
    String jdkPath;
    HashMap<File, List<String>> seedPoolOptionPair = new HashMap<>();

    public SeedPool(String seedNumber, String jdkPath) throws IOException {
        this.seedNumber = Integer.parseInt(seedNumber);
        this.jdkPath = jdkPath;
        generate();
        initialize();
    }

    private void initialize() {
        File rootDir = new File("./JavaFuzzer/tests/");
        for (File dir : Objects.requireNonNull(rootDir.listFiles())) {
            if (dir.isDirectory())
                seedPoolOptionPair.put(dir, Collections.singletonList(""));
        }
    }

    private void generate() throws IOException {
        System.out.println("Generating seed");
        String cmd = "bash generate.sh " + seedNumber + " " + jdkPath;
        int exitValue = execute(cmd, "./JavaFuzzer");
        if (exitValue != 0) {
            System.err.println("Error: generate seed failed");
            System.exit(1);
        }
    }

    public int getTestNumber() {
        return seedNumber;
    }

    public void addTestNumber() {
        this.seedNumber++;
    }

    public void pairOption(File dir, List<String> option) {
        seedPoolOptionPair.put(dir, option);
    }

}
