import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class ReferenceCrasher {
	public static ReferencesKeeper root;
	public static Object dummy;
	public static Object[] keeper;
	public static ReferenceQueue reference_queue = null;//new ReferenceQueue();
	static class OurWeakReference extends WeakReference {
		OurWeakReference(Object o, ReferenceQueue q) {
			super(o, q);
		}
		long padding00;
		long padding01;
		long padding02;
		long padding03;
		long padding04;
		long padding05;
		long padding06;
		long padding07;
		long padding08;
		long padding09;

		long padding10;
		long padding11;
		long padding12;
		long padding13;
		long padding14;
		long padding15;
		long padding16;
		long padding17;
		long padding18;
		long padding19;

		long padding20;
		long padding21;
		long padding22;
		long padding23;
		long padding24;
		long padding25;
		long padding26;
		long padding27;
		long padding28;
		long padding29;

		long padding30;
		long padding31;
		long padding32;
		long padding33;
		long padding34;
		long padding35;
		long padding36;
		long padding37;
		long padding38;
		long padding39;

		long padding40;
		long padding41;
		long padding42;
		long padding43;
		long padding44;
		long padding45;
		long padding46;
		long padding47;
		long padding48;
		long padding49;

		long padding50;
		long padding51;
		long padding52;
		long padding53;
		long padding54;
		long padding55;
		long padding56;
		long padding57;
		long padding58;
		long padding59;

		long padding60;
		long padding61;
		long padding62;
		long padding63;
		long padding64;
		long padding65;
		long padding66;
		long padding67;
		long padding68;
		long padding69;

		long padding70;
		long padding71;
		long padding72;
		long padding73;
		long padding74;
		long padding75;
		long padding76;
		long padding77;
		long padding78;
		long padding79;

		long padding80;
		long padding81;
		long padding82;
		long padding83;
		long padding84;
		long padding85;
		long padding86;
		long padding87;
		long padding88;
		long padding89;

		long padding90;
		long padding91;
		long padding92;
		long padding93;
		long padding94;
		long padding95;
		long padding96;
		long padding97;
		long padding98;
		long padding99;

		long padding100;
		long padding101;
		long padding102;
		long padding103;
		long padding104;
		long padding105;
		long padding106;
		long padding107;
		long padding108;
		long padding109;
		
		long padding110;
		long padding111;
		long padding112;
		long padding113;
		long padding114;
		long padding115;
		long padding116;
		long padding117;
		long padding118;
		long padding119;
		
		long padding120;
		long padding121;
		long padding122;
		long padding123;
		long padding124;
		long padding125;
		long padding126;
		long padding127;
		long padding128;
		long padding129;
		
		long padding130;
		long padding131;
		long padding132;
		long padding133;
		long padding134;
		long padding135;
		long padding136;
		long padding137;
		long padding138;
		long padding139;
	} 
	
	static class OurWeakReference1 extends OurWeakReference {
		OurWeakReference1(Object o, ReferenceQueue q) {
			super(o, q);
		}
	}
	static class OurWeakReference2 extends OurWeakReference {
		OurWeakReference2(Object o, ReferenceQueue q) {
			super(o, q);
		}
	}
	static class OurWeakReference3 extends OurWeakReference {
		OurWeakReference3(Object o, ReferenceQueue q) {
			super(o, q);
		}
	}
	
	static class Referent {
		int number;
		Referent(int number) {
			this.number = number;
		}
	}
    static class Referent1 extends Referent {
		Referent1(int number) {
			super(number);
		}
	}
    static class Referent2 extends Referent {
		Referent2(int number) {
			super(number);
		}
	}
    static class Referent3 extends Referent {
		Referent3(int number) {
			super(number);
		}
	}
	
    static class ReferenceQueue1 extends ReferenceQueue {
		long padding00;
		long padding01;
		long padding02;
		long padding03;
		long padding04;
		long padding05;
		long padding06;
		long padding07;
		long padding08;
		long padding09;

		long padding10;
		long padding11;
		long padding12;
		long padding13;
		long padding14;
		long padding15;
		long padding16;
		long padding17;
		long padding18;
		long padding19;

		long padding20;
		long padding21;
		long padding22;
		long padding23;
		long padding24;
		long padding25;
		long padding26;
		long padding27;
		long padding28;
		long padding29;

		long padding30;
		long padding31;
		long padding32;
		long padding33;
		long padding34;
		long padding35;
		long padding36;
		long padding37;
		long padding38;
		long padding39;

		long padding40;
		long padding41;
		long padding42;
		long padding43;
		long padding44;
		long padding45;
		long padding46;
		long padding47;
		long padding48;
		long padding49;

		long padding50;
		long padding51;
		long padding52;
		long padding53;
		long padding54;
		long padding55;
		long padding56;
		long padding57;
		long padding58;
		long padding59;

		long padding60;
		long padding61;
		long padding62;
		long padding63;
		long padding64;
		long padding65;
		long padding66;
		long padding67;
		long padding68;
		long padding69;

		long padding70;
		long padding71;
		long padding72;
		long padding73;
		long padding74;
		long padding75;
		long padding76;
		long padding77;
		long padding78;
		long padding79;

		long padding80;
		long padding81;
		long padding82;
		long padding83;
		long padding84;
		long padding85;
		long padding86;
		long padding87;
		long padding88;
		long padding89;

		long padding90;
		long padding91;
		long padding92;
		long padding93;
		long padding94;
		long padding95;
		long padding96;
		long padding97;
		long padding98;
		long padding99;

		long padding100;
		long padding101;
		long padding102;
		long padding103;
		long padding104;
		long padding105;
		long padding106;
		long padding107;
		long padding108;
		long padding109;
		
		long padding110;
		long padding111;
		long padding112;
		long padding113;
		long padding114;
		long padding115;
		long padding116;
		long padding117;
		long padding118;
		long padding119;
		
		long padding120;
		long padding121;
		long padding122;
		long padding123;
		long padding124;
		long padding125;
		long padding126;
		long padding127;
		long padding128;
		long padding129;
		
		long padding130;
		long padding131;
		long padding132;
		long padding133;
		long padding134;
		long padding135;
		long padding136;
		long padding137;
		long padding138;
		long padding139;
    }
    static class ReferenceQueue2 extends ReferenceQueue {}
    static class ReferenceQueue3 extends ReferenceQueue {}
    
	static class ReferenceKeeper1 {
		OurWeakReference1 reference;
		Object referent;
		ReferenceKeeper1(Object referent) {
			this.referent = referent;
			reference = new OurWeakReference1(this.referent, new ReferenceQueue1());
		}
	}
	static class ReferenceKeeper2 {
		OurWeakReference2 reference;
		Object referent;
		ReferenceKeeper2(Object referent) {
			this.referent = referent;
			reference = new OurWeakReference2(this.referent, new ReferenceQueue2());
		}
	}
	static class ReferenceKeeper3 {
		OurWeakReference3 reference;
		Object referent;
		ReferenceKeeper3(Object referent) {
			this.referent = referent;
			reference = new OurWeakReference3(this.referent, new ReferenceQueue3());
		}
	}
	
	static class ReferencesKeeper {
		ReferenceKeeper3 r3;
		ReferenceKeeper2 r2;
		ReferenceKeeper1 r1;
	}
	
	public static void main(String [] args) throws Exception {
		int numKeepers = 1000000;
		if (args.length > 0) {
			numKeepers = Integer.parseInt(args[0]);
		}
		
		root = new ReferencesKeeper();
		
		root.r1 = new ReferenceKeeper1(new Referent1(0));
		//root.r2 = new ReferenceKeeper2(new Referent2(0));
		//root.r3 = new ReferenceKeeper3(new Referent3(0));

		for (int i = 0; i < numKeepers; i++) {
			dummy = new byte[1000];
		}

		keeper = new Object[1000];
		for (int i = 0; i < keeper.length; i++) {
			keeper[i] = new byte[1000];
		}
		
		System.err.println("Setup done");
		
		for (int i = 0; i < numKeepers; i++) {
			dummy = new byte[1000];
		}
		
		System.err.println("Alloc done");

		System.gc();
		Thread.sleep(100);
		System.gc();
		Thread.sleep(100);
		
		for (int i = 0; i < 1000; i++) {
			dummy = new byte[1000];
		}
		dummy = new byte[10000000];
		
		root.r2 = new ReferenceKeeper2(new Referent2(0));
		root.r3 = new ReferenceKeeper3(new Referent3(0));

		root.r1.referent = null;
		root.r3.referent = null;

		for (int i = 0; i < numKeepers; i++) {
			dummy = new byte[1000];
		}
/*
		System.err.println((OurWeakReference)reference_queue.remove());
		System.err.println((OurWeakReference)reference_queue.remove());
		System.err.println((OurWeakReference)reference_queue.remove());
		*/
		
		Thread.sleep(5000);
	}
}