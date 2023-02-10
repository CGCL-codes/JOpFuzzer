

import javax.swing.text.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class SimpleWebBrowser {

  public static void main(String[] args) {
        
    // get the first URL
    String initialPage = "http://metalab.unc.edu/javafaq/";
    if (args.length > 0) initialPage = args[0];
    
    // set up the editor pane
    JEditorPane jep = new JEditorPane();
    jep.setEditable(false);
    jep.addHyperlinkListener(new LinkFollower(jep));
    
    try {
      jep.setPage(initialPage);
    }
    catch (IOException e) {
      System.err.println("Usage: java SimpleWebBrowser url");
      System.err.println(e);
      System.exit(-1);
    }
      
    // set up the window
    JScrollPane scrollPane = new JScrollPane(jep);
    JFrame f = new JFrame("Simple Web Browser");
    f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    f.getContentPane().add(scrollPane);
    f.setSize(512, 342);
    f.show();
    
  }

}

