import java.nio.*;


public class InlineBug {

    static int time(String name, ByteBuffer bb) {
int n = bb.capacity();
bb.limit(bb.capacity());
int a = 0;
long start = System.currentTimeMillis();
for (int i = 0; i < 4096; i++) {
bb.rewind();
for (int j = 0; j < n; j++)
a += bb.get(j);
}
System.out.println(name + " " + (System.currentTimeMillis() - start));
return a;
    }

    public static void main(String[] args) {
int n = 4096;
String key = "hd";
if (args.length == 1)
key = args[0];
int a = 0;
if (key.indexOf('h') >= 0)
a += time("heap", ByteBuffer.allocate(n));
if (key.indexOf('d') >= 0)
a += time("direct", ByteBuffer.allocateDirect(n));
System.exit(a);
    }

}