package Preprocess;

import Options.*;
import com.opencsv.CSVWriter;
import javaslang.Tuple3;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Utils.Execute.execute;

public class RelationMapping {
    double[][] StructureOptionRelation = new double[SourceCodeFeature.values().length][Option.values().length];
    int[][] StructureOptionRelationTime = new int[SourceCodeFeature.values().length][Option.values().length];

    public void mapping(List<Encode> encodes, Config config) throws IOException {
        String jdkPath = config.getJdkPath();
        String seedPath = config.getSeedPath();
        String seedName = config.getSeedName();
        String javac = jdkPath + "/bin/javac " + seedPath + seedName;
        int javacExitValue = execute(javac);
        if (javacExitValue != 0) {
            System.err.println(config.getSeedPath() + config.getSeedName() + " compiles error!");
            return;
        } else System.out.println(config.getSeedPath() + config.getSeedName() + " compiles success!");
        String classPath = "";
        String packageName = config.getPackageName();
        seedName = seedName.replace(".java", "");
        if (packageName == null) {
            classPath = " -cp " + seedPath;
        } else {
            seedName = packageName + "." + seedName;
        }
        String cmd = "timeout 30s " + jdkPath + "/bin/java" + classPath + " " + seedName + " > " + seedPath + "result";
        int resultValue = execute(cmd);
        if (resultValue != 0) {
            System.err.println(config.getSeedPath() + config.getSeedName() + " runs error!");
            System.err.println(cmd);
            return;
        } else System.out.println(config.getSeedPath() + config.getSeedName() + " runs success!");
        for (Encode enc : encodes) {
            String methodName = enc.getMethodName();
            String excludeMethod = "-XX:CompileCommand=exclude," + seedName.replaceAll("\\.", "/") + "." + methodName;
            int optionIndex = 0;
            ExecutorService es = Executors.newFixedThreadPool(Option.values().length);
            for (ProfileData p : ProfileData.values()) {
                String profile = "-XX:+" + p.toString();
                for (Tuple3<String, List<Integer>, Boolean> tuple : p.getOptionAndValue()) {
                    String option = makeOption(tuple, p);
                    String methodOn = "timeout 30s " + jdkPath + "/bin/java -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions " + classPath + " " + profile + option + " " + seedName + " > " + seedPath + tuple._1 + "_" + methodName + "_on";
                    String methodOff = "timeout 30s " + jdkPath + "/bin/java -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions " + classPath + " " + profile + option + " " + excludeMethod + " " + seedName + " > " + seedPath + tuple._1 + "_" + methodName + "_off";
                    es.execute(new Task(tuple, enc, methodOn, methodOff, optionIndex, StructureOptionRelation, StructureOptionRelationTime, seedPath));
                    optionIndex++;
                }
            }
            es.shutdown();
            while (!es.isTerminated()) {
                //wait
            }
        }

    }

    private String makeOption(Tuple3<String, List<Integer>, Boolean> tuple, ProfileData p) {
        String option = " ";
        for (Tuple3<String, List<Integer>, Boolean> tuple2 : p.getOptionAndValue()) {
            if (tuple._1.equals(tuple2._1)) { //open the selected option
                if (tuple2._3) option += "-XX:+" + tuple2._1 + " ";
                else option += "-XX:" + tuple2._1 + "=" + tuple2._2.get(1) + " ";
            } else {
                if (tuple2._3) option += "-XX:-" + tuple2._1 + " ";
                else option += "-XX:" + tuple2._1 + "=" + tuple2._2.get(0) + " ";
            }
        }
        return option;
    }


    public void saveArray() throws IOException {
        File result = new File("./result.csv");
        FileWriter outputFile = new FileWriter(result);
        CSVWriter writer = new CSVWriter(outputFile);
        for (int i = 0; i < SourceCodeFeature.values().length; i++) {
            String[] value = Arrays.stream(StructureOptionRelation[i]).mapToObj(String::valueOf).toArray(String[]::new);
            writer.writeNext(value);
        }
        writer.close();
    }

    public void calculateFinalRelationship() {
        for (int i = 0; i < SourceCodeFeature.values().length; i++) {
            for (int j = 0; j < Option.values().length; j++) {
                StructureOptionRelation[i][j] = StructureOptionRelation[i][j] / StructureOptionRelationTime[i][j];
            }
        }
    }

    public double[][] getStructureOptionRelation() {
        return StructureOptionRelation;
    }


}

