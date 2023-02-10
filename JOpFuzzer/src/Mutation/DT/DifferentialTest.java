package Mutation.DT;

import Options.Option;
import Preprocess.ProfileData;
import Preprocess.SourceCodeFeature;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javaslang.Tuple3;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static Utils.Execute.execute;

public class DifferentialTest {
    float[][] StructureOptionRelation = new float[SourceCodeFeature.values().length][Option.values().length];
    String jdkPath;
    Boolean isRunnable = true;
    Boolean isSafe = true;
    Boolean isWorthToSave = true;
    Boolean random = false;
    List<String> options = new ArrayList<>();
    List<String> newOption = new ArrayList<>();
    HashMap<String, String> optionProfiles = new HashMap<>();
    File test;
    String bugCmd;
    float threshold;// threshold for the relation between structure and option
    int selectOptionNumber = 3;// number of options to be selected

    public DifferentialTest(String jdkPath, File test, List<String> originOptions, List<String> changedStructure) throws IOException, ExecutionException, InterruptedException {
        this.jdkPath = jdkPath;
        this.test = test;
        readArray();
        String cmdResult = "timeout 30s " + jdkPath + "/bin/java -Xbatch -XX:+IgnoreUnrecognizedVMOptions -cp " + test.getAbsolutePath() + " Test " + " > " + test.getCanonicalPath() + "/result.log";
        int exitValueResult = execute(cmdResult);
        if (exitValueResult != 0) {
            System.err.println("Result Error:" + test.getCanonicalPath() + " failed");
            isRunnable = false;
        } else {
            if (random) {
                randomSelectOption();
                executeOption(new ArrayList<>());
            } else {
                options = analyzeChangedStructure(changedStructure);
                if (options == null)
                    return;
                executeOption(originOptions);
                if (isSafe)
                    executeProfile();
            }
        }
    }

    private void randomSelectOption() {
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            int index = r.nextInt(Option.values().length);
            options.add(String.valueOf(Option.values()[index]));
        }
    }

    public void executeProfile() throws IOException {
        getProfile();
        String option;
        String profile;
        String cmd;
        HashMap<String, Double> optionSimilarity = new HashMap<>();
        for (Map.Entry<String, String> p : optionProfiles.entrySet()) {
            profile = " -XX:+" + p.getValue() + " ";
            option = getOptionNon_defaultValue(p.getKey());
            cmd = jdkPath + "/bin/java -cp -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions " + test.getCanonicalPath() + " " + profile + " Test " + " > " + test.getCanonicalPath() + "/default.log";
            String cmdProfile = jdkPath + "/bin/java -cp -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions " + test.getCanonicalPath() + " " + profile + " " + option + " Test " + " > " + test.getCanonicalPath() + "/profile.log";
            int exitValue = execute(cmd);
            int exitValueProfile = execute(cmdProfile);
            if (exitValue != 0 || exitValueProfile != 0) {
                System.err.println("Profile Error:" + test.getCanonicalPath() + " failed");
                Files.writeString(Path.of(test.getCanonicalPath() + "/bugCmd.log"), cmd);
                isSafe = false;
                return;
            }
            double similarity = calculateProfileSimilarity();
            optionSimilarity.put(p.getKey(), similarity);
        }
        List<Map.Entry<String, Double>> optionSimilarityList = new ArrayList<>(optionSimilarity.entrySet());
        optionSimilarityList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        if (Double.parseDouble(optionSimilarityList.get(0).getKey()) > threshold && Double.parseDouble(optionSimilarityList.get(1).getKey()) > threshold && Double.parseDouble(optionSimilarityList.get(2).getKey()) > threshold) {
            isWorthToSave = false;
            return;
        }
        for (int i = 0; i < selectOptionNumber; i++) {
            newOption.add(optionSimilarityList.get(i).getKey());
        }
    }

    private double calculateProfileSimilarity() throws IOException {
        double similarity;
        File result = new File(test.getCanonicalPath() + "/result.log");
        File defaultFile = new File(test.getCanonicalPath() + "/default.log");
        File profileFile = new File(test.getCanonicalPath() + "/profile.log");
        List<String> resultList = Files.readAllLines(result.toPath());
        List<String> defaultLines = Files.readAllLines(defaultFile.toPath());
        List<String> profileLines = Files.readAllLines(profileFile.toPath());
        defaultLines.removeAll(resultList);
        profileLines.removeAll(resultList);
        Patch<String> diff = DiffUtils.diff(defaultLines, profileLines);
        double delta = 0;
        for (AbstractDelta<String> p : diff.getDeltas()) {
            delta += p.getTarget().size() + p.getSource().size();
        }
        if (defaultLines.size() + profileLines.size() == 0)
            return 1;
        similarity = delta / defaultLines.size() + profileLines.size();
        Files.delete(defaultFile.toPath());
        Files.delete(profileFile.toPath());
        return similarity;
    }

    private String getOptionNon_defaultValue(String option) {
        int[] result = Option.valueOf(option).getRange();
        if (result[2] == 1) {
            if (result[0] == 1)
                return "-XX:-" + option;
            else
                return "-XX:+" + option;
        } else {
            if (result[0] == result[1])
                return "-XX:" + option + "=" + result[2];
            else
                return "-XX:" + option + "=" + result[1];
        }


    }

    private void getProfile() {
        for (String op : options) {
            for (ProfileData profileData : ProfileData.values()) {
                List<Tuple3<String, List<Integer>, Boolean>> result = profileData.getOptionAndValue();
                for (Tuple3<String, List<Integer>, Boolean> tuple : result) {
                    if (tuple._1.equals(op)) {
                        optionProfiles.put(op, profileData.toString());
                        break;
                    }
                }
            }
        }
    }


    private void executeOption(List<String> originOptions) throws IOException, ExecutionException, InterruptedException {
        Set<String> optionSet = new HashSet<>();
        optionSet.addAll(originOptions);
        optionSet.addAll(options);
        optionSet.remove("");
        String cmd;
        int testNumber = (int) Math.pow(2, optionSet.size());
        ExecutorService threadPool = Executors.newFixedThreadPool(testNumber);
        for (int i = 0; i < testNumber; i++) {
            cmd = "timeout 30s " + jdkPath + "/bin/java -cp -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions " + test.getCanonicalPath() + " " + solveStringSet(optionSet, i) + " Test > " + test.getCanonicalPath() + "/" + i + ".log";
            Future<Integer> result = threadPool.submit(new Task(cmd));
            if (result.get() != 0) {
                System.err.println("Option Error:" + cmd + " failed");
                Files.writeString(Path.of(test.getCanonicalPath() + "/bugCmd.log"), cmd);
                isSafe = false;
            }
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            //wait
        }
        if (!isSafe)
            return;
        compareResult(testNumber);
        options = new ArrayList<>(optionSet);
    }

    private void compareResult(int size) throws IOException {
        File result = new File(test.getCanonicalPath() + "/result.log");
        for (int i = 0; i < size; i++) {
            File file = new File(test.getCanonicalPath() + "/" + i + ".log");
            if (file.exists()) {
                if (!FileUtils.contentEquals(result, file)) {
                    isSafe = false;
                    return;
                }
            }
            Files.delete(file.toPath());
        }
    }

    private String solveStringSet(Set<String> optionSet, int i) {
        List<String> optionList = new ArrayList<>(optionSet);
        int length = optionSet.size();
        String cmd = "";
        for (int j = 0; j < length; j++) {
            cmd += makeOption(optionList.get(j), i % 2);
            i /= 2;
        }
        return cmd;
    }

    private String makeOption(String option, int i) {
        if (option.equals("")) {
            return "";
        }
        int[] result = Option.valueOf(option).getRange();
        if (result[2] != 1) {
            if (i == 1) return " -XX:" + option + "=" + result[2] + " ";
            else return " -XX:" + option + "=" + result[1] + " ";
        } else {
            if (i == 1) return " -XX:+" + option + " ";
            else return " -XX:-" + option + " ";
        }
    }


    private List<String> analyzeChangedStructure(List<String> changedStructure) {
        if (changedStructure.size() == 0)
            return null;
        List<String> options = new ArrayList<>();
        float[] optionRelation = new float[Option.values().length];
        HashMap<Integer, Float> finalOptionRelation = new HashMap<>();
        for (String structure : changedStructure) {
            int index = SourceCodeFeature.valueOf(structure).ordinal();
            for (int i = 0; i < Option.values().length; i++) {
                optionRelation[i] += StructureOptionRelation[index][i];
            }
        }
        float sum = 0;
        for (int i = 0; i < Option.values().length; i++) {
            if (!Float.isNaN(optionRelation[i]))
                sum += optionRelation[i];
        }
        Random random = new Random();
        for (int i = 0; i < selectOptionNumber; i++) {
            float r = random.nextFloat() * sum;
            for (int j = 0; j < Option.values().length; j++) {
                r -= optionRelation[j];
                if (r <= 0) {
                    options.add(Option.values()[j].toString());
                    break;
                }
            }
        }
        return options;
    }

    private void readArray() {
        try {
            Reader reader = new InputStreamReader(new FileInputStream("result.csv"));
            CSVReader csvReader = new CSVReader(reader);
            String[] nextLine;
            int index = 0;
            while ((nextLine = csvReader.readNext()) != null) {
                for (int i = 0; i < nextLine.length; i++) {
                    StructureOptionRelation[index][i] = Float.parseFloat(nextLine[i]);
                    threshold += StructureOptionRelation[index][i];
                }
                index++;
                threshold = threshold / (index * nextLine.length);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Boolean getRunnable() {
        return isRunnable;
    }

    public Boolean getSafe() {
        return isSafe;
    }

    public String getBugCmd() {
        return bugCmd;
    }

    public Boolean getWorthToSave() {
        return isWorthToSave;
    }
}