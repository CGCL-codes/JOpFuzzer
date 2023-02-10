
import javax.swing.*;

public class JTextFieldTest extends JFrame {

  public static void main( String[] args ){
    JFrame w = new JTextFieldTest( "JTextFieldTest" );
    w.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    w.setSize( 200, 50 );
    w.setVisible( true );
  }

  public JTextFieldTest( String title ){
    super( title );
    JPanel pane = (JPanel)getContentPane();
    
    JTextField tf1 = new JTextField();

    pane.add( tf1 );
  }
}

