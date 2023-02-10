

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainApp extends JFrame
{
	public MainApp() {
		setTitle("MainApp");
		JButton button = new JButton("Launch SubApp");
		button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Runtime.getRuntime().exec("launchSubApp.bat");
					} catch (Exception exp) {
						exp.printStackTrace();
					}
				}
			});
		getContentPane().add(button, BorderLayout.CENTER);
		setSize(200, 50);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainApp();
	}
}


