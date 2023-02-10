import java.io.*;

class Test {
public static final int CHUNK_SHIFT = 14;
public static final int CHUNK_SIZE = (1 << CHUNK_SHIFT);
public static final int CHUNK_MASK = CHUNK_SIZE - 1;

public String toString(int offset, int length) {
synchronized (fgTempBufferLock) {
int outOffset = 0;
Test dataChunk = this;
int endOffset = offset + length;
int index = offset & CHUNK_MASK;
byte[] data = fData;
boolean skiplf = false;
while (offset < endOffset) {
int b0 = data[index++] & 0xff;
offset++;
if (index == CHUNK_SIZE && offset < endOffset) {
System.out.println("index == CHUNK_SIZE && offset < endOffset");
dataChunk = dataChunk.fNextChunk;
data = dataChunk.fData;
index = 0;
}
if (b0 < 0x80) {
if (skiplf) {
skiplf = false;
if (b0 == 0x0A)
continue;
}
if (b0 == 0x0D) {
b0 = 0x0A;
skiplf = true;
}
try {
fgTempBuffer[outOffset] = (char)b0;
outOffset++;
} catch (NullPointerException ex) {
fgTempBuffer = new char[CHUNK_SIZE];
fgTempBuffer[outOffset++] = (char)b0;
} catch (ArrayIndexOutOfBoundsException ex) {
char[] newBuffer = new char[outOffset * 2];
System.arraycopy(fgTempBuffer, 0, newBuffer, 0, outOffset);
fgTempBuffer = newBuffer;
fgTempBuffer[outOffset++] = (char)b0;
}
continue;
}
int b1 = data[index++] & 0xff;
offset++;
if (index == CHUNK_SIZE && offset < endOffset) {
dataChunk = dataChunk.fNextChunk;
data = dataChunk.fData;
index = 0;
}
if ((0xe0 & b0) == 0xc0) {
int ch = ((0x1f & b0)<<6) + (0x3f & b1);
try {
fgTempBuffer[outOffset] = (char)ch;
outOffset++;
} catch (NullPointerException ex) {
fgTempBuffer = new char[CHUNK_SIZE];
fgTempBuffer[outOffset++] = (char)ch;
} catch (ArrayIndexOutOfBoundsException ex) {
char[] newBuffer = new char[outOffset * 2];
System.arraycopy(fgTempBuffer, 0, newBuffer, 0, outOffset);
fgTempBuffer = newBuffer;
fgTempBuffer[outOffset++] = (char)ch;
}
continue;
}
int b2 = data[index++] & 0xff;
offset++;
if (index == CHUNK_SIZE && offset < endOffset) {
dataChunk = dataChunk.fNextChunk;
data = dataChunk.fData;
index = 0;
}
if ((0xf0 & b0) == 0xe0) {
int ch = ((0x0f & b0)<<12) + ((0x3f & b1)<<6) + (0x3f & b2);
try {
fgTempBuffer[outOffset] = (char)ch;
outOffset++;
} catch (NullPointerException ex) {
fgTempBuffer = new char[CHUNK_SIZE];
fgTempBuffer[outOffset++] = (char)ch;
} catch (ArrayIndexOutOfBoundsException ex) {
char[] newBuffer = new char[outOffset * 2];
System.arraycopy(fgTempBuffer, 0, newBuffer, 0, outOffset);
fgTempBuffer = newBuffer;
fgTempBuffer[outOffset++] = (char)ch;
}
continue;
}
int b3 = data[index++] & 0xff;
offset++;
if (index == CHUNK_SIZE && offset < endOffset) {
dataChunk = dataChunk.fNextChunk;
data = dataChunk.fData;
index = 0;
}
int ch = ((0x0f & b0)<<18) + ((0x3f & b1)<<12) + ((0x3f & b2)<<6) + (0x3f & b3);
if (ch < 0x10000) {
try {
fgTempBuffer[outOffset] = (char)ch;
outOffset++;
} catch (NullPointerException ex) {
fgTempBuffer = new char[CHUNK_SIZE];
fgTempBuffer[outOffset++] = (char)ch;
} catch (ArrayIndexOutOfBoundsException ex) {
char[] newBuffer = new char[outOffset * 2];
System.arraycopy(fgTempBuffer, 0, newBuffer, 0, outOffset);
fgTempBuffer = newBuffer;
fgTempBuffer[outOffset++] = (char)ch;
}
} else {
char ch1 = (char)(((ch-0x00010000)>>10)+0xd800);
char ch2 = (char)(((ch-0x00010000)&0x3ff)+0xdc00);
try {
fgTempBuffer[outOffset] = (char)ch1;
outOffset++;
} catch (NullPointerException ex) {
fgTempBuffer = new char[CHUNK_SIZE];
fgTempBuffer[outOffset++] = (char)ch1;
} catch (ArrayIndexOutOfBoundsException ex) {
char[] newBuffer = new char[outOffset * 2];
System.arraycopy(fgTempBuffer, 0, newBuffer, 0, outOffset);
fgTempBuffer = newBuffer;
fgTempBuffer[outOffset++] = (char)ch1;
}
try {
fgTempBuffer[outOffset] = (char)ch2;
outOffset++;
} catch (NullPointerException ex) {
fgTempBuffer = new char[CHUNK_SIZE];
fgTempBuffer[outOffset++] = (char)ch2;
} catch (ArrayIndexOutOfBoundsException ex) {
char[] newBuffer = new char[outOffset * 2];
System.arraycopy(fgTempBuffer, 0, newBuffer, 0, outOffset);
fgTempBuffer = newBuffer;
fgTempBuffer[outOffset++] = (char)ch2;
}
}
}
return new String(fgTempBuffer, 0, outOffset);
}
}

public static void main(String[] args) {
Test myInstance = new Test();

myInstance.fData = new byte[20000];
for (int i=0; i<20000; i+=5) {
myInstance.fData[i+0] = (byte)0x30;
myInstance.fData[i+1] = (byte)0x31;
myInstance.fData[i+2] = (byte)0x32;
myInstance.fData[i+3] = (byte)0x33;
myInstance.fData[i+4] = (byte)0x34;
}
for (int i=0; i<20000; i+=5) {
String theString = myInstance.toString(i, 5);
if (theString.equals("hello")) {
System.out.println("FECK!");
}
}
}

private byte[] fData = null;
private Test fNextChunk = this;
private static char[] fgTempBuffer = null;
private static Object fgTempBufferLock = new Object();
}
