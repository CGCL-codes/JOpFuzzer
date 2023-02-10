class FrequentMethod {

    int aCounter = 0;
    int bCounter = 0;
    int i = 0;
    int j = 199;

    public void aMethod() {
	i = i << 3;
	j = i >> j;
	i = i + 1;
	j = j * i;
	i--;
	aCounter++;
    }

    public void bMethod() {
	i = i << 3;
	j = i >> j;
	i = i + 1;
	j = j * i;
	i--;
	bCounter++;
    }

    public void cMethod() {
	if (i == 0) {
	    aMethod();
	} else {
	    bMethod();
	    bCounter--;
	    aCounter++;
	}
    }

    public void dMethod() {
	if (i != 0) {
	    aMethod();
	} else {
	    bMethod();
	    bCounter--;
	    aCounter++;
	}
    }

    public static void main(String args[]) {
	func1();
	func2();
    }

    public static void func1() {
	int nr_executions = 600;

	FrequentMethod fm = new FrequentMethod();
	
	for (int i = 0; i < nr_executions; i++) {
	    fm.cMethod();
	}

	System.out.println("FrequentMethod C executed " + fm.aCounter + " times");
	    
	fm.aCounter = 0;

	for (int i = 0; i < nr_executions; i++) {
	    fm.dMethod();
	}

	System.out.println("FrequentMethod D executed " + fm.aCounter + " times");
    }

    public static void func2() {
    	int nr_executions = 600;

    	FrequentMethod fm = new FrequentMethod();
	
    	for (int i = 0; i < nr_executions; i++) {
    	    fm.cMethod();
    	}
	
	System.out.println("FrequentMethod C executed " + fm.aCounter + " times");
    
    	fm.aCounter = 0;

    	for (int i = 0; i < nr_executions; i++) {
    	    fm.dMethod();
    	}

	System.out.println("FrequentMethod D executed " + fm.aCounter + " times");
    }
}
