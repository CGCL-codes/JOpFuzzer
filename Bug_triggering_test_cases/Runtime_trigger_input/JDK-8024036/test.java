
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class SleepTest
{
    private TimeZone defaultTimeZone = null;
    private final String TIME_FORMAT = " HH:mm:ss ";
    private SimpleDateFormat timeFormat = null;

    SleepTest(int seconds)
    {
        this.defaultTimeZone = TimeZone.getDefault();

        this.timeFormat = new SimpleDateFormat(this.TIME_FORMAT);
        this.timeFormat.setTimeZone(this.defaultTimeZone);

        System.out.println("interval: " + seconds + " seconds");
        System.out.println(Calendar.getInstance(this.defaultTimeZone).getTime());

        for (;;)
        {
            try
            {
                Thread.sleep(seconds * 1000);
                System.out.println(Calendar.getInstance(this.defaultTimeZone).getTime());
            }
            catch (Exception e)
            {
                System.out.println(Calendar.getInstance(this.defaultTimeZone).getTime());
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args)
    {
        int seconds = 1;
        if (args.length > 0)
        {
            seconds = Integer.valueOf(args[0]);
        }

        new SleepTest(seconds);

        try
        {
            Thread.sleep(1000000000);
        }
        catch (Exception e)
        {
        }
    }
}

