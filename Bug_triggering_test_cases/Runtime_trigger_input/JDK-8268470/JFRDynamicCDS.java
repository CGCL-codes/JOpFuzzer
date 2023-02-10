/*


rm -rf JFRDynamicCDS.tmp
mkdir -p JFRDynamicCDS.tmp
javac -d JFRDynamicCDS.tmp JFRDynamicCDS.java
jar cvf JFRDynamicCDS.jar -C JFRDynamicCDS.tmp .

java -cp JFRDynamicCDS.jar -Xlog:cds -XX:ArchiveClassesAtExit=JFRDynamicCDS.jsa JFRDynamicCDS
java -cp JFRDynamicCDS.jar -Xlog:cds -XX:SharedArchiveFile=JFRDynamicCDS.jsa JFRDynamicCDS

 */

import java.nio.file.Paths;
import jdk.jfr.Configuration;
import jdk.jfr.Description;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingStream;

public class JFRDynamicCDS {
    public static void main(String args[]) throws Exception {
        int mode = 4;
        try {
            mode = Integer.parseInt(args[0]);
        } catch (Throwable t) {}

        if (mode > 0) {
            RecordingStream rs = new RecordingStream();
            if (mode > 1) {
                rs.enable("JFRDynamicCDS.StressEvent");
                rs.startAsync();

                if (mode > 2) {
                    Recording recording = startRecording();
                    if (mode > 3) {
                        loop();
                    }
                    recording.stop();
                    recording.close();
                }
            }

            rs.close();
        }
    }

    static Recording startRecording() throws Exception {
        Configuration configuration = Configuration.getConfiguration("default");
        Recording recording = new Recording(configuration);

        recording.setName("internal");
        recording.enable(StressEvent.class);
        recording.setDestination(Paths.get("JFRDynamicCDS.jfr"));
        recording.start();
        return recording;
    }


    static void loop() {
        for (int i=0; i<100; i++) {
            StressEvent event = new StressEvent();
            event.iteration = i;
            event.description = "Stressful Event, take it easy!";
            event.customClazz = StressEvent.class;
            event.value = i;
            event.commit();
        }
    }


    /**
     * Internal StressEvent class.
     */
    @Label("Stress Event")
    @Description("A duration event with 4 entries")
    @Name("JFRDynamicCDS.StressEvent")
    public static class StressEvent extends jdk.jfr.Event {
        public Class<?> customClazz;
        public String description;
        public int iteration;
        public double value;
    }
}

