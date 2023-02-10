
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import sun.misc.Unsafe;

public class DirectMemoryCrash {

	private void reserveMemory(int bytes) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException,
			NoSuchFieldException {
		Class<?> bitsClass = Class.forName("java.nio.Bits");
		Method reserveMemoryMethod = bitsClass.getDeclaredMethod("reserveMemory", long.class, int.class);
		reserveMemoryMethod.setAccessible(true);
		reserveMemoryMethod.invoke(null, (long) bytes, bytes);
	}

	private void unreserveMemory(int bytes) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException,
			NoSuchFieldException {
		Class<?> bitsClass = Class.forName("java.nio.Bits");
		Method unreserveMemoryMethod = bitsClass.getDeclaredMethod("unreserveMemory", long.class, int.class);
		unreserveMemoryMethod.setAccessible(true);
		unreserveMemoryMethod.invoke(null, (long) bytes, bytes);
	}


	private sun.misc.Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
		java.lang.reflect.Field f = sun.misc
				.Unsafe.class.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		sun.misc.Unsafe unsafe = (Unsafe) f.get(null);
		return unsafe;
	}

	private long[] reserveAndAllocateMemory(int numberBits) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		long[] result = new long[2];
		int numberBytes = ((numberBits >>> 6) << 3);
		// make sure we allocate enough to work with longs
		if (numberBits > (numberBytes << 3)) {
			numberBytes += 8;
		}
		sun.misc.Unsafe unsafe = getUnsafe();
		reserveMemory(numberBytes);
		long address = unsafe.allocateMemory(numberBytes);
		unsafe.setMemory(address, numberBytes, (byte) 0);
		result[0] = address;
		result[1] = numberBytes;
		return result;
	}

	private void alternateBitsUsingBytes(int numberBits) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException,
			NoSuchFieldException {

		long[] addressAndLength = reserveAndAllocateMemory(numberBits);

		sun.misc.Unsafe unsafe = getUnsafe();

		for (int i = 0; i < numberBits; i++) {
			int oddOrEven = i & 1;
			long byteAddress = addressAndLength[0] + (i >>> 3);
			byte oldValue = unsafe.getByte(byteAddress);
			System.out.println("iteration " + i + " reading " + oldValue + " at address " + byteAddress);
			byte newValue = (byte) (oldValue | (oddOrEven << (i & 7)));
			System.out.println("iteration " + i + " putting " + newValue + " at address " + byteAddress);
			unsafe.putByte(byteAddress, newValue);
		}

		unsafe.freeMemory(addressAndLength[0]);
		unreserveMemory((int) addressAndLength[1]);
	}

	private void alternateBitsUsingLongs(int numberBits) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException,
			NoSuchFieldException {

		long[] addressAndLength = reserveAndAllocateMemory(numberBits);

		sun.misc.Unsafe unsafe = getUnsafe();
		for (int i = 0; i < numberBits; i++) {
			long oddOrEven = i & 1;
			long longAddress = addressAndLength[0] + ((i >>> 6) << 3);
			long oldValue = unsafe.getLong(longAddress);
			System.out.println("iteration " + i + " reading " + oldValue + " at address " + longAddress);
			long newValue = oldValue | (long) ((oddOrEven << (i & 63)));
			System.out.println("iteration " + i + " putting " + newValue + " at address " + longAddress);
			unsafe.putLong(longAddress, newValue);
		}

		unsafe.freeMemory(addressAndLength[0]);
		unreserveMemory((int) addressAndLength[1]);
	}

	public static void main(String[] args) {
		try {
			int numberBits = 100_000;
			DirectMemoryCrash test = new DirectMemoryCrash();
			if (args.length > 0 && "useLongs".equals(args[0])) {
				test.alternateBitsUsingLongs(numberBits);
				System.out.println("Finished job using longs");
			}
			else {
				test.alternateBitsUsingBytes(numberBits);
				System.out.println("Finished job using bytes");
			}
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException ex) {
			System.out.println("There was an exception : " + ex.getClass().getSimpleName());
		}
	}

}

