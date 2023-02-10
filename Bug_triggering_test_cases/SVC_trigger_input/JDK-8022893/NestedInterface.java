import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * This class tries to produce a bug on the profiler that involves showing 
 * impossible stacktraces.
 * 
 * <p>It creates two different kind of lists, {@link List1} and {@link List2}, that
 * are each used and called in independent contexts and methods, such as any {@link List1} 
 * method should never be seen in a stacktrace containing any methods with a 2 in it's name
 * and vice versa.
 * 
 * <p> For example, the following stacktrace is impossible: 
 * <pre>
 * java.lang.Integer.valueOf(Integer.java -- method id 0x7f552c0cf9e0:642)
 * com.qfs.profiler.test.NestedInterface$List1.writeInt(NestedInterface.java -- method id 0x7f552c154c70:400)
 * com.qfs.profiler.test.NestedInterface.transferSome(NestedInterface.java -- method id 0x7f552c167010:-1)
 * com.qfs.profiler.test.NestedInterface.transferOne(NestedInterface.java -- method id 0x7f552c166ff8:240)
 * com.qfs.profiler.test.NestedInterface.transferTwo(NestedInterface.java -- method id 0x7f552c166ff0:230)
 * com.qfs.profiler.test.NestedInterface.transferList(NestedInterface.java -- method id 0x7f552c166fe8:211)
 * com.qfs.profiler.test.NestedInterface.computeList2(NestedInterface.java -- method id 0x7f552c166fe0:204)
 * com.qfs.profiler.test.NestedInterface.runList2(NestedInterface.java -- method id 0x7f552c166fc0:168)
 * com.qfs.profiler.test.NestedInterface.doDummyWork(NestedInterface.java -- method id 0x7f552c166fa0:40)
 * com.qfs.profiler.test.NestedInterface.main(NestedInterface.java -- method id 0x7f552c166f98:24)
 * </pre>
 */
public class NestedInterface {

	public static void main(String[] args) {
		doDummyWork();
	} 
	
	/**
	 * Does some work to test the profiler.
	 */
	public static void doDummyWork() {
		final int nbEls = 1<<25;
		final int nbRuns = 1<<0;
		//Lets do a bunch of computation, so a bug 
		//is more likely to appear.
		final int nbComputations = 1<<6;
		
		runList1(nbEls, nbRuns, nbComputations);
		
		System.out.println("");
		System.out.println("=============================");
		System.out.println("Start second computing phase.");
		System.out.println("=============================");
		System.out.println("");
		
		runList2(nbEls, nbRuns, nbComputations);
		
	}
	
	/**
	 * Do computations on the two lists in a mixed way.
	 * 
	 * @param nbEls the number of elements to store in a custom list
	 * @param nbRuns the number of time to run the method
	 * @param nbComputations the number of time to do core computation (transfering the data
	 *        from an {@link ArrayList} to both {@link List1} and {@link List2}).
	 */
	public static void runList1AndList2(final int nbEls, final int nbRuns, final int nbComputations) {
		for(int run=0; run<nbRuns; run++) {
			System.out.println("Run "+(run+1)+"/"+nbRuns );

			//Create two different types of lists
			final List1 list1 = new List1();
			final List2 list2 = new List2();

			final ArrayList<Integer> sourceList = new ArrayList<>();

			for (int i=0; i<nbEls; ++i) {
				list1.add(0);
				list2.add(0);
				sourceList.add(i+1);
			}

			
			//Transfer data from the array list to list1
			for (int i=0; i<nbComputations; ++i) {
				System.out.println("\tComputing list 1: "+(i+1)+"/"+nbComputations );
				computeList1(sourceList, list1);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("");
			System.out.println("=============================");
			System.out.println("Start second computing phase.");
			System.out.println("=============================");
			System.out.println("");
			
			//Transfer data from the array list to list2
			for (int i=0; i<nbComputations; ++i) {
				System.out.println("\tComputing list 2: "+(i+1)+"/"+nbComputations );
				computeList2(sourceList, list2);
			}
		}
	}
	
	/**
	 * Do computationss on the {@link List1} list.
	 * 
	 * @param nbEls the numbers of elements to store in the list
	 * @param nbRuns the numbers of time to run the method.
	 * @param nbComputations the number of time to do the core comutation (transfering from
	 *        an {@link ArrayList} to the {@link List1 list1}). 
	 */
	
	public static void runList1(final int nbEls, final int nbRuns, final int nbComputations) {
		for(int run=0; run<nbRuns; run++) {
			System.out.println("Run "+(run+1)+"/"+nbRuns );

			final List1 list1 = new List1();

			final ArrayList<Integer> sourceList = new ArrayList<>();

			for (int i=0; i<nbEls; ++i) {
				list1.add(0);
				sourceList.add(i+1);
			}

			for (int i=0; i<nbComputations; ++i) {
				System.out.println("\tComputing list 1: "+(i+1)+"/"+nbComputations );
				computeList1(sourceList, list1);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Do computationss on the {@link List2} list.
	 * 
	 * @param nbEls the numbers of elements to store in the list
	 * @param nbRuns the numbers of time to run the method.
	 * @param nbComputations the number of time to do the core comutation (transfering from
	 *        an {@link ArrayList} to the {@link List2 list2}). 
	 */
	
	public static void runList2(final int nbEls, final int nbRuns, final int nbComputations) {
		for(int run=0; run<nbRuns; run++) {
			System.out.println("Run "+(run+1)+"/"+nbRuns );

			final List2 list2 = new List2();

			final ArrayList<Integer> sourceList = new ArrayList<>();

			for (int i=0; i<nbEls; ++i) {
				list2.add(0);
				sourceList.add(i+1);
			}

			for (int i=0; i<nbComputations; ++i) {
				System.out.println("\tComputing list 1: "+(i+1)+"/"+nbComputations );
				computeList2(sourceList, list2);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Do some computing. (Transfer the source to dest)
	 * 
	 * @param source the source list
	 * @param dest the destination list
	 */

	public static void computeList1(ArrayList<Integer> source, IWritableNumberList<? extends Number> dest) {
		transferList(source, dest);
	}

	/**
	 * Do some computing. (Transfer the source to dest)
	 * 
	 * @param source the source list
	 * @param dest the destination list
	 */
	public static void computeList2(ArrayList<Integer> source, IWritableNumberList<? extends Number> dest) {
		transferList(source, dest);
	}

	
	/**
	 * Transfer the data of source list to dest list by batches of two.
	 * No checks are made on the sizes of the lists.
	 * 
	 * @param source the source list
	 * @param dest the destination list
	 */
	public static void transferList(ArrayList<Integer> source, IWritableNumberList<? extends Number> dest) {
		int i=0;
		for (; i<source.size()-1; i+=2) {
			transferTwo(source, dest, i);
		}
		if(i==source.size()-1) {
			transferOne(source, dest, i);
		}
	}

	/**
	 * Transfer the data of source list to dest list by batches of two, from
	 * biggest index to smallest.
	 * No checks are made on the sizes of the lists.
	 * 
	 * <p> This method is here to provide an alternative to the {@link #transferList(ArrayList, IWritableNumberList)}
	 * method and allow having different stacktraces.
	 * 
	 * @param source the source list
	 * @param dest the destination list
	 */
	public static void reverseTransferList(ArrayList<Integer> source, IWritableNumberList<? extends Number> dest) {
		int i=source.size()-1;
		for (; i>0 ; i-=2) {
			reverseTransferTwo(source, dest, i);
		}
		if(i==0) {
			transferOne(source, dest, i);
		}

	}

	/**
	 * Transfer two records in ascending order, starting from idx.
	 * @param source
	 * @param dest
	 * @param idx the index of the records to transfer
	 */
	public static void transferTwo(ArrayList<Integer> source, IWritableNumberList<?> dest, int idx) {
		transferOne(source, dest, idx);
		transferOne(source, dest, idx+1);
	}

	/**
	 * Transfer two records in descending order, starting from idx.
	 * 
	 * @param source
	 * @param dest
	 * @param idx the index of the records to transfer
	 */
	public static void reverseTransferTwo(ArrayList<Integer> source, IWritableNumberList<?> dest, int idx) {
		transferOne(source, dest, idx);
		transferOne(source, dest, idx-1);
	}

	/**
	 * Transfer one record from the <tt>source</tt> list to the <tt>dest</tt> list.
	 * 
	 * @param source
	 * @param dest
	 * @param idx the index of the records to transfer
	 */
	public static void transferOne(ArrayList<Integer> source, IWritableList dest, int idx) {
		//To add some depth to the stack trace, we call yet another function.
		transferSome(source,dest, idx, idx+1);
	}

	/**
	 * Transfer a batch of records from <tt>source</tt> list to <tt>dest</tt> list.
	 * @param source
	 * @param dest
	 * @param startIdx the index, inclusive, at which to start transferring.
	 * @param endIdx the index, exclusive, at which to end transferring.
	 */
	public static void transferSome(ArrayList<Integer> source, IWritableList dest, int startIdx, int endIdx) {
		for (int i=startIdx; i<endIdx; ++i) {
			dest.writeInt(i, source.get(i));
		}
	}

	/**
	 * A simple read only list interface.
	 */
	public interface IList {
		Object read(int idx);

		int readInt(int idx);

		long readLong(int idx);
	}

	/**
	 * A simple read-write list interface.
	 */
	public interface IWritableList extends IList {
		void write(int idx, Object value);	

		void add(Object value);

		void writeInt(int idx, int value);	

		void addInt(int value);

		void writeLong(int idx, long value);	

		void addLong(long value);
	}

	/**
	 * An interface for a read only list able to store some {@link Number numbers}.
	 * 
	 * @param <T>
	 */
	protected static interface INumberList<T extends Number> extends IList, Iterable<T>{
		int size();
	}

	/**
	 * An interface for a read-write list able to store some {@link Number numbers}.
	 * 
	 * @param <T>
	 */
	public static interface IWritableNumberList<T extends Number> extends INumberList<T>, IWritableList {
		/**
		 * Make sure the list can hold <tt>capacity</tt> items.
		 * @param capacity
		 */
		void ensureCapacity(int capacity);

		/**
		 * Empty the list of any content.
		 */
		void clear();
	}


	/**
	 * Abstract implementation of an {@link INumberList}.
	 */
	protected abstract static class ANumberList<T extends Number> implements INumberList<T> {

		final List<T> underlying;

		long counter = 0;

		public ANumberList(List<T> underlying) {
			this.underlying = underlying;
		}

		public int size() {
			return underlying.size();
		}

	}

	/**
	 * First implementation of an {@link ANumberList}, for testing purposes.
	 */
	protected static class List1 extends ANumberList<Integer> implements IWritableNumberList<Integer> {

		public List1() {
			super(new Vector<Integer>());
		}

		@Override
		public Object read(int idx) {
			return underlying.get(idx);
		}

		@Override
		public int readInt(int idx) {
			return underlying.get(idx);
		}

		@Override
		public long readLong(int idx) {
			return underlying.get(idx);
		}

		@Override
		public Iterator<Integer> iterator() {
			return underlying.iterator();
		}

		@Override
		public void write(int idx, Object value) {
			underlying.set(idx, (Integer)value);
		}

		@Override
		public void add(Object value) {
			underlying.add((Integer)value);
		}

		@Override
		public void writeInt(int idx, int value) {
			underlying.set(idx, value);
		}

		@Override
		public void addInt(int value) {
			underlying.add(value);
		}

		@Override
		public void writeLong(int idx, long value) {
			throw new UnsupportedOperationException("Cannot write a long in an integer list.");
		}

		@Override
		public void addLong(long value) {
			throw new UnsupportedOperationException("Cannot write a long in an integer list.");
		}

		@Override
		public void ensureCapacity(int capacity) {
			((Vector<Integer>)underlying).ensureCapacity(capacity);
		}

		@Override
		public void clear() {
			underlying.clear();
		}

	}


	/**
	 * A dummy interface, to complexify the structure.
	 */
	protected static interface IDummyTable2 extends IList{
		int dummyTable2();
	}

	/**
	 * A dummy interface, to complexify the structure.
	 */
	protected static interface IList2 extends IDummyTable2, IWritableList {
		int list2();
	}

	/**
	 * Second implementation of an {@link ANumberList}, for testing purposes.
	 */
	protected static class List2 extends ANumberList<Integer> implements IWritableNumberList<Integer>, IList2{

		public List2() {
			super(new ArrayList<Integer>());
		}

		@Override
		public Object read(int idx) {
			return underlying.get(idx);
		}

		@Override
		public int readInt(int idx) {
			return underlying.get(idx);
		}

		@Override
		public long readLong(int idx) {
			return underlying.get(idx);
		}

		@Override
		public Iterator<Integer> iterator() {
			return underlying.iterator();
		}

		@Override
		public void write(int idx, Object value) {
			underlying.set(idx, (Integer)value);
		}

		@Override
		public void add(Object value) {
			underlying.add((Integer)value);
		}

		@Override
		public void writeInt(int idx, int value) {
			underlying.set(idx, value);
		}

		@Override
		public void addInt(int value) {
			underlying.add(value);
		}

		@Override
		public void writeLong(int idx, long value) {
			throw new UnsupportedOperationException("Cannot write a long in an integer list.");
		}

		@Override
		public void addLong(long value) {
			throw new UnsupportedOperationException("Cannot write a long in an integer list.");
		}

		@Override
		public void ensureCapacity(int capacity) {
			((ArrayList<Integer>)underlying).ensureCapacity(capacity);
		}

		@Override
		public void clear() {
			underlying.clear();
		}

		@Override
		public int dummyTable2() {
			return 0;
		}

		@Override
		public int list2() {
			return 0;
		}

	}

}
