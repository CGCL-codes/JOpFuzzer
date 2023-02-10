import java.nio.*;


public class BBSpeed {

    static final int SIZE = 4096;
    static final int TRIALS = 4096;

    static int sum(ByteBuffer bb) {
int n = bb.capacity();
int a = 0;
bb.rewind();
for (int j = 0; j < n; j++)
a += bb.get(j);
return a;
    }

    static int sumReverse(ByteBuffer bb) {
int n = bb.capacity();
int a = 0;
bb.rewind();
for (int j = n - 1; j >= 0; j--)
a += bb.get(j);
return a;
    }

    static int time(String name, ByteBuffer bb) {
int n = bb.capacity();
bb.limit(bb.capacity());
int a = 0;

for (int i = 0; i < TRIALS; i++) sum(bb);
long start = System.currentTimeMillis();
for (int i = 0; i < TRIALS; i++) sum(bb);
System.out.println(name + " " + (System.currentTimeMillis() - start));

for (int i = 0; i < TRIALS; i++) sumReverse(bb);
start = System.currentTimeMillis();
for (int i = 0; i < TRIALS; i++) sumReverse(bb);
System.out.println(name + " rev " + (System.currentTimeMillis() - start));

return a;
    }

    static int sum(byte[] ba) {
int n = ba.length;
int a = 0;
for (int j = 0; j < n; j++)
a += ba[j];
return a;
    }

    static int sumReverse(byte[] ba) {
int n = ba.length;
int a = 0;
for (int j = n - 1; j >= 0; j--)
a += ba[j];
return a;
    }

    static int timeArray() {
byte[] ba = new byte[SIZE];
int a = 0;

for (int i = 0; i < TRIALS; i++) a += sum(ba);
long start = System.currentTimeMillis();
for (int i = 0; i < TRIALS; i++) a += sum(ba);
System.out.println("array " + (System.currentTimeMillis() - start));

for (int i = 0; i < TRIALS; i++) a += sumReverse(ba);
start = System.currentTimeMillis();
for (int i = 0; i < TRIALS; i++) a += sumReverse(ba);
System.out.println("array rev " + (System.currentTimeMillis() - start));

return a;
    }

    public static void main(String[] args) {
String key = "hda";
if (args.length == 1)
key = args[0];
int a = 0;
if (key.indexOf('a') >= 0)
a += timeArray();
if (key.indexOf('h') >= 0)
a += time("heap", ByteBuffer.allocate(SIZE));
if (key.indexOf('d') >= 0)
a += time("direct", ByteBuffer.allocateDirect(SIZE));
System.exit(a);
    }

}