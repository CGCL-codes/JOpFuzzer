public class test{
public static void open() {
        Options options = new Options();
        options.createIfMissing(true);
        options.compressionType(CompressionType.SNAPPY);
        int fails = 0;

        try {
            while (!opened && fails < 3) {
                try {
                    // start from scratch each time
                    db = factory.open(new File(LEVELDB_FILE), options);
                    opened = true;
                } catch (NativeDB.DBException e) {
                    org.apache.commons.io.FileUtils.deleteQuietly(new File(String.format("%s%s%s", LEVELDB_FILE, File.separator, "LOCK")));
                    fails++;
                }
            }
            log.info("LevelDB [{}] started...", LEVELDB_FILE);
        } catch (IOException e) {
            log.warn("error opening LevelDB database", e);
        }
    }
}