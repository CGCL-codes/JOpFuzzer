package Preprocess;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import javaslang.Tuple3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static Utils.Execute.execute;

public class Task implements Runnable {

    private final Tuple3<String, List<Integer>, Boolean> tuple;
    private final int optionIndex;
    private final Encode enc;
    private final String methodName;
    private final String cmdOn;
    private final String cmdOff;
    private double[][] StructureOptionRelation;
    private int[][] StructureOptionRelationTime;
    private final String seedPath;

    public Task(Tuple3<String, List<Integer>, Boolean> tuple, Encode enc, String cmdOn, String cmdOff, int optionIndex, double[][] StructureOptionRelation, int[][] StructureOptionRelationTime, String seedPath) {
        this.tuple = tuple;
        this.optionIndex = optionIndex;
        this.methodName = enc.getMethodName();
        this.enc = enc;
        this.cmdOn = cmdOn;
        this.cmdOff = cmdOff;
        this.StructureOptionRelation = StructureOptionRelation;
        this.StructureOptionRelationTime = StructureOptionRelationTime;
        this.seedPath = seedPath;
    }

    @Override
    public void run() {
        System.out.println("Running task\n");
        int exitValue1, exitValue2;
        try {
            exitValue1 = execute(cmdOn);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            exitValue2 = execute(cmdOff);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        double similarity;

        try {
            if (exitValue1 == 0 && exitValue2 == 0) {
                similarity = CalculateProfileSimilarity(seedPath, tuple._1, methodName);
            } else {
                similarity = -1;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Integer> structureIndex = enc.getStructureIndex();
        for (Integer index : structureIndex) {
            synchronized (this) {
                if (similarity == -1)
                    break;
                StructureOptionRelation[index][optionIndex] += similarity;
                StructureOptionRelationTime[index][optionIndex]++;
            }
        }
    }

    private double CalculateProfileSimilarity(String seedPath, String option, String methodName) throws IOException {
        List<String> result = Files.readAllLines(Paths.get(seedPath + "result"));
        Path path_on = Paths.get(seedPath + option + "_" + methodName + "_on");
        Path path_off = Paths.get(seedPath + option + "_" + methodName + "_off");
        List<String> dProfile = Files.readAllLines(path_on);
        List<String> sProfile = Files.readAllLines(path_off);
        if (dProfile.size() > 10_000 || sProfile.size() > 10_000) {
            Files.delete(path_on);
            Files.delete(path_off);
            return -1;
        }

        dProfile.removeAll(result);
        sProfile.removeAll(result);
        Patch<String> diff = DiffUtils.diff(dProfile, sProfile);
        double originLineNumber = dProfile.size() + sProfile.size();
        double similarity;
        int delta = 0;
        for (AbstractDelta<String> p : diff.getDeltas()) {
            delta += p.getSource().size() + p.getTarget().size();
        }
        if (delta > 0) {
            similarity = delta / originLineNumber;
        } else {
            similarity = 0;
        }
        Files.delete(path_on);
        Files.delete(path_off);
        return similarity;
    }

//    public int execute(String cmd) throws IOException {
//        String osName = System.getProperty("os.name");
//        Process p;
//        String[] command;
//        if (osName.toLowerCase().contains("windows")) {
//            command = new String[]{"cmd", "/C", cmd + " 2>&1"};
//        } else {
//            command = new String[]{"/bin/sh", "-c", cmd + " 2>&1"};
//        }
//        p = Runtime.getRuntime().exec(command);
//        InputStream fis = p.getInputStream();
//        InputStreamReader isr = new InputStreamReader(fis);
//
//        BufferedReader br = new BufferedReader(isr);
//        String line = br.readLine();
//        while (line != null) {
//            line = br.readLine();
//        }
//        int exitValue;
//        try {
//            exitValue = p.waitFor();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        return exitValue;
//    }
}