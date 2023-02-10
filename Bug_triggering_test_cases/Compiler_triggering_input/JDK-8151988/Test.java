public final class Test { 
public static void main(final String[] args) { 
new Test().test(); 
} 

private void test() { 
for (int i = 0; i < 100000; i++) { 
long ts = System.nanoTime(); 
int sum = aaa(); 
System.out.println(i + "\t" + ((System.nanoTime() - ts) / 1000000) + "\t" + sum); 
} 
} 

private int aaa() { 
int sum = 0; 
for (int i = 1; i < 100000; i++){ 
for (int j = 1; j < 10000; j++){ 
int d = i / j; 
int m = i % j; 
sum += d; 
sum += m; 
} 
} 
return sum; 
} 
} 