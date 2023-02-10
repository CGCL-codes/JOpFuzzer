class a {
  int b;
  int c;
}
class MainClass {
  a[] d = {new a()};
  int e(a f, a g) {
    int h = 9;
    d[0].b = f.b - g.b * 31;
    d[0].b = 8;
    return h;
  }
  int i(a[] j) {
    a k = new a();
    a l = new a();
    for (int m = 0; m < 9;)
      e(k, l);
    return j[0].c;
  }
  public static void main(String[] args) {
    MainClass n = new MainClass();
    a[] o = {};
    n.i(o);
  }
}

