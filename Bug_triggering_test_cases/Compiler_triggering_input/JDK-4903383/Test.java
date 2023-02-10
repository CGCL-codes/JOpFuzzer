class Test {
  private int pos;
  private int charBufSize;
  private char[] charBuf = new char[8];

  // Only compile this method to see the failure.
  private void fill() throws Exception {
    int preloopPos = pos; // pos == 1
    for (int i = 0; i < charBufSize - pos; i++)
      charBuf[i] = charBuf[i + pos];
    int postloopPos = pos;
    charBufSize = charBufSize - pos;
    pos = 0;
    int n = read(charBuf, charBufSize, charBuf.length - charBufSize);
    if (postloopPos != preloopPos) {
      // Should never reach here, but we do.
      throw new Exception("Pre loop " + preloopPos +
                          " Post loop " + postloopPos);
    }
    if (n > 0) {
      charBufSize += n;
    }
  }

  // This should not be inlined into fill(). What it does is irrelevant.
  private int read(char[] buf, int x, int y) {
    for (int i = 0; i != y; ++i)
      buf[i + x] = 'Z';
    return y;
  }

  public static void main(String[] args) {
    Test test = new Test();
    try {
      test.pos = 1; // set pos != 0
      test.charBufSize = 2;
      test.fill();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}