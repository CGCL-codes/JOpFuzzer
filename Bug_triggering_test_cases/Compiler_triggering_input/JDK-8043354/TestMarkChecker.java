import java.util.ArrayList;
import java.util.List;

public class TestMarkChecker {

    static TestMarkChecker dummy;

    class Marker {
    }

    List<Marker> markerList = new ArrayList<>();

    // Suppress compilation of this method, it must be processed
    // by the bytecode escape analyzer.

    // Make a new marker and put it on the List
    Marker getMarker() {
        // result escapes through markerList
        final Marker result = new Marker();
        markerList.add(result);
        return result;
    }

    void visit(int depth) {
        // Make a new marker
        getMarker();

        // Call visitAndPop every once in a while
        // Cap the depth of our recursive visits
        if (depth % 10 == 2) {
            visitAndPop(depth + 1);
        } else if (depth < 15) {
            visit(depth + 1);
        }
    }

    void visitAndPop(int depth) {
        // Random dummy allocation to force EscapeAnalysis to process this method
        dummy = new TestMarkChecker();

        // Make a new marker
        Marker marker = getMarker();

        visit(depth + 1);

        // Walk and pop the marker list up to the current marker
        boolean found = false;
        for (int i = markerList.size() - 1; i >= 0; i--) {
            Marker removed = markerList.remove(i);

            // In the failure, EA mistakenly converts this comparison to false
            if (removed == marker) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("test fails");
        }
    }


    public static void main(String args[]) {
        TestMarkChecker tc = new TestMarkChecker();

        // Warmup and run enough times
        for (int i = 0; i < 20000; i++) {
            tc.visit(0);
        }
    }
}