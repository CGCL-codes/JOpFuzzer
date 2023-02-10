
package com.msi.database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.msi.carriercert.CarrierCertification;

public class NewReleaseDatabase extends JFrame{
	NetworkDatabase nd;
	
	boolean connectionFlag=false;
	
	Connection con;
	
	//UI Elements
	JPanel mainPanel=new JPanel();
	JPanel panel1=new JPanel();
	JLabel label1=new JLabel("<html><b>Phase: </b></html>");
	JLabel label2=new JLabel("<html><b>SW: </b></html>");
	JLabel label3=new JLabel("<html><b>TestArea: </b></html>");	
	JLabel label4=new JLabel("<html><b>TestCase: </b></html>");
	JLabel label5=new JLabel("<html><b>Duration: </b></html>");
	JLabel label6=new JLabel("<html><b>HW: </b></html>");
	
	JPanel panel2=new JPanel();
	JTextField txt1=new JTextField ();
	JTextField txt2=new JTextField ();
	JTextField txt3=new JTextField ();
	JTextField txt4=new JTextField ();
	JTextField txt5=new JTextField ();
	JTextField txt6=new JTextField ();
	JButton btn1 = new JButton("<html><b>Add Data</b></html>");
	
	public void makeConnection(){
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			URL location = CarrierCertification.class.getProtectionDomain().getCodeSource().getLocation();
			//System.out.println(location.getFile());
			//String connURL="jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+location.getFile()+"\\FieldTestDatabase.mdb;";
			String connURL="jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=D:\\Automation\\AFTT Reference\\FIELD TESTING DATABASE TOOL\\FieldTestDatabase.mdb;";
		
			con = DriverManager.getConnection(connURL, "","");
			connectionFlag=true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public NewReleaseDatabase(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				try{
					UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
				}
				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setVisible(true);
			}
		});
		//UI Part
		setSize(500, 500);
		setTitle("NewRelease");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2,
				size.height/2 - getHeight()/2);
		
		initUI();
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		if(!connectionFlag){
			makeConnection();	
		}
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				NetworkDatabase ex = new NetworkDatabase();
                ex.setVisible(true);
            }
        });
	}
	
	public void initUI(){
		LineBorder border = new LineBorder ( Color.GRAY, 1, true );
		TitledBorder tborder = new TitledBorder ( border, "Parameter", TitledBorder.CENTER,
	            TitledBorder.DEFAULT_POSITION, new Font ( "Arial", Font.BOLD, 12 ), Color.BLACK );
		TitledBorder tborder1 = new TitledBorder ( border, "Input", TitledBorder.CENTER,
	            TitledBorder.DEFAULT_POSITION, new Font ( "Arial", Font.BOLD, 12 ), Color.BLACK );
		//Main Panel
		mainPanel.setLayout(new BorderLayout());
		
		//Panel1
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		label1.setPreferredSize(new Dimension(200, 20));
		label2.setPreferredSize(new Dimension(200, 20));
		label3.setPreferredSize(new Dimension(200, 20));
		label4.setPreferredSize(new Dimension(200, 20));
		label5.setPreferredSize(new Dimension(200, 20));
		label6.setPreferredSize(new Dimension(200, 20));
		
		panel1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(tborder),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		panel1.add(label1);
		panel1.add(Box.createVerticalStrut(30));
		panel1.add(label2);
		panel1.add(Box.createVerticalStrut(30));
		panel1.add(label3);
		panel1.add(Box.createVerticalStrut(30));
		panel1.add(label4);
		panel1.add(Box.createVerticalStrut(30));
		panel1.add(label5);
		panel1.add(Box.createVerticalStrut(30));
		panel1.add(label6);
		//panel1.add(Box.createVerticalStrut(30));

		
		//Panel2
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		txt1.setPreferredSize(new Dimension(200, 20));
		txt1.setText("");
		txt2.setPreferredSize(new Dimension(200, 20));
		txt2.setText("");
		txt3.setPreferredSize(new Dimension(200, 20));
		txt3.setText("");
		txt4.setPreferredSize(new Dimension(200, 20));
		txt4.setText("");
		txt5.setPreferredSize(new Dimension(200, 20));
		txt5.setText("");
		txt6.setPreferredSize(new Dimension(200, 20));
		txt6.setText("");
		btn1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(tborder1),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		panel2.add(txt1);
		panel2.add(Box.createVerticalStrut(15));
		panel2.add(txt2);
		panel2.add(Box.createVerticalStrut(15));
		panel2.add(txt3);
		panel2.add(Box.createVerticalStrut(15));
		panel2.add(txt4);
		panel2.add(Box.createVerticalStrut(15));
		panel2.add(txt5);
		panel2.add(Box.createVerticalStrut(15));
		panel2.add(txt6);
		panel2.add(Box.createVerticalStrut(15));
		panel2.add(btn1);
		panel2.add(Box.createVerticalStrut(10));
		
		//Add all panels to Main Panel
		mainPanel.add(panel1, BorderLayout.WEST);
		mainPanel.add(panel2, BorderLayout.CENTER);
		
		add(mainPanel);
		
		//Event Handlers		
		btn1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(txt1.getText().length()==0 || txt2.getText().length()==0 || txt3.getText().length()==0 || txt4.getText().length()==0
						 || txt5.getText().length()==0 || txt6.getText().length()==0){
					JOptionPane.showMessageDialog(nd, "Provide Input for all Parameters");
				}
				else{
					try{
						Statement stmt = con.createStatement();
						String query="Insert into NewRelease values(";
						query+="'"+txt1.getText()+"', ";
						query+="'"+txt2.getText()+"', ";
						query+="'"+txt3.getText()+"', ";
						query+="'"+txt4.getText()+"', ";
						query+="'"+txt5.getText()+"', ";
						query+="'"+txt6.getText()+"'";
						query+=")";
						System.out.println(query);
						stmt.executeUpdate(query);
						JOptionPane.showMessageDialog(nd, "Data Added Successfully");
					}
					catch(Exception e1){
						e1.printStackTrace();
					}
				}
			}			
		});
	}
}

