class Reduced {
  final int a = 400;
  long b;
  long c;
  void l(double d, int e, int f) {
    int q, g, h, j[][] = new int[a][a];
    long r[] = new long[a];
    for (q = 8; q < 282; ++q) {
      g = 1;
      do
        r[g + 1] += b;
      while (++g < 6);
      for (h = 1; 6 > h; ++h)
        b += 9;
    }
    c = FuzzerUtils.checkSum(j) + FuzzerUtils.checkSum(r);
  }
  void s(int i, int k) { l(1, k, k); }
  void m(String[] n) {
    int t = 9, o[] = new int[a];
    FuzzerUtils.init(o, 8);
    s(t, t);
    FuzzerUtils.out.println("vMeth1_check_sum " + c);
  }
  public static void main(String[] p) {
    try {
      Reduced u = new Reduced();
      for (int i = 0; i < 10; i++)
        u.m(p);
    } catch (Exception ex) {
    }
  }
}
