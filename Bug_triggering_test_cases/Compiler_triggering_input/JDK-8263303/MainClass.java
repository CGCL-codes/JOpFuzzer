class a {
  byte b;
}
class MainClass {
  a c = new a();
  byte d() {
    char e = 2;
    float[] f = new float[3];
    double g;
    for (int h = 0; h < 1; ++h) {
      g = e;
      for (int i = 0; i < 4; ++i)
        e = (char)(c.b * h);
    }
    return c.b;
  }
  a[] j(int k) {
    a[] l = {};
    d();
    return l;
  }
  public static void main(String[] args) {
    MainClass m = new MainClass();
    for (int n = 0;;)
      m.j(n);
  }
}

