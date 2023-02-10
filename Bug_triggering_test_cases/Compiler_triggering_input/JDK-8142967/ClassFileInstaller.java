public class ClassFileInstaller {
    /**
     * @param args The names of the classes to dump
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        for (String arg : args) {
            ClassLoader cl = ClassFileInstaller.class.getClassLoader();

            // Convert dotted class name to a path to a class file
            String pathName = arg.replace('.', '/').concat(".class");
            InputStream is = cl.getResourceAsStream(pathName);

            // Create the class file's package directory
            Path p = Paths.get(pathName);
            if (pathName.contains("/")) {
                Files.createDirectories(p.getParent());
            }
            // Create the class file
            Files.copy(is, p, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}