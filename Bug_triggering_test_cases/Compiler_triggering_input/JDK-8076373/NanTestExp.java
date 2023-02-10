import java.nio.ByteBuffer;

class NanTestExp {
    static double dValue;

    static void test(int iteration, long writtenValue) {
	System.out.println("=============================");
	System.out.printf("Original long value is: 0x%X\n", writtenValue);
	dValue = Double.longBitsToDouble(writtenValue);
	long readValue = Double.doubleToRawLongBits(dValue);
	String errorMessage;
	if (writtenValue != readValue) {
	    errorMessage = String.format("ERROR (%d): Written and read values mismatch\n0x%X 0x%X",
					 iteration,
					 writtenValue,
					 readValue);
	} else {
	    errorMessage = String.format("SUCCESS (%d): Written and read values match\n0x%X 0x%X",
					 iteration,
					 writtenValue,
					 readValue);
	}
	System.out.println(errorMessage);
	System.out.println("=============================\n");
    }

    public static void main(String args[]) {
	System.out.println("### NanTest started");
	for (int i = 0; i < 100; i++) {
	    long writtenValue = 0xFFF1000000000000L;
	    test(i, writtenValue);
	}
	System.out.println("### NanTest ended");
    }
}
