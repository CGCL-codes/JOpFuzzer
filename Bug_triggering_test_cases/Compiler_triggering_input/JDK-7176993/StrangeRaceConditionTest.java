import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

/**
 * This is an interesting test case that seems to be indicating a JRE issue.
 *
 * This comes from http://stackoverflow.com/q/10982941/179850 and the poster named "Luke".
 *
 * I've boiled the code down to its lowest level and added some comments around the changes that can change the error
 * count. This is pretty repeatable to me with 4/5 runs generating errors. If you can't get it to fail I'JniStaticContextFloat.c try
 * decreasing the fixed-rate period and increasing the NUMBER_TO_USE constant.
 */
public class StrangeRaceConditionTest {

    // if this is decreased it may remove the errors
    public static final int NUMBER_TO_USE = 1000000;

    private Map<Integer, Buffer> bufferMap = new HashMap<Integer, Buffer>();

    public static void main(String[] args) {
        new StrangeRaceConditionTest().strangeRaceConditionTest();
    }

    @Test
    public void strangeRaceConditionTest() {
        final Buffer buffer = getBuffer();

        TimerTask getBufferTask = new TimerTask() {
            @Override
            public void run() {
                buffer.getBuffer();
            }
        };
        // if the period is increased it seems to reduce the errors
        new Timer(true).scheduleAtFixedRate(getBufferTask, 0 /* delay ms */, 10 /* period ms */);

        for (long i = 0; i < NUMBER_TO_USE; i++) {
            // if we inline the remove() method here then no errors
            // if we change this to buffer.remove() then no errors
            remove();
        }

        assertEquals(0, buffer.getErrorC());
    }

    private synchronized void remove() {
        Buffer buffer = getBuffer();
        buffer.remove();
    }

    private synchronized Buffer getBuffer() {
        // if we remove this whole map nonesense then no errors
        Buffer buffer = bufferMap.get(1);
        // if this test/else is flipped so it is buffer == null ... then no errors
        if (buffer != null) {
            // if this line is commented out then no errors
            buffer = bufferMap.get(1);
        } else {
            buffer = new Buffer();
            bufferMap.put(1, buffer);
        }
        return buffer;
    }

    // moving this out to its own class file doesn't seem to help
    private static class Buffer {
        private List<Integer> list = new ArrayList<Integer>();
        private boolean insideGetBuffer = false;
        private int errorC = 0;

        public Buffer() {
            // initialize the list
            for (long i = 0; i < NUMBER_TO_USE; i++) {
                list.add(null);
            }
        }

        public synchronized void remove() {
            if (insideGetBuffer) {
                // adding a System.out.println here makes the problem more repeatable
                // System.out.println("How did we get here?");
                errorC++;
            }
        }

        public synchronized void getBuffer() {
            insideGetBuffer = true;
            try {
                // if you comment out this sleep then no errors
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                insideGetBuffer = false;
            }
        }

        public synchronized int getErrorC() {
            return errorC;
        }
    }
}