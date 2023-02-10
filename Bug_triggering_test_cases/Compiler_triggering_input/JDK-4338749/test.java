import java.awt.*;
import java.awt.event.*;

class test {

    public static void main(String args[]) {
Frame f = new Frame();
f.setSize(400,400);
f.pack();
f.show();
System.out.println(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_KANA_LOCK));
    }
}