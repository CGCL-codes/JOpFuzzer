package Preprocess;

public class Config {
    String jdkPath;
    String seedsPath;
    String seedPath;
    String seedName;
    String packageName;

    public Config(String jdkPath, String seedsPath) {
        this.jdkPath = jdkPath.trim();
        this.seedsPath = seedsPath.trim();
        String separator = System.getProperty("file.separator");
        if (!this.seedsPath.endsWith(separator)) {
            this.seedsPath += separator;
        }
    }

    public String getJdkPath() {
        return jdkPath;
    }

    public String getSeedPath() {
        return seedPath;
    }

    public void setSeedPath(String seedPath) {
        this.seedPath = seedPath.trim();
        String separator = System.getProperty("file.separator");
        if (!this.seedPath.endsWith(separator)) {
            this.seedPath += separator;
        }
    }

    public String getSeedsPath() {
        return seedsPath;
    }

    public void setSeedsPath(String seedPath) {
        this.seedsPath = seedPath;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Config clone() {
        return new Config(jdkPath, seedsPath);
    }
}