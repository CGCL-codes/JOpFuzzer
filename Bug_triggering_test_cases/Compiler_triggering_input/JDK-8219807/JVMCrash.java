
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JVMCrash  {

    private static void testCrash(Logger log) {
        printLog(log);

        GilStopWatch sw1 = new GilStopWatch();

        int operationID2 = 4;

        log.log(Level.INFO,() -> sw1.pointStop("ID " + operationID2));

        GilStopWatch sw2 = new GilStopWatch();
        log.log(Level.INFO, () -> sw2.pointStop("ID " + operationID2));
    }

    private static void printLog(Logger log) {
        GilStopWatch sw = new GilStopWatch();
        log.log(Level.FINE, ()->sw.pointStop("ID"));

    }

    public static void main(String[] args) {
        Logger logger = new AlonNamedWFLogger("Crash1", "Crash2");

        for (int i = 0 ; i < 10000000 ; ++i) {
            testCrash(logger);
        }
    }
}

class GilStopWatch {
    public String pointStop(String processName) {
        String current = " Finished , "+String.valueOf(3);
        System.nanoTime();
        return current;
    }

}

class GilLoggerLevels  {
    private static final Level CAUTION = new Level("CAUTION", 950, Level.WARNING.getResourceBundleName()) {
    };

    private GilLoggerLevels() {
    }
    public static Level[] levelValues() {
        return new Level[]{Level.OFF, Level.SEVERE, CAUTION, Level.WARNING, Level.INFO, Level.CONFIG, Level.FINE, Level.FINER, Level.FINEST};
    }
}

class GilLogFormatter extends java.util.logging.Formatter {
    private boolean osDependentEOL;
    private String lineSeparator;
    private DateFormat dateFormat;
    private Date currentDate;
    private FieldPosition fieldPosition;
    private StringBuffer buffer;
    private final int fieldLevelWidth;
    private final int fieldThreadIDWidth;

    private StringBuilder headerBuilder;

    public GilLogFormatter(boolean osDependentEOL) {
        this(osDependentEOL, false);
    }

    public GilLogFormatter(boolean osDependentEOL, boolean isYearRequired) {
        this.lineSeparator = System.getProperty("line.separator");
        this.currentDate = new Date();
        this.fieldPosition = new FieldPosition(0);
        this.buffer = new StringBuffer(1024);
        this.osDependentEOL = osDependentEOL;
        if (isYearRequired) {
            this.dateFormat = getDateFormatWithYear();
        } else {
            this.dateFormat = this.getDateFormat();
        }

        this.fieldLevelWidth = Arrays.stream(GilLoggerLevels.levelValues()).mapToInt((l) -> {
            return l.getName().length();
        }).max().getAsInt();
        this.fieldThreadIDWidth = 6;
    }

    public GilLogFormatter() {
        this(true);
    }

    public DateFormat getDateFormat() {
        return getDateFormatDefault();
    }

    public static DateFormat getDateFormatDefault() {
        return new SimpleDateFormat("MMM dd,HH:mm:ss.SSS ", Locale.US);
    }

    public static DateFormat getDateFormatWithYear() {
        return new SimpleDateFormat("MMM dd,YYYY HH:mm:ss.SSS ", Locale.US);
    }

    public String format(LogRecord record) {
        this.currentDate.setTime(record.getMillis());
        this.buffer.setLength(0);
        this.dateFormat.format(this.currentDate, this.buffer, this.fieldPosition);
        this.buffer.append(record.getLevel().toString());
        this.buffer.append(' ');
        String message = record.getMessage();
        if (message != null) {
            Object[] parameters = record.getParameters();
            if (parameters != null && parameters.length > 0 && (message.contains("{0") || message.contains("{1") || message.contains("{2") || message.contains("{3"))) {
                message = MessageFormat.format(message, parameters);
            }

            this.buffer.append(message);
            if (this.osDependentEOL) {
                if (!message.endsWith(this.lineSeparator)) {
                    this.buffer.append(this.lineSeparator);
                }
            } else {
                if (message.endsWith(this.lineSeparator)) {
                    this.buffer.setLength(this.buffer.length() - this.lineSeparator.length());
                }

                this.buffer.append('\n');
            }
        }

        return this.buffer.toString();
    }

    public String getHead(Handler h) {
        return this.headerBuilder != null ? "-------------------------- Log Header -------------------------" + this.lineSeparator + this.headerBuilder + this.lineSeparator + "---------------------------------------------------------------" + this.lineSeparator : "";
    }
}

class AlonWFLogger extends Logger {
    protected AlonWFLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }
}

class AlonNamedWFLogger extends AlonWFLogger {
    public AlonNamedWFLogger(String loggerName, String name) {
        super(loggerName, null);
        addHandler(AlonGlobalLogHandler.instance);
    }
}

class AlonGlobalLogHandler extends Handler{

    public static final Handler instance = new AlonGlobalLogHandler();

    private AlonGlobalLogHandler() {
    }

    @Override
    public void publish(LogRecord record) {
        System.out.print(new GilLogFormatter(true).format(record));
    }

    @Override
    public void flush() {
        System.out.flush();
    }

    @Override
    public void close() throws SecurityException {
    }

}

