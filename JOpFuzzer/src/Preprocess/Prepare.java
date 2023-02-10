package Preprocess;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Prepare {
    List<String> seeds = null;
    Config config;

    public Prepare(Config config) throws IOException {
        this.config = config;
        try (Stream<Path> paths = Files.walk(Paths.get(config.getSeedsPath()))) {
            seeds = paths.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error: seed path is not valid");
            System.exit(1);
        }
    }

    public void preprocess() throws IOException {
        String separator = System.getProperty("file.separator");
        RelationMapping map = new RelationMapping();
        BufferedWriter out = new BufferedWriter(new FileWriter("encodeClass.log")); // the encoded class will be written to this file
        for (String seed : seeds) {
            if (seed.endsWith(".java")) {
                out.write(seed + "\n");
                out.flush();
                Config seedConfig = config.clone();
                seedConfig.setSeedPath(seed.substring(0, seed.lastIndexOf(separator)));
                seedConfig.setSeedName(seed.substring(seed.lastIndexOf(separator) + 1));
                List<Encode> encodes = encodingMethod(seed, seedConfig);
                map.mapping(encodes, seedConfig);
                clearHotSpotLog();
            }
        }
        out.close();
        map.calculateFinalRelationship();
        map.saveArray();
    }

    public List<Encode> encodingMethod(String classNameWithPath, Config seedConfig) {
        Launcher launcher = new Launcher();
        launcher.addInputResource(classNameWithPath);
        launcher.buildModel();
        CtModel model = launcher.getModel();
        Collection<CtPackage> packages = model.getAllPackages();
        for (CtPackage p : packages) {
            if (p.getPackages().size() == 0) {
                if (!p.toString().equals("unnamed package"))
                    seedConfig.setPackageName(p.toString());
            }
        }
        List<CtMethod> methods = model.getElements(new TypeFilter<>(CtMethod.class));
        List<Encode> encodes = new ArrayList<>();
        for (CtMethod method : methods) {
            Encode encode = new Encode(method);
            if (!encode.uselessMethod) {
                encodes.add(encode);
            }
        }
        return encodes;
    }

    public void clearHotSpotLog() {
        File dir = new File(".");
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.getName().endsWith(".log") && file.getName().startsWith("hotspot_pid")) {
                file.delete();
            }
        }
    }

}