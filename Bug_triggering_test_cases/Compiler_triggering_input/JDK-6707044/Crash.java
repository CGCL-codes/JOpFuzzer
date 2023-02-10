public class Crash {

    public static void main(String[] args) {
        new Crash().crash();
    }

    private Object alwaysNull;

    final void crash() throws Throwable {
        for (int r = 0; r < 3; r++) {
            for (int docNum = 0; docNum < 10000;) {
                if (r < 2) {
                    for(int j=0;j<3000;j++)
                        docNum++;
                } else {
                    docNum++;
                    doNothing(getNothing());
                    if (alwaysNull != null) {
                        throw new RuntimeException("BUG: checkAbort is always null: r=" + r + " of 3; docNum=" + docNum);
                    }
                }
            }
        }
    }

    Object getNothing() {
        return this;
    }

    int x;
    void doNothing(Object o) {
        x++;
    }
}