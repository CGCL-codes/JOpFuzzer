import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;


public class SwingOOS4 {
    private static final class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println("Unhandled exception in thread \"" + t.getName() + "\":");
            e.printStackTrace();
            System.err.println("\nLoop counter is " + loopCounter);
            System.exit(1);
        }
    }

    public static final class UncheckedInterruptedException extends RuntimeException {
        UncheckedInterruptedException(Throwable t) {
            super(t);
        }
    }

    private static final class Poker implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(0, (int) Math.floor(100000*Math.random()));
                } catch (InterruptedException e) {
                    throw new UncheckedInterruptedException(e);
                }

                final int choice = (int) Math.floor(tabCount*Math.random());

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tabPane.setSelectedIndex(choice);
                    }
                });

                try {
                    Thread.sleep(0, (int) Math.floor(20000*Math.random()));
                } catch (InterruptedException e) {
                    throw new UncheckedInterruptedException(e);
                }
            }
        }
    }

    private static final class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent k) {
            if (k.getKeyCode () == KeyEvent.VK_ESCAPE) {
                System.exit(2);
            }
        }
    }

    private static JFrame frame;
    private static JTabbedPane tabPane;
    private static int tabCount;
    private static int loopCounter = 0;

    private static final void buildFrame() {
        tabPane = new JTabbedPane();
        tabPane.setTabPlacement(JTabbedPane.LEFT);
        tabPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        tabPane.setBackground(null);

        tabPane.addChangeListener(
            new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    int tabId = tabPane.indexOfComponent(tabPane.getSelectedComponent());
                    tabPane.setBackgroundAt(tabId, null);
                    tabPane.setTitleAt(tabId, "<html>Baz</html>");
                    tabPane.repaint();
                }
            }
        );

        tabPane.addKeyListener(new KeyHandler());

        tabPane.addTab("<html>Tab 1</html>", new JPanel());
        tabPane.addTab("<html>Tab 2</html>", new JPanel());

        tabCount = tabPane.getTabCount();

        frame = new JFrame("Test");
        frame.setContentPane(tabPane);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                buildFrame();
            }
        });

        Thread pokerThread = new Thread(new Poker(), "Poker");
        pokerThread.start();

        while (true) {
            Thread.sleep(0, (int) Math.floor(50000*Math.random()));

            final String tabTitle = String.format("<html>Foo<br>%d</html>", ++loopCounter);

            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    tabPane.setBackgroundAt(1, Color.RED);
                    tabPane.setTitleAt(1, tabTitle);
                    tabPane.repaint();
                }
            });

            if (loopCounter >= 3000) {
                System.exit(0);
            }

            Thread.sleep(0, (int) Math.floor(20000*Math.random()));
        }
    }
}

