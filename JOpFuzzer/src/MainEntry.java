import Mutation.SeedPool;
import Mutation.MutationEntry;
import Preprocess.Config;
import Preprocess.Prepare;
import org.apache.commons.cli.*;

public class MainEntry {
    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("jdkPath", true, "the tested jdk path");
        options.addOption("regressionTestPath", true, "the regression test path");
        options.addOption("seedNumber", true, "the seed number generated by JavaFuzzer");
        options.addOption("mutationRound", true, "the mutation round");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        String jdkPath = cmd.getOptionValue("jdkPath");
        String regressionTestPath = cmd.getOptionValue("regressionTestPath");
        String seedNumber = cmd.getOptionValue("seedNumber");
        String mutationRound = cmd.getOptionValue("mutationRound");
        if (jdkPath == null || regressionTestPath == null || seedNumber == null || mutationRound == null) {
            System.err.println("Error: missing arguments");
            System.out.println("Usage: java -jar JOpFuzzer.jar -jdkPath <jdkPath> -regressionTestPath <regressionTestPath> -seedNumber <seedNumber> -mutationRound <mutationRound>");
            System.exit(1);
        }
        Config config = new Config(jdkPath, regressionTestPath);
        Prepare prepare = new Prepare(config);
        prepare.preprocess();//preprocess the seeds and construct the prior knowledge from the regression test
        SeedPool seedPool = new SeedPool(seedNumber, jdkPath); //generate the seed pool
        MutationEntry mutationEntry = new MutationEntry(jdkPath, seedPool, mutationRound);

    }
}