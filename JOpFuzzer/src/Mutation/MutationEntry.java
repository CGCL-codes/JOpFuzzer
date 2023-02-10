package Mutation;

import Mutation.DT.DifferentialTest;
import Preprocess.Encode;
import Preprocess.SourceCodeFeature;
import org.apache.commons.io.FileUtils;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.*;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.LineFilter;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static Utils.Execute.execute;

public class MutationEntry {
    String separator = System.getProperty("file.separator");

    public MutationEntry(String jdkPath, SeedPool seedPool, String mr) throws IOException, ExecutionException, InterruptedException {
        int mutationRound = Integer.parseInt(mr);//the mutation time
        String projectPath = "./JavaFuzzer/tests/"; // source code root path. e.g. /home/kui/Desktop/buggyProject
        int lineNumber; // line number of target file to be mutated. e.g. 10
        List<File> seeds = new ArrayList<>(seedPool.seedPoolOptionPair.keySet());
        Random rand = new Random();
        int seedIndex;
        int format = String.valueOf(seeds.size()).length();
        while (mutationRound-- > 0) {

            seedIndex = rand.nextInt(seeds.size());
            File dir = seeds.get(seedIndex);
            lineNumber = analyzeFile(dir);
            int testNumber = seedPool.getTestNumber();
            String sIndex = String.format("%0" + format + "d", seedIndex);
            String tIndex = String.format("%0" + format + "d", testNumber + 1);
            String mutate = jdkPath + "/bin/java -jar JavaMutator.jar " + projectPath + " " + sIndex + " " + tIndex + " " + lineNumber + " " + jdkPath;
            while (true) {
                int exitValue = execute(mutate);
                if (exitValue == 0) {
                    System.out.println("Mutation success!");
                    break;
                }
            }
            String originPath = projectPath + sIndex;
            String targetPath = projectPath + tIndex;
            List<String> changedStructure = new ArrayList<>(new HashSet<>(getChangedStructure(originPath, targetPath)));
            File mutatedFile = new File(targetPath);
            DifferentialTest differentialTest = new DifferentialTest(jdkPath, mutatedFile, seedPool.seedPoolOptionPair.get(dir), changedStructure);
            if (!differentialTest.getRunnable()) {
                FileUtils.deleteDirectory(mutatedFile);
                continue;
            }
            if (!differentialTest.getWorthToSave()) {
                FileUtils.deleteDirectory(mutatedFile);
                continue;
            }
            if (!differentialTest.getSafe()) {
                Files.createDirectories(Paths.get("Bug"));
                FileUtils.moveDirectory(dir, new File("Bug" + separator));
            }
            seedPool.addTestNumber();
        }
    }

    private List<String> getChangedStructure(String originPath, String targetPath) {
        List<String> changedStructure = new ArrayList<>();
        int start, end;
        try {
            List<String> lines = Files.readAllLines(Path.of(targetPath + "/patchLine.log"));
            String line = lines.get(0);
            start = Integer.parseInt(line.trim().split(" ")[0]);
            end = Integer.parseInt(line.trim().split(" ")[1]);
        } catch (IOException e) {
            return changedStructure;
        }
        Launcher launcher = new Launcher();
        launcher.addInputResource(originPath + "/Test.java");
        launcher.buildModel();
        CtModel model = launcher.getModel();
        List<CtStatement> statements = model.getElements(new LineFilter());
        for (CtStatement statement : statements) {
            SourcePosition position = statement.getPosition();
            if (position.isValidPosition()) {
                if (start >= position.getLine() && position.getEndLine() >= end) {
                    Encode encode = new Encode(statement);
                    for (Integer i : encode.getStructureIndex()) {
                        changedStructure.add(SourceCodeFeature.values()[i].toString());
                    }
                }
            }
        }
        return changedStructure;
    }

    private int analyzeFile(File dir) throws IOException {
        int statementIndex = 0;
        Launcher launcher = new Launcher();
        launcher.addInputResource(dir.getCanonicalPath() + separator + "Test.java");
        launcher.buildModel();
        CtModel model = launcher.getModel();
        List<CtStatement> lines = model.getElements(new LineFilter());
        List<Integer> statementValues = new ArrayList<>();
        for (CtStatement ctStatement : lines) {
            statementValues.add(analyzeStatement(ctStatement));
        }
        int sum = statementValues.stream().mapToInt(Integer::intValue).sum();
        Random rand = new Random();
        int randomValue = rand.nextInt(sum);
        for (int i = 0; i < statementValues.size(); i++) {
            randomValue -= statementValues.get(i);
            if (randomValue <= 0) {
                statementIndex = i;
                break;
            }
        }
        return lines.get(statementIndex).getPosition().getLine();
    }

    private int analyzeStatement(CtStatement statement) {
        int value = 0;
        int inLoop = 0;
        if (statement instanceof CtFor) {
            inLoop = 1; // since statement is a loop.
            CtFor ctFor = (CtFor) statement;
            if (ctFor.getExpression().getElements(new TypeFilter<>(CtFieldAccess.class)).size() > 0) {
                value += 1;
            }
            if (ctFor.getExpression().getElements(new TypeFilter<>(CtBinaryOperator.class)).size() > 0) {
                value += 1;
            }
            for (CtStatement ctStatement : ctFor.getForInit()) {
                value += analyzeStatement(ctStatement);
            }
            for (CtStatement ctStatement : ctFor.getForUpdate()) {
                value += analyzeStatement(ctStatement);
            }
        } else if (statement instanceof CtWhile) {
            inLoop = 1;
            CtWhile ctWhile = (CtWhile) statement;
            if (ctWhile.getLoopingExpression().getElements(new TypeFilter<>(CtFieldAccess.class)).size() > 0) {
                value += 1;
            }
            if (ctWhile.getLoopingExpression().getElements(new TypeFilter<>(CtBinaryOperator.class)).size() > 0) {
                value += 1;
            }
        } else if (statement instanceof CtIf) {
            CtIf ctIf = (CtIf) statement;
            if (ctIf.getCondition().getElements(new TypeFilter<>(CtFieldAccess.class)).size() > 0) {
                value += 1;
            }
            if (ctIf.getCondition().getElements(new TypeFilter<>(CtBinaryOperator.class)).size() > 0) {
                value += 1;
            }
        } else if (statement instanceof CtTry) {

        } else {
            if (statement.getElements(new TypeFilter<>(CtFieldAccess.class)).size() > 0) {
                value += 1;
            }
            if (statement.getElements(new TypeFilter<>(CtBinaryOperator.class)).size() > 0) {
                value += 1;
            }
        }
        if (inLoop != 1) {
            inLoop = checkInLoop(statement);
        }
        return value + inLoop;
    }

    private int checkInLoop(CtStatement statement) {
        int inLoop = 0;
        CtElement parent = statement.getParent();
        while (parent != null) {
            if (parent instanceof CtFor || parent instanceof CtWhile) {
                inLoop = 1;
                break;
            }
            parent = parent.getParent();
        }
        return inLoop;
    }
}
