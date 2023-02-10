
package com.bonuslord;

public class CrashDemo {

	public static void main(String[] args) {
		tryCrash();
	}
	
	public static void tryCrash() {
		for (int i=0; i<100; i++) {
			rudeBoi(new MyStruct(-1));
		}
		for (int i=0; i<100; i++) {
			rudeBoi(new MyStruct(null));
		}
	}
	
	private static void rudeBoi(MyStruct struct) {
		
		int min = Integer.MAX_VALUE;
		
		byte[] arr = new byte[600_000];
		
		for (int i = 0; i < 300_000; i++) {
			int bInd = i*2;
			int val = arr[bInd+1] << 8 | (arr[bInd] & 0xFF);
			
			if(struct.myBool) {
				val = 0;
			} else {
				val = 0;
			}

			if (struct.myInt == null || val != struct.myInt) {
				if (val < min) {
					min = val;
				}
			}
		}
	}
	
	private static class MyStruct {
		boolean myBool = true;
		Integer myInt;

		public MyStruct(Integer i) {
			this.myInt = i;
		}
	}
	
}

