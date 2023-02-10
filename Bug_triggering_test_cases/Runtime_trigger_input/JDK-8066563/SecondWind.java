
package prem.sadan;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
//import org.w3c.dom.Document;
import java.sql.SQLException;
import javax.swing.border.*;
/*-------------- JTable Package ----------*/
import javax.swing.table.*;

/*-----------Numbers only Packages ------------------*/
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/* ---------------- Report Card ------------------------ */
import net.proteanit.sql.DbUtils;

/* ---------------- Print Media -------------------*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.String;
import java.awt.print.*;


import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



/*------- Package files are Over --------*/

public class SecondWind extends JFrame implements ActionListener, Printable {
    /*-- Database Connection Phase I --*/

    private static String info;
    /*---End ---*/
    JLabel jLabel2, name, fname, mname, dob, firstpro, finalpro, cno, phone, address, edu, com, course;
    JTextField txtname, txtfname, txtmname, txtdob, txtfirstpro, txtfinalpro, txtcno, txtphone;
    JButton jAdd, jSearch, jClear, jExit, btupload, jUpdate, jDelete, jReport, jPrint;
    JButton jaddrow1, jaddrow2, jaddrow3;
    JButton jButton2, jButton3;
    JPanel jPanel2, jPanel3, jPanel4, jPanel5, jPanel6, jPanel7, imagePanel;
    JTable jTable1, jTable2, jTable3;
    JTextArea txtaraddress;
    MyDocumentFilter documentFilter; 	/* Numbers Only */
    JLabel imageLabel;

    Image img2;   /* upload Image */
    WebCam wc;
    /*--- Look and find ----*/
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    
    /*  ----------------- */
    Border bDetails;
    JLabel lbl;
    JPanel jPanel1;
    DefaultTableModel dtm;
    String filePath = null;
    Connection connection = null;
    PreparedStatement ps = null;
    Connection dbconn = null;
    private String b;
    
    

    public SecondWind() /*------------------- start ------------------*/ {
        /*-- Database Connect Phase II (connection) --*/
        String url;
        try {
            System.out.println("Boom !!! Boom !!!");
            url = "jdbc:odbc:test";
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            dbconn = DriverManager.getConnection(url, "root", "root");
            System.out.println("Bang");
            //url ="jdbc:mysql://localhost/" +"test"+ "?user=root&password=" +"student"+ "&useUnicode=ue&characterEncoding=gb2312"; //original

            dbconn = DriverManager.getConnection(url);
            info = "Connection successful\n";
        } catch (ClassNotFoundException cnfex) //yes 3 catches
        {
            cnfex.printStackTrace();
            info = info + "Connection unsuccessful\n" + cnfex.toString();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            info = info + "Connection unsuccessful\n" + sqlex.toString();
        } catch (Exception excp) {
            excp.printStackTrace();
            info = info + excp.toString();
        }
        JOptionPane.showMessageDialog(null, info, "Welcome To Society Of The Helpers Of Mary", JOptionPane.INFORMATION_MESSAGE);
        /*-- End --*/

        Border bDetails = BorderFactory.createTitledBorder(" ");

/* --------------  Add row in JTable -------------- */
        Vector<String> headers = new Vector<String>();
        headers.add("Academic");
        headers.add("Professional");
        headers.add("Theological");

        DefaultTableModel dtm = new DefaultTableModel(0, 3);
        jTable1 = new JTable(dtm);
        jTable2 = new JTable(dtm);
        jTable3 = new JTable(dtm);

        for (int x = 0; x < 0; x++) {
            insertRow();
        }
        /* --------------  End -------------- */




        Container p = getContentPane();
        this.setTitle("Admission");
        this.setSize(1300, 715);
        this.setLocation(50, 5);
        this.setResizable(false);
        this.show(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jPanel1 = new JPanel();
    
getContentPane().setLayout(null);

        /*----------------- Panel Arrangement -------------------*/
        String value1, value2, value3;
        jPanel1 = new javax.swing.JPanel();
        name = new javax.swing.JLabel();
        fname = new javax.swing.JLabel();
        mname = new javax.swing.JLabel();
        dob = new javax.swing.JLabel();
        firstpro = new javax.swing.JLabel();
        finalpro = new javax.swing.JLabel();
        cno = new javax.swing.JLabel();
        phone = new javax.swing.JLabel();
        address = new javax.swing.JLabel();
        txtname = new javax.swing.JTextField();
        txtfname = new javax.swing.JTextField();
        txtmname = new javax.swing.JTextField();
        txtdob = new javax.swing.JTextField();
        txtfirstpro = new javax.swing.JTextField();
        txtfinalpro = new javax.swing.JTextField();
        txtcno = new javax.swing.JTextField();
        txtphone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaraddress = new javax.swing.JTextArea();
        imagePanel = new PicturePanel();
        jPanel3 = new javax.swing.JPanel();
        btupload = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jaddrow1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jaddrow2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jaddrow3 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jAdd = new javax.swing.JButton();
        jSearch = new javax.swing.JButton();
        jUpdate = new javax.swing.JButton();
        jDelete = new javax.swing.JButton();
        jReport = new javax.swing.JButton();
        jClear = new javax.swing.JButton();
        jExit = new javax.swing.JButton();
        jPrint = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel();
        
        jScrollPane5 = new javax.swing.JScrollPane();
        jScrollPane5.setViewportView(imageLabel);
        getContentPane().add(jScrollPane5);
        jScrollPane5.setBounds(60, 440, 210, 160);

        

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Details", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        name.setText("Name");
        fname.setText("Father Name");
        mname.setText("Mother Name");
        dob.setText("Date of Birth");
        firstpro.setText("Date of First Profession ");
        finalpro.setText("Date of Final Profession");
        cno.setText("Congregation No.");
        phone.setText("Phone");
        address.setText("Address");
        txtaraddress.setColumns(20);
        txtaraddress.setLineWrap(true);
        txtaraddress.setRows(5);

        jScrollPane1.setViewportView(txtaraddress);
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(name)
                .addComponent(fname)
                .addComponent(mname)
                .addComponent(dob)
                .addComponent(firstpro)
                .addComponent(finalpro)
                .addComponent(cno)
                .addComponent(phone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(txtcno)
                .addComponent(txtfirstpro)
                .addComponent(txtfinalpro)
                .addComponent(txtphone)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(txtname, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addComponent(txtfname)
                .addComponent(txtmname)
                .addComponent(txtdob)))))
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(address)
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1)))
                .addGap(40, 40, 40)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(name)
                .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fname)
                .addComponent(txtfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mname)
                .addComponent(txtmname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(dob)
                .addComponent(txtdob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(firstpro)
                .addComponent(txtfirstpro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(finalpro)
                .addComponent(txtfinalpro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cno)
                .addComponent(txtcno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(phone)
                .addComponent(txtphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(address)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))));

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
                imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 217, Short.MAX_VALUE));
        imagePanelLayout.setVerticalGroup(
                imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 155, Short.MAX_VALUE));

        btupload.setText("Upload Photo");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btupload, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btupload)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Education Qualification", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
            {null, null, null},
            {null, null, null},
            {null, null, null},
            {null, null, null},
            {null, null, null}
        },
                new String[]{
            "ID","Academic", "Professional", "Theological"
        }));
        jTable1.setCellSelectionEnabled(true);
        jScrollPane2.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setResizable(false);


        jaddrow1.setText("Add Row");
        //jaddrow1.addActionListener(new java.awt.event.ActionListener() {

        //});

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jaddrow1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jaddrow1)
                .addContainerGap()))));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Course Attended: (inclusive of Seminars, Refresher Course etc.)", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jTable2.setAutoCreateRowSorter(true);        
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
            {null, null, null},
            {null, null, null},
            {null, null, null},
            {null, null, null},
            {null, null, null},
            {null, null, null}
        },
                new String[]{
            "ID","Type", "Duration", "Place"
        }));
        jTable2.setCellSelectionEnabled(true);
        jTable2.getColumnModel().getColumn(0).setResizable(false);
        jScrollPane3.setViewportView(jTable2);

        jaddrow2.setText("Add Row");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addGap(18, 18, 18)
                .addComponent(jaddrow2)
                .addContainerGap()));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jaddrow2)
                .addGap(29, 29, 29))
                .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Religious Assignment: (Communities)", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
            {null, null, null},
            {null, null, null},
            {null, null, null},
            {null, null, null},
            {null, null, null},
            {null, null, null},
            {null, null, null}
        },
                new String[]{
            "ID","Type", "Duration", "Place"
        }));
        jScrollPane4.setViewportView(jTable3);
        jTable3.setCellSelectionEnabled(true);
        jTable3.getColumnModel().getColumn(0).setResizable(false);
        
        jaddrow3.setText("Add Row");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addGap(18, 18, 18)
                .addComponent(jaddrow3)
                .addContainerGap()));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addComponent(jaddrow3)
                .addGap(22, 22, 22))
                .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap()));


        jAdd.setText("Save Data");
        jSearch.setText("Search");
        jUpdate.setText("Update Data");
        jDelete.setText("Delete Data");
        jReport.setText("Report Card");
        jClear.setText("Clear Form");
        jPrint.setText("Print Form");
        jExit.setText("Exit");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jReport, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jClear, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jExit, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jAdd)
                .addComponent(jSearch)
                .addComponent(jUpdate)
                .addComponent(jDelete)
                .addComponent(jReport)
                .addComponent(jClear)
                .addComponent(jPrint)
                .addComponent(jExit))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE)));
        jPanel1.setBorder(bDetails);


        /*-------------------End Panel Arrangment ---------------------*/

        /*------------------ Main Progam Start -------------------*/

        /*--------- Numbers only -----------*/
        ((AbstractDocument) txtphone.getDocument()).setDocumentFilter(new MyDocumentFilter());
        /*-------------*/

        /*---------- Listener Part----------*/

        jAdd.addActionListener(this);
        jSearch.addActionListener(this);
        jReport.addActionListener(this);
        jClear.addActionListener(this);
        jExit.addActionListener(this);
        jUpdate.addActionListener(this);
        jDelete.addActionListener(this);
        jPrint.addActionListener(this);

        jaddrow1.addActionListener(this);
        jaddrow2.addActionListener(this);
        jaddrow3.addActionListener(this);
        btupload.addActionListener(this);


        /*----------- End ------------------*/

    }  /*End SecondWind Constructor */

    

    public void actionPerformed(ActionEvent e) 
    {
        PreparedStatement pstm;
        int dialogtype = JOptionPane.PLAIN_MESSAGE;
        String dm = "";

        String name = "";
//	String ID="";     //must initialize to ""
        String fname = "";
        String mname = "";
        String dob = "";
        String firstp = "";
        String finalp = "";
        String cno = "";
        String phone = "";
        String address = "";
        int i;

        Object source = e.getSource();
        name = txtname.getText().trim();
        txtname.setText(name);
        fname = txtfname.getText().trim();
        txtfname.setText(fname);
        mname = txtmname.getText().trim();
        txtmname.setText(mname);
        dob = txtdob.getText().trim();
        txtdob.setText(dob);
        firstp = txtfirstpro.getText().trim();
        txtfirstpro.setText(firstp);
        finalp = txtfinalpro.getText().trim();
        txtfinalpro.setText(finalp);
        cno = txtcno.getText().trim();
        txtcno.setText(cno);
        phone = txtphone.getText().trim();
        txtphone.setText(phone);

        address = txtaraddress.getText().trim();
        txtaraddress.setText(address);

        ResultSet rs;
        String sql;



        int index;
        int count;

        if (e.getSource() == jAdd) {
            
            try {
                Statement statement = dbconn.createStatement();
                if (!name.equals("")
                        && !fname.equals("")
                        && !mname.equals("")
                        && !dob.equals("")
                        && !firstp.equals("")
                        && !finalp.equals("")
                        && !cno.equals("")
                        && !phone.equals("")
                        && !address.equals("")) {
            
                    String temp = "INSERT INTO dummy1 (" + "Name, fname," + "mname" + ", dob" + ", firstp" + ", finalp" + ",cno" + ",phone" + ",address" + ") VALUES ('" + name + "', '" + fname + "', '" + mname + "','" + dob + "','" + firstp + "','" + finalp + "','" + cno + "', '" + phone + "', '" + address + "')";
//JOptionPane.showMessageDialog(null, dbconn.nativeSQL( temp ),"Prem Sadan", JOptionPane.INFORMATION_MESSAGE);

                    JOptionPane.showMessageDialog(null, "Details added in MySql DataBase", "Society Of The Helpers Of Mary", JOptionPane.INFORMATION_MESSAGE);
                    int result = statement.executeUpdate(temp);
                    if (result == 1) {
                        String query = "";
                        try {
                            query = "SELECT * FROM dummy1 WHERE Name='" + name + "' AND fname= '" + fname + "'";
                            rs = statement.executeQuery(query);
                            rs.next();
                        } catch (SQLException sqlex) {
                            JOptionPane.showMessageDialog(null, sqlex.toString(), "Society Of The Helpers Of Mary", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Insertion failed", "Society Of The Helpers Of Mary", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Fill all Fields", "Society Of The Helpers Of Mary", JOptionPane.ERROR_MESSAGE);
                }
                statement.close();
            } catch (SQLException sqlex) {
                JOptionPane.showMessageDialog(null, "Entry already exists -- re-enter", "Society Of The Helpers Of Mary", JOptionPane.WARNING_MESSAGE);
            }
            //PreparedStatement pstm;

/*----------------------------   Table 1 -----------------------------*/
            try {                
                index = 1;
                Statement statement = dbconn.createStatement();
                count = jTable1.getRowCount();
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection connect = DriverManager.getConnection("jdbc:odbc:test");
                
                for (i = 0; i <=count; i++) {  
                                    
                    Object id = GetData(jTable1, i, 0);                    
                    Object obj1 = GetData(jTable1, i, 1);
                    Object obj2 = GetData(jTable1, i, 2);
                    Object obj3 = GetData(jTable1, i, 3);
                                                                             
                    String value1 = obj1.toString();
                    String value2 = obj2.toString();
                    String value3 = obj3.toString();
                    
                    boolean exists = false;
                    if(id != null)
                    {
                        exists = CheckExists(id);
                        JOptionPane.showMessageDialog(null,  "id is "+id,"tr", dialogtype, null); 
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,  "null","tr", dialogtype, null); 
                    }                                        
                    String temp;
                    if(exists)
                    {
                        temp = "update education set academic = '"+ value1+"', profess = '"+ value2+"', theo= '"+ value3+"' where name = '"+txtname.getText()+"' AND fname = '"+txtfname.getText()+"' AND id = "+id+" ";
                    }
                    else
                    {
                        temp = "INSERT INTO education (" + "academic, profess," + "theo" + ",name" +", fname"+ ") VALUES ('" + value1 + "', '" + value2 + "', '" + value3 + "', '" + name + "', '"+fname+"')";
                    }
                    
                    int result2 = statement.executeUpdate(temp);
                    if (result2 == 1) {
                        String query2 = "";
                        try {
                            query2 = "SELECT * FROM education WHERE academic='" + value1 + "' AND profess= '" + value2 + "'";
                            rs = statement.executeQuery(query2);
                            rs.next();                          
                        } 
                        catch (SQLException sqlex) {
                            JOptionPane.showMessageDialog(null, sqlex.toString(), "Society Of The Helpers Of Mary", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    index++;
                }          
            } 
            catch (Exception te) {
            }
                    ResultSet rse=null;
                    getDatadb1(rse);            
/*----------------------------   Table 1 End -----------------------------*/
/*----------------------------   Table 2 -----------------------------*/                    
            try {
                int index2 = 1;
                Statement statement = dbconn.createStatement();
                int count2 = jTable2.getRowCount();
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection connect = DriverManager.getConnection("jdbc:odbc:test");
                
                for (i = 0; i <=count2; i++) {  
                                    
                    Object id = GetData2(jTable2, i, 0);                    
                    Object obj1 = GetData2(jTable2, i, 1);
                    Object obj2 = GetData2(jTable2, i, 2);
                    Object obj3 = GetData2(jTable2, i, 3);
                                                                             
                    String value1 = obj1.toString();
                    String value2 = obj2.toString();
                    String value3 = obj3.toString();                                        
                    
                    boolean exists2 = false;
                    if(id != null)
                    {
                        exists2 = CheckExists2(id);
                        JOptionPane.showMessageDialog(null,  "id is "+id,"tr", dialogtype, null); 
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,  "null","tr", dialogtype, null); 
                    }                                        
                    
                    String temp;
                    if(exists2)
                    {
                        temp = "update course set Type = '"+ value1+"', Duration = '"+ value2+"', Place= '"+ value3+"' where name = '"+txtname.getText()+"' AND fname = '"+txtfname.getText()+"' AND id = "+id+" ";
                    }
                    else
                    {
                        temp = "INSERT INTO course (" + "Type, Duration," + "Place" + ",name" + ", fname"+") VALUES ('" + value1 + "', '" + value2 + "', '" + value3 + "', '" + name + "', '" + fname + "')";
                    }
                    
                    int result2 = statement.executeUpdate(temp);
                    if (result2 == 1) {
                        String query2 = "";
                        try {
                            query2 = "SELECT * FROM course WHERE Type='" + value1 + "' AND Duration= '" + value2 + "'";
                            rs = statement.executeQuery(query2);
                            rs.next();
                        } 
                        catch (SQLException sqlex) {
                            JOptionPane.showMessageDialog(null, sqlex.toString(), "Society Of The Helpers Of Mary", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    index2++;
                }          
            } 
            catch (Exception te) {
            }
                    ResultSet rse2=null;
                    getDatadb2(rse2);            
/*----------------------------   Table 2 over -----------------------------*/
/*----------------------------   Table 3 -----------------------------*/                    
                    try {
                      int index3 = 1;
                Statement statement = dbconn.createStatement();
                int count2 = jTable3.getRowCount();
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection connect = DriverManager.getConnection("jdbc:odbc:test");
                
                for (i = 0; i <=count2; i++) {  
                                    
                    Object id = GetData3(jTable3, i, 0);                    
                    Object obj1 = GetData3(jTable3, i, 1);
                    Object obj2 = GetData3(jTable3, i, 2);
                    Object obj3 = GetData3(jTable3, i, 3);
                                                                             
                    String value1 = obj1.toString();
                    String value2 = obj2.toString();
                    String value3 = obj3.toString();                                        
                    
                    boolean exists3 = false;
                    if(id != null)
                    {
                        exists3 = CheckExists3(id);
                        JOptionPane.showMessageDialog(null,  "id is "+id,"tr", dialogtype, null); 
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,  "null","tr", dialogtype, null); 
                    } 
                    String temp;
                    if(exists3)
                    {
                        temp = "update relig set Type = '"+ value1+"', Duration = '"+ value2+"', Place= '"+ value3+"' where name = '"+txtname.getText()+"' AND fname = '"+txtfname.getText()+"'AND id = "+id+" ";
                    }
                    else
                    {
                        temp = "INSERT INTO relig (" + "Type, Duration," + "Place" + ",name" + ", fname"+") VALUES ('" + value1 + "', '" + value2 + "', '" + value3 + "', '" + name + "', '" + fname + "')";
                    }
                    int result3 = statement.executeUpdate(temp);
                    if (result3 == 1) {
                        String query2 = "";
                        try {
                            query2 = "SELECT * FROM relig WHERE Type='" + value1 + "' AND Duration= '" + value2 + "'";
                            rs = statement.executeQuery(query2);
                            rs.next();
                        } 
                        catch (SQLException sqlex) {
                            JOptionPane.showMessageDialog(null, sqlex.toString(), "Society Of The Helpers Of Mary", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    index3++;
                }          
            } 
            catch (Exception te) {
            }
            ResultSet rse3=null;
           getDatadb3(rse3);            
         }
/*----------------------------   Table 3 Over  -----------------------------*/        

        if(e.getSource() == jPrint)
        {
            try{
                
                Statement statement = dbconn.createStatement();
                JFileChooser chooser=new JFileChooser(new File("D://"));  
                chooser.setMultiSelectionEnabled(false);
                chooser.setVisible(true);
                chooser.showSaveDialog(this);
                File file=chooser.getSelectedFile();
                if(file!=null){filePath=file.getPath();}

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file+".pdf"));
                
                document.open();
                
                Image emlogo = Image.getInstance("logo//PDFPSL.gif");
                emlogo.setAbsolutePosition(20f, 719f);
                document.add(emlogo);
                
                Image header = Image.getInstance("logo//header_prem.gif");
                header.setAbsolutePosition(165f, 775f);
                document.add(header);
                
             
            Font font1 = new Font(FontFactory.HELVETICA  , 10, Font.BOLD);
            PdfPTable table = new PdfPTable(4);
            PdfPCell pname = new PdfPCell(new Paragraph(txtname.getText(), FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
            PdfPCell pfname = new PdfPCell(new Paragraph(txtfname.getText(),  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
            PdfPCell pmname = new PdfPCell(new Paragraph(txtmname.getText(), FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
            PdfPCell pdob = new PdfPCell(new Paragraph(txtdob.getText(),  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
            PdfPCell pfir = new PdfPCell(new Paragraph(txtfirstpro.getText(),  FontFactory.getFont(FontFactory.TIMES_ROMAN,11 )));
            PdfPCell pfin = new PdfPCell(new Paragraph(txtfinalpro.getText(),  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
            PdfPCell pcon = new PdfPCell(new Paragraph(txtcno.getText(),  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
            PdfPCell pphone = new PdfPCell(new Paragraph(txtphone.getText(),  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
            PdfPCell paddr = new PdfPCell(new Paragraph(txtaraddress.getText(),  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
            PdfPCell pname2 = new PdfPCell(new Paragraph("Name", FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
            PdfPCell pfname2 = new PdfPCell(new Paragraph("Father Name",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
            PdfPCell pmname2 = new PdfPCell(new Paragraph("Mother Name", FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
            PdfPCell pdob2 = new PdfPCell(new Paragraph("Date of Birth",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
            PdfPCell pfir2 = new PdfPCell(new Paragraph("Date of First Profssion",  FontFactory.getFont(FontFactory.TIMES_BOLD,10 )));
            PdfPCell pfin2 = new PdfPCell(new Paragraph("Date of Final Profession",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
            PdfPCell pcon2 = new PdfPCell(new Paragraph("Congregation No.",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
            PdfPCell pphone2 = new PdfPCell(new Paragraph("Phone",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
            PdfPCell paddr2 = new PdfPCell(new Paragraph("Address",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
            PdfPCell last = new PdfPCell(new Paragraph("   "));
            
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.setWidthPercentage(100);
            table.addCell(pname2);
            table.addCell(pname); 
            table.addCell(pfir2);
            table.addCell(pfir);
//            pic.setRowspan(6);
//            table.addCell(pic);
            table.completeRow();
            
            table.addCell(pfname2);
            table.addCell(pfname);
            table.addCell(pfin2);
            table.addCell(pfin);
            table.completeRow();
            
            table.addCell(pmname2);
            table.addCell(pmname);
            table.addCell(pcon2);
            table.addCell(pcon);
            table.completeRow();
 
            table.addCell(pdob2);
            table.addCell(pdob);
            paddr2.setRowspan(2);
            table.addCell(paddr2);
            paddr.setRowspan(3);
            table.addCell(paddr);
            table.completeRow();

            table.addCell(pphone2);
            table.addCell(pphone);
            table.completeRow();
            
            last.setColspan(3);
            table.addCell(last);
            table.completeRow();            
/*---------------------------------------------------------------------------------------------------------*/
 
            PdfPTable table1 = new PdfPTable(3);
           table1.setHorizontalAlignment(Element.ALIGN_LEFT);
           table1.setWidthPercentage(100);
 
            int rowcount1 = jTable1.getRowCount();
        PdfPCell caca = new PdfPCell(new Paragraph("Academic",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
        PdfPCell cpro = new PdfPCell(new Paragraph("Professional",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
        PdfPCell ctheo = new PdfPCell(new Paragraph("Theological",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
                table1.addCell(caca);
                table1.addCell(cpro);
                table1.addCell(ctheo);

            for (i = 0; i <rowcount1; i++){                     
                Object obj1 = GetData(jTable1, i, 1);
                Object obj2 = GetData(jTable1, i, 2);
                Object obj3 = GetData(jTable1, i, 3);
                
                String academic = obj1.toString();
                String profess = obj2.toString();
                String theo = obj3.toString();
                PdfPCell aca = new PdfPCell(new Paragraph(academic,  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
                PdfPCell pro = new PdfPCell(new Paragraph(profess,  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
                PdfPCell th = new PdfPCell(new Paragraph(theo,  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));

                table1.addCell(aca);
                table1.addCell(pro);
                table1.addCell(th);
            }
       
            PdfPTable table2 = new PdfPTable(3);
           table2.setHorizontalAlignment(Element.ALIGN_LEFT);
           table2.setWidthPercentage(100);
 
            int rowcount2 = jTable2.getRowCount();
        PdfPCell ctype = new PdfPCell(new Paragraph("Type",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
        PdfPCell cdur = new PdfPCell(new Paragraph("Duration",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
        PdfPCell cplace = new PdfPCell(new Paragraph("Place",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));

                table2.addCell(ctype);
                table2.addCell(cdur);
                table2.addCell(cplace);
                
            for (i = 0; i <rowcount2; i++){                     
                Object obj1 = GetData2(jTable2, i, 1);
                Object obj2 = GetData2(jTable2, i, 2);
                Object obj3 = GetData2(jTable2, i, 3);
                
                String type = obj1.toString();
                String dur = obj2.toString();
                String place = obj3.toString();

                PdfPCell ty = new PdfPCell(new Paragraph(type,  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
                PdfPCell du = new PdfPCell(new Paragraph(dur,  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
                PdfPCell pl = new PdfPCell(new Paragraph(place,  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));

                
                table2.addCell(ty);
                table2.addCell(du);
                table2.addCell(pl);
            }
       
            PdfPTable table3 = new PdfPTable(3);
            
            int rowcount3 = jTable3.getRowCount();
           table3.setHorizontalAlignment(Element.ALIGN_LEFT);
           table3.setWidthPercentage(100);
        PdfPCell ctype2 = new PdfPCell(new Paragraph("Type",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
        PdfPCell cdur2 = new PdfPCell(new Paragraph("Duration",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
        PdfPCell cplace2 = new PdfPCell(new Paragraph("Place",  FontFactory.getFont(FontFactory.TIMES_BOLD,10)));

                table3.addCell(ctype2);
                table3.addCell(cdur2);
                table3.addCell(cplace2);
 
            for (i = 0; i <rowcount3; i++){                     
                Object obj1 = GetData3(jTable3, i, 1);
                Object obj2 = GetData3(jTable3, i, 2);
                Object obj3 = GetData3(jTable3, i, 3);
                
                String type2 = obj1.toString();
                String dur2 = obj2.toString();
                String place2 = obj3.toString();
 
                PdfPCell ty2 = new PdfPCell(new Paragraph(type2,  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
                PdfPCell du2 = new PdfPCell(new Paragraph(dur2,  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));
                PdfPCell pl2 = new PdfPCell(new Paragraph(place2,  FontFactory.getFont(FontFactory.TIMES_ROMAN,11)));

                table3.addCell(ty2);
                table3.addCell(du2);
                table3.addCell(pl2);
            }
            
            
            PdfPTable table5=new PdfPTable(1);
table5.setWidthPercentage(20);
Connection result = null;
ps = dbconn.prepareStatement("select image from prem_image where name='"+txtname.getText()+"' and fname='"+txtfname.getText()+"'");
rs = ps.executeQuery();
if (rs != null) {
while(rs.next()) {
Image image = Image.getInstance (rs.getBytes(1));
table5.addCell(image);
table5.setHorizontalAlignment(Element.ALIGN_RIGHT);
//table5.addCell(new String(rs.getBytes(2)));
//table5.addCell(new String(rs.getBytes(3)));
System.out.println("Image:"+rs.getBytes(1));
}}
            document.add(table5);

                document.add(new Paragraph (" "));
                document.add(new Paragraph (" "));
           
            float[] columnWidths = new float[] {8f, 13f, 11f, 10f};
            table.setWidths(columnWidths);
            document.add(table);
            
            document.add(new Paragraph (" "));
            Chunk ueducation = new Chunk("Education Qualification",  FontFactory.getFont(FontFactory.TIMES_BOLD,11));
            ueducation.setUnderline(0.1f, -2f);

            Paragraph education = new Paragraph(); 
            education.add(ueducation);
            education.setSpacingAfter(7);
            document.add(education);
            
            document.add(table1);
            
            document.add(new Paragraph (" "));
            Chunk ucourse = new Chunk("Course Attended: (Inclusive of Seminar, Refresher Course etc.)",  FontFactory.getFont(FontFactory.TIMES_BOLD,11));
            ucourse.setUnderline(0.1f, -2f);

            Paragraph course = new Paragraph ();
            course.add(ucourse);
            course.setSpacingAfter(7);            
            document.add(course);        
            
            document.add(table2);
            
            document.add(new Paragraph (" "));
            Chunk ureligs = new Chunk("Religious Assignment: (Communities)",  FontFactory.getFont(FontFactory.TIMES_BOLD,11));
            ureligs.setUnderline(0.1f, -2f);

            Paragraph religs = new Paragraph ();
            religs.add(ureligs);
            religs.setSpacingAfter(7);
            document.add(religs);
            
            document.add(table3);
            
                        
            document.close();
//            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+file);
            JOptionPane.showMessageDialog(null, "Report Saved");
}
catch(Exception ex){
           JOptionPane.showMessageDialog(null, "Report not Saved");
}
        }
        
        
        
        if (e.getSource() == jReport) {
//            re.setVisible(true);
        }

        if (e.getSource() == jaddrow1) {
            insertRow();
        }
        if (e.getSource() == jaddrow2) {
            insertRow2();
        }
        if (e.getSource() == jaddrow3) {
            insertRow3();
        }
        /*---------------------------------------------------------------------------------*/
        if (e.getSource() == jClear) {
            txtname.setText("");
            txtfname.setText("");
            txtmname.setText("");
            txtdob.setText("");
            txtfirstpro.setText("");
            txtfinalpro.setText("");
            txtcno.setText("");
            txtphone.setText("");
            txtaraddress.setText("");
            imageLabel.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().getImage("")));  /* Display Blank Image */
            clearTable1(jTable1);
            clearTable2(jTable2);
            clearTable3(jTable3);
        }
        /*---------------------------------------------------------------------------------*/
        if (e.getSource() == jExit) {
            System.exit(0);
        }
        /*---------------------------------------------------------------------------------*/

        if (e.getSource() == btupload) /*  upload image */ {
//            String file = getImageFile();
//            Toolkit kit = Toolkit.getDefaultToolkit();
//            img = kit.getImage(file);
//            img = img.getScaledInstance(220, 170, Image.SCALE_SMOOTH);
//            this.repaint();           
  //          storeImage();
            
            wc = new WebCam();
            wc.setVisible(rootPaneCheckingEnabled);
            wc.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        }
 /*---------------------------------------------------------------------------------*/
        if (e.getSource() == jSearch) {
            try {
                if (!name.equals("") && !fname.equals("")) {
                    System.out.println("First=" + name + " Last=" + fname);
                    Statement statement = dbconn.createStatement();
                    String query = "SELECT * FROM dummy1 WHERE Name='" + name + "' AND fname = '" + fname + "'";

                    rs = statement.executeQuery(query);
                    display(rs);
                    retriveImage();
                    statement.close();
                } else {
                    JOptionPane.showMessageDialog(null, "Enter Name and Father name", "Society Of The Helpers Of Mary", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException sqlex) {}
}
/*---------------------------------------------------------------------------------*/
        if (e.getSource() == jUpdate) {
            
            ugetDatadb1();      // update table 1
            ugetDatadb2();      // update table 2
            ugetDatadb3();      // update table 3
            
            String uname = "";
            String ufname = "";
            String umname = "";
            String udob = "";
            String ufirstp = "";
            String ufinalp = "";
            String ucno = "";
            String uphone = "";
            String uaddress = "";

            Object usource = e.getSource();
            uname = txtname.getText().trim();
            txtname.setText(uname);
            ufname = txtfname.getText().trim();
            txtfname.setText(ufname);
            umname = txtmname.getText().trim();
            txtmname.setText(umname);
            udob = txtdob.getText().trim();
            txtdob.setText(udob);
            ufirstp = txtfirstpro.getText().trim();
            txtfirstpro.setText(ufirstp);
            ufinalp = txtfinalpro.getText().trim();
            txtfinalpro.setText(ufinalp);
            ucno = txtcno.getText().trim();
            txtcno.setText(ucno);
            uphone = txtphone.getText().trim();
            txtphone.setText(uphone);

            uaddress = txtaraddress.getText().trim();
            txtaraddress.setText(uaddress);
            String utext1 = "update dummy1 set Name='" + uname + "', fname = '" + ufname + "', mname = '" + umname + "', dob = '" + udob + "', firstp = '" + ufirstp + "', finalp = '" + ufinalp + "', cno = '" + ucno + "', phone = '" + uphone + "', address = '" + uaddress + "' where Name='" + uname + "' AND fname='"+ufname+"' ";
          //String utext1 = "update dummy1 set Name='" + uname + "', fname = '" + ufname + "', mname = '" + umname + "', dob = '" + udob + "', firstp = '" + ufirstp + "', finalp = '" + ufinalp + "', cno = '" + ucno + "', phone = '" + uphone + "', address = '" + uaddress + "' where Name='" + uname + "' ";
                   
           try {
               
                Statement statement = dbconn.createStatement();
                if (!name.equals("")) {
                   
                    int result = statement.executeUpdate(utext1);
                    if (result == 1) {
                        JOptionPane.showMessageDialog(null, "Update Successful", "Society Of The Helpers Of Mary", JOptionPane.WARNING_MESSAGE);                       
                    } else {                        
                        JOptionPane.showMessageDialog(null, "Update successful", "Society Of The Helpers Of Mary", JOptionPane.INFORMATION_MESSAGE);
                        txtname.setText("");
                        txtfname.setText("");
                        txtmname.setText("");
                        txtdob.setText("");
                        txtfirstpro.setText("");
                        txtfinalpro.setText("");
                        txtcno.setText("");
                        txtphone.setText("");
                        txtaraddress.setText("");
                        
                        clearTable1(jTable1);
                        clearTable2(jTable2);
                        clearTable3(jTable3);
                        imageLabel.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().getImage("")));  /* Display Blank Image */

                        
                    }
                    statement.close();
                } else {
                    JOptionPane.showMessageDialog(null, "You may only update an existing record. Use Find to \nlocate the record, then modify the information and \npress Update.\n", "Society Of The Helpers Of Mary", JOptionPane.QUESTION_MESSAGE);
                }
            } catch (SQLException sqlex) {
                //txtInfo.append( sqlex.toString());
            }
        }
        
        /*---------------------------------------------------------------------------------*/
        if (e.getSource() == jDelete) {
            deletetable1();
            deletetable2();
            deletetable3();
            
            String dname=null;
            dname = txtname.getText().trim();
            txtname.setText(dname);
            try {
                Statement statement = dbconn.createStatement();
                if (!txtname.getText().equals("")) {
                    String dtemp = "DELETE from dummy1 " + " WHERE Name= '"+dname+"' AND fname= '"+txtfname.getText()+"'";
                    
                    int result = statement.executeUpdate(dtemp);                   
                    if (result == 1) {
                        deleteimage();
                        JOptionPane.showMessageDialog(null, "Deletion successful", "Society Of The Helpers Of Mary", JOptionPane.INFORMATION_MESSAGE);
                        clearTable1(jTable1);
                        clearTable2(jTable2);
                        clearTable3(jTable3);
                        txtname.setText("");
                        txtfname.setText("");
                        txtmname.setText("");
                        txtdob.setText("");
                        txtfirstpro.setText("");
                        txtfinalpro.setText("");
                        txtcno.setText("");
                        txtphone.setText("");
                        txtaraddress.setText("");
                        imageLabel.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().getImage("")));  /* Display Blank Image */
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Deletion failed", "Society Of The Helpers Of Mary", JOptionPane.INFORMATION_MESSAGE);
                        txtname.setText("");
                        txtfname.setText("");
                        txtmname.setText("");
                        txtdob.setText("");
                        txtfirstpro.setText("");
                        txtfinalpro.setText("");
                        txtcno.setText("");
                        txtphone.setText("");
                        txtaraddress.setText("");
                    }
                    statement.close();
                } else {
                    JOptionPane.showMessageDialog(null, "You may only delete an existing record. Use Find to \nlocate the record, then press delete.", "Society Of The Helpers Of Mary", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException sqlex) {
            }
        }
    } 
    
 /*--- -------------------------------------------- End Performed ------------------------------------------------ ----*/

/*---------------- Display Table Data -----------------*/

     private void ugetDatadb1() {
         String dbname1 = txtname.getText().trim();
         txtname.setText(dbname1);
         
         try {                          
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection connect = DriverManager.getConnection("jdbc:odbc:test");
            PreparedStatement pstatement;

            int rowcount = jTable1.getRowCount();

            for (int i = 0; i <rowcount; i++) {                    
                Object obj1 = GetData(jTable1, i, 0);
                Object obj2 = GetData(jTable1, i, 1);
                Object obj3 = GetData(jTable1, i, 2);
                Object obj4 = GetData(jTable1, i, 3);

                String id = obj1.toString();
                int ids = Integer.parseInt(id);
                String academic = obj2.toString();
                String profess = obj3.toString();
                String theo = obj4.toString();

String temp = "update education set academic = '"+ academic+"', profess = '"+ profess+"', theo= '"+ theo+"' where name = '"+txtname.getText()+"' AND fname= '"+txtfname.getText()+"' AND id = "+ids+" ";
               
                pstatement = connect.prepareStatement(temp);
               int roweffect =  pstatement.executeUpdate();
                
                System.out.println(roweffect);
                
            }ResultSet rse=null;
                getDatadb1(rse); 
                
         }
         catch (Exception te) {}
}
/* ------------------------------------------------------------------------------------------------------------------------------------*/
      private void ugetDatadb2(){

          String dbname1 = txtname.getText().trim();
         txtname.setText(dbname1);
         
         try {                          
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection connect = DriverManager.getConnection("jdbc:odbc:test");
            PreparedStatement pstatement;

            int rowcount = jTable2.getRowCount();

            for (int i = 0; i <rowcount; i++) {                    
                Object obj1 = GetData2(jTable2, i, 0);
                Object obj2 = GetData2(jTable2, i, 1);
                Object obj3 = GetData2(jTable2, i, 2);
                Object obj4 = GetData2(jTable2, i, 3);

                String id = obj1.toString();
                int ids = Integer.parseInt(id);
                String type = obj2.toString();
                String dur = obj3.toString();
                String place = obj4.toString();

String temp = "update course set Type= '"+ type+"', Duration = '"+ dur+"', Place= '"+ place+"' where name = '"+txtname.getText()+"' AND fname = '"+txtfname.getText()+"' AND id = "+ids+" ";
               
                pstatement = connect.prepareStatement(temp);
                int roweffect =  pstatement.executeUpdate();
                System.out.println(roweffect);
                
            }ResultSet rse2=null;
                getDatadb2(rse2); 
         }
         catch (Exception te) {}
  }
/* ------------------------------------------------------------------------------------------------------------------------------------*/
     
      private void ugetDatadb3() {
         String dbname1 = txtname.getText().trim();
         txtname.setText(dbname1);
         
         try {                          
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection connect = DriverManager.getConnection("jdbc:odbc:test");
            PreparedStatement pstatement;

            int rowcount = jTable3.getRowCount();

            for (int i = 0; i <rowcount; i++) {                    
                Object obj1 = GetData3(jTable3, i, 0);
                Object obj2 = GetData3(jTable3, i, 1);
                Object obj3 = GetData3(jTable3, i, 2);
                Object obj4 = GetData3(jTable3, i, 3);

                String id = obj1.toString();
                int ids = Integer.parseInt(id);
                String type = obj2.toString();
                String dur = obj3.toString();
                String place = obj4.toString();

String temp = "update relig set Type= '"+ type+"', Duration = '"+ dur+"', Place= '"+ place+"' where name = '"+txtname.getText()+"' AND fname='"+txtfname.getText()+"' AND id = "+ids+" ";
               
                pstatement = connect.prepareStatement(temp);
                int roweffect =  pstatement.executeUpdate();
                
                System.out.println(roweffect);
                
            }ResultSet rse3=null;
                getDatadb3(rse3); 
                
         }
         catch (Exception te) {}
                
    }
 /* ------------------------------------------------- End Table -----------------------------------------------------------------------------------*/
     
     public boolean CheckExists(Object id)
     {
         boolean exists = false;
         try
         {
            Statement statement = dbconn.createStatement();
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection connect = DriverManager.getConnection("jdbc:odbc:test");         
            String query = "Select * from education where id = " + id + "";
            ResultSet rss = statement.executeQuery(query);
            if(rss.next())
            {
                exists = true;
            }
         }
         catch(Exception u){
         }
         return exists;
     }
     
     public boolean CheckExists2(Object id2)
     {
         boolean exists = false;
         try
         {
            Statement statement = dbconn.createStatement();
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection connect = DriverManager.getConnection("jdbc:odbc:test");         
            String query = "Select * from course where id = " + id2 + "";
            ResultSet rss = statement.executeQuery(query);
            if(rss.next())
            {
                exists = true;
            }
         }
         catch(Exception uc){
         }
         return exists;
     }
     
       public boolean CheckExists3(Object id3)
     {
         boolean exists = false;
         try
         {
            Statement statement = dbconn.createStatement();
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection connect = DriverManager.getConnection("jdbc:odbc:test");         
            String query = "Select * from relig where id = " + id3 + "";
            ResultSet rss = statement.executeQuery(query);
            if(rss.next())
            {
                exists = true;
            }
         }
         catch(Exception uc){
         }
         return exists;
     } 
       
 /* ------------------------------------------------------------------------------------------------------------------------------------*/
    private void getDatadb1(ResultSet rs) {
        // Enter Your MySQL Database Table name in below Select Query.
        String dbname1, dbfname;
        dbname1 = txtname.getText().trim();
        txtname.setText(dbname1);
        dbfname = txtfname.getText().trim();
        txtfname.setText(dbfname);

        try {
            //String str="SELECT education.academic, education.profess, education.theo FROM education INNER JOIN dummy1 ON education.name = dummy1.name";
            String str1 = "SELECT id, academic, profess, theo FROM education where name  = '" + dbname1 + "' AND fname='"+dbfname+"'";
            ps = dbconn.prepareStatement(str1);
            rs = ps.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }

    private void getDatadb2(ResultSet rs) {
        // Enter Your MySQL Database Table name in below Select Query.
        String dbname2, dbfname2;
        dbname2 = txtname.getText().trim();
        txtname.setText(dbname2);
        dbfname2 = txtfname.getText().trim();
        txtfname.setText(dbfname2);


        try {
            String str2 = "SELECT id, Type, Duration, Place FROM course where name  = '" + dbname2 + "' AND fname= '"+dbfname2+"' ";
            ps = dbconn.prepareStatement(str2);
            rs = ps.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }

    private void getDatadb3(ResultSet rs) {
        // Enter Your MySQL Database Table name in below Select Query.
        String dbname3, dbfname3;
        dbname3 = txtname.getText().trim();
        txtname.setText(dbname3);
        dbfname3 = txtfname.getText().trim();
        txtfname.setText(dbfname3);


        try {
//  String str3="SELECT relig.Type, relig.Duration, relig.Place FROM relig INNER JOIN dummy1 ON relig.name = dummy1.name";
            String str3 = "SELECT id, Type, Duration, Place FROM relig where name  = '" + dbname3 + "' AND fname = '"+dbfname3+"' ";
            ps = dbconn.prepareStatement(str3);
            rs = ps.executeQuery();
            jTable3.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }
/* ------------------------------------------------------------------------------------------------------------------------------------*/
        public static void clearTable1(final JTable jTable1) {
   for (int i = 0; i < jTable1.getRowCount(); i++)
      for(int j = 0; j < jTable1.getColumnCount(); j++) {
          jTable1.setValueAt("", i, j);
      }
   }
        
        public static void clearTable2(final JTable jTable2) {
   for (int i = 0; i < jTable2.getRowCount(); i++)
      for(int j = 0; j < jTable2.getColumnCount(); j++) {
          jTable2.setValueAt("", i, j);
      }
   }
        
        public static void clearTable3(final JTable jTable3) {
   for (int i = 0; i < jTable3.getRowCount(); i++)
      for(int j = 0; j < jTable3.getColumnCount(); j++) {
          jTable3.setValueAt("", i, j);
      }
   }
    
    public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {
                      
            Graphics2D g2 = (Graphics2D) g;
            g2.translate(pf.getImageableX()+5, pf.getImageableY()+5);
            
    	    Font  f = new Font("Monospaced",Font.PLAIN,12);
    	    g2.setFont (f);
	    	paint (g2);
	    	
            return Printable.PAGE_EXISTS;
        }
    
/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    public void display(ResultSet rs) {
        try {
            rs.next();
            txtname.setText(rs.getString(2)); //2 is second column in database
            txtfname.setText(rs.getString(3)); //3 is third column in database
            txtmname.setText(rs.getString(4));
            txtdob.setText(rs.getString(5));
            txtfirstpro.setText(rs.getString(6));
            txtfinalpro.setText(rs.getString(7));
            txtcno.setText(rs.getString(8));
            txtphone.setText(rs.getString(9));
            txtaraddress.setText(rs.getString(10));
            getDatadb1(rs);             //display( rs );
            getDatadb2(rs);
            getDatadb3(rs);
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "*** No In Database ***", "Society Of The Helpers Of Mary", JOptionPane.ERROR_MESSAGE);
        }
    }
/* ------------------------------------------------------END------------------------------------------------------------------------------*/
    
private void deletetable1()
       {
           try{
            Statement statement = dbconn.createStatement();
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection connect = DriverManager.getConnection("jdbc:odbc:test");         
            String query = "delete from education where name = '"+txtname.getText()+"' AND fname= '"+txtfname.getText()+"' ";
            ResultSet rss = statement.executeQuery(query);
            if(rss.next())
            {
                System.out.println("Delete data");
            }
           }
           catch(Exception de){ 
               System.out.println("Error" );}
       }

private void deletetable2()
       {
           try{
            Statement statement = dbconn.createStatement();
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection connect = DriverManager.getConnection("jdbc:odbc:test");         
            String query = "delete from course where name = '"+txtname.getText()+"' AND fname = '"+txtfname.getText()+"' ";
            ResultSet rss = statement.executeQuery(query);
            if(rss.next())
            {
                System.out.println("Delete data");
            }
           }
           catch(Exception de){ 
               System.out.println("Error" );}
       }
    
private void deletetable3()
       {
           try{
            Statement statement = dbconn.createStatement();
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection connect = DriverManager.getConnection("jdbc:odbc:test");         
            String query = "delete from relig where name = '"+txtname.getText()+"' AND fname= '"+txtfname.getText()+"'";
            ResultSet rss = statement.executeQuery(query);
            if(rss.next())
            {
                System.out.println("Delete data");
            }
           }
           catch(Exception de){ 
               System.out.println("Error" );}
       }

private void deleteimage()
{
           try{
            Statement statement = dbconn.createStatement();
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection connect = DriverManager.getConnection("jdbc:odbc:test");         
            String query = "delete from prem_image where name = '"+txtname.getText()+"' AND fname = '"+txtfname.getText()+"'";
            ResultSet rss = statement.executeQuery(query);
            if(rss.next())
            {
                System.out.println("Delete data");
            }
           }
           catch(Exception de){ 
               System.out.println("Error" );}
    
}
    
/* ----------------------------------------  Store / Retrive Image ------------------------------------------------------------*/
public void storeImage()
{
try
{
Statement statement = dbconn.createStatement();
JFileChooser chooser=new JFileChooser(new File("D://"));
chooser.setMultiSelectionEnabled(false);
chooser.setVisible(true);
chooser.showOpenDialog(this);

File file=chooser.getSelectedFile();
if(file!=null){filePath=file.getPath();}
if(filePath!=null){
imageLabel.setIcon(new ImageIcon(filePath));

}

if(filePath!=null && checkImage())
{
ps=dbconn.prepareStatement("insert into prem_image values(?,?,?)");
FileInputStream fileInputStream=new FileInputStream(filePath);
byte b[]=new byte[fileInputStream.available()];
fileInputStream.read(b);
fileInputStream.close();
ps.setObject(1, txtname.getText());
ps.setBytes(2, b);
ps.setObject(3, txtfname.getText());

int val=ps.executeUpdate();
if(val>=1)JOptionPane.showMessageDialog(this, "Succesfully Stored...");
else
JOptionPane.showMessageDialog(this, "Error in storage...");
}
else
{
JOptionPane.showMessageDialog(this,"Please select an Image of type .jpeg/gif/jpg...");
}

}catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage());
e.printStackTrace();
}
} 
/* -------------------------------------------------------------------------------------------------- */

public void retriveImage()
{
ResultSet rs;
    try
    {
String val=txtname.getText();
String val2=txtfname.getText();

if(txtname.getText() != "")
{
ps=dbconn.prepareStatement("select * from prem_image where name=? and fname=? ");
ps.setObject(1, val);
ps.setObject(2, val2);
rs=ps.executeQuery();
byte b[] = null;
while(rs.next())
{
b= rs.getBytes(2);
}
imageLabel.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().createImage(b)));
}else
{
JOptionPane.showMessageDialog(this,"Please enter Name..." );
}
}catch(Exception ime){}
}
/* -------------------------------------------------------------------------------------------------- */

private boolean checkImage() {
if(filePath!=null)
{
if(filePath.endsWith(".jpeg")||filePath.endsWith(".gif")||filePath.endsWith(".jpg")||filePath.endsWith(".JPEG")||filePath.endsWith(".GIF")||filePath.endsWith(".JPG"))
{
return true;
}
return false;
}
return false;
}
/* ------------------------------------- Storing and Retriving Image over ------------------------------------------------------------- */


    
    public Object GetData(JTable jTable1, int row_index, int col_index) {
        return jTable1.getModel().getValueAt(row_index, col_index);
    }

    public Object GetData2(JTable jTable2, int row_index, int col_index) {
        return jTable2.getModel().getValueAt(row_index, col_index);
    }

    public Object GetData3(JTable jTable3, int row_index, int col_index) {
        return jTable3.getModel().getValueAt(row_index, col_index);
    }

 /* ------------------------------------------------------------------------------------------------------------------------------------*/
    

    
    public static void main(String args[]) {
        /*  Look and Feel Setting */

        /*------------------*/
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SecondWind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SecondWind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SecondWind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SecondWind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /*---------- End --------*/

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SecondWind();
            }
        });
    }
   
 /* ----------------------------------------Insert Row in jTable 1,2,3-------------------------------------------------------------------*/

    public void insertRow() {
        ((DefaultTableModel) jTable1.getModel()).addRow(new java.util.Vector<String>(java.util.Arrays.asList(new String[]{"", "", ""})));
    }

    public void insertRow2() {
        ((DefaultTableModel) jTable2.getModel()).addRow(new java.util.Vector<String>(java.util.Arrays.asList(new String[]{"", "", ""})));
    }

    public void insertRow3() {
        ((DefaultTableModel) jTable3.getModel()).addRow(new java.util.Vector<String>(java.util.Arrays.asList(new String[]{"", "", ""})));
    }
    /* ------------------ End -----------------*/

    /*------------ get Image --------------------*/
    private String getImageFile() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new ImageFilter());
        int result = fc.showOpenDialog(null);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            return file.getPath();
        } else {
            return null;
        }
    }

    private String indexValueof(byte[] b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   /* @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
*/
    private class PicturePanel extends JPanel {

        public void paint(Graphics g) {
            g.drawImage(null, 0, 0, this);
        }
    }

    private class ImageFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String name = f.getName();
            if (name.matches(".*((.jpg)|(.gif)|(.png))")) {
                return true;
            } else {
                return false;
            }
        }

        public String getDescription() {
            return "Image files (*.jpg, *.gif, *.png)";
        }
    }
}
/*------------ Numbers Only Class ------------*/
class MyDocumentFilter extends DocumentFilter {

    @Override
    public void insertString(DocumentFilter.FilterBypass fp, int offset, String string, AttributeSet aset)
            throws BadLocationException {
        int len = string.length();
        boolean isValidInteger = true;

        for (int i = 0; i < len; i++) {
            if (!Character.isDigit(string.charAt(i))) {
                isValidInteger = false;
                break;
            }
        }
        if (isValidInteger) {
            super.insertString(fp, offset, string, aset);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fp, int offset, int length, String string, AttributeSet aset)
            throws BadLocationException {
        int len = string.length();
        boolean isValidInteger = true;

        for (int i = 0; i < len; i++) {
            if (!Character.isDigit(string.charAt(i))) {
                isValidInteger = false;
                break;
            }
        }
        if (isValidInteger) {
            super.replace(fp, offset, length, string, aset);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
