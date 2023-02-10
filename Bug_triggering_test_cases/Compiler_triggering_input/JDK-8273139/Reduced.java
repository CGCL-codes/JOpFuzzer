public class Reduced {

  public void mainTest() {
    int i = 0;
    for (int i12 = 12; i12 < 369; i12 += 2) {
      for (long l2 = i12; l2 < 141; ++l2)
        switch ((i12 * 5) + 21) {
        case 43:
        case 81:
        case 83:
        case 109:
        case 113:
        case 121:
        case 125:
        case 150:
        case 162:
        case 191:
        case 199:
        case 216:
        case 240:
        case 246:
        case 263:
        case 267:
        case 301:
        case 318:
        case 344:
        case 322:
          break;
        case 45:
        case 244:
        case 346:
        case 313:
          break;
        case 42:
        case 101:
        case 135:
        case 173:
        case 180:
        case 235:
        case 302:
        case 311:
          break;
        default:
          break;
        }
    }
  }
  public static void main(String[] strArr) {
      Reduced _instance = new Reduced();
      _instance.mainTest();
      _instance.mainTest();
  }
}
