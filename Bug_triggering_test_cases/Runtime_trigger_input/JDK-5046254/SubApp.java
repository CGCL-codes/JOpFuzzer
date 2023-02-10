
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SubApp extends JFrame {
    public SubApp() {
        setTitle("SubApp");
        JButton button = new JButton("Click me");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    throw new Exception("Exception occurs!");
                } catch (Exception exp) {
                    System.out.println("In catach block, before invoke printStackTrace()");
                    exp.printStackTrace();
// the system will also hang after execute System.err.println() for 9 times
// if replace exp.prinStackTrace() with below statements.
//						for (int i = 0; i < 20; i++) {
//							System.err.println("i = " + i);
//							System.out.println("i = " + i);
//						}
                    System.out.println("Done.");
                }
            }
        });
        getContentPane().add(button, BorderLayout.CENTER);
        setSize(200,100);
        setVisible(true);
    }

    public static void main(String args[]) {
        new SubApp();
    }
}
