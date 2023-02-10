import java.nio.ByteOrder;

/**
 * 
 * Tests the merge of different kinds of NaN nodes in C2
 * 
 * To reproduce, run 
 *    java -server -cp ./bin/ -Xcomp -XX:-TieredCompilation NaNTest
 * @author stefan
 *
 */

public class NaNTest {
	public static final boolean isBigEndian = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
	public static final long NA_LONGBITS = isBigEndian ? 0x7ff00000000007a2L
			: 0x7ff7a20000000000L;
	public static final double DOUBLE_NA = Double.longBitsToDouble(NA_LONGBITS);
	
	

	public static void main(String[] args) {
		long[] result = new long[2];
		double[] dresult = new double[2];
		test1(result, dresult);
		if (result[0] == result[1])
            throw new InternalError(String.format("0x%x 0x%x", result[0], result[1]));
		if (Double.doubleToRawLongBits(result[0]) == Double.doubleToRawLongBits(result[1]))
            throw new InternalError(String.format("0x%x 0x%x", result[0], result[1]));
		test2(result, dresult);
		if (result[0] == result[1])
            throw new InternalError(String.format("0x%x 0x%x", result[0], result[1]));
		if (Double.doubleToRawLongBits(result[0]) == Double
				.doubleToRawLongBits(result[1]))
            throw new InternalError(String.format("0x%x 0x%x", result[0], result[1]));
		
		System.out.println("Test Successful");
	}

	static void test1(long[] result, double[] dresult) {
		double d1 = DOUBLE_NA;
		double d2 = Double.NaN;
		dresult[0] = d1;
		dresult[1] = d2;
		result[0] = Double.doubleToRawLongBits(d1);
		result[1] = Double.doubleToRawLongBits(d2);
	}

	static void test2(long[] result, double[] dresult) {
		double d1 = DOUBLE_NA;
		double d2 = Double.NaN;
		dresult[1] = d2;
		dresult[0] = d1;
		result[0] = Double.doubleToRawLongBits(d1);
		result[1] = Double.doubleToRawLongBits(d2);
	}
}
