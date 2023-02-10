class b
{
  public static void main(String[] arg) throws Exception
  {
    Node node = new Node();
    ConvStringExpr con = new ConvStringExpr(node);
    for (int j = 0 ; j < 1000000 ; ++j) {
      invoke(node, con);
    }
  }
  static void invoke(Node node, ConvStringExpr expr) {
    String str = expr.eval(node);
  }
}

class ConvStringExpr {
  Node n;
  ConvStringExpr(Node n) {
    this.n = n;
  }
  String eval(Node node) {
    NodeItera i = n.xxx(node);
    return Converter.toString(i);
  }
}

class Node {
  public NodeItera xxx(Node node) {
    return new SingleNodeItera(node);
  }
}

interface NodeItera {
  Node next();
}

class SingleNodeItera implements NodeItera {
  Node n;
  SingleNodeItera (Node node) {
    n = node;
  }
  public Node next() {
    Node tem = n;
    n = null;
    return tem;
  }
}

class Converter {
  static String toString(NodeItera i) {
    Node n = i.next();
    if (n == null) {
      System.out.println("NG");
      System.exit(1);
    }
    return "";
  }
}