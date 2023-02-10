
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapstoneProject;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_highgui;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author UnknownUser
 */
public class residentsUIButton extends javax.swing.JFrame {

    String JDBCDriver = "com.mysql.cj.jdbc.Driver";
    String serverUrl = "jdbc:mysql://localhost:3306/bims_db";
    String databaseUsername = "root";
    String databasePassword = "";
    String crudID = "";
    String crudResidentID = "";
    String crudFirstName = "";
    String crudMiddleName = "";
    String crudLastName = "";
    String crudGender = "";
    String crudCivilStatus = "";
    String crudAge = "";
    String crudReligion = "";
    String crudFatherName = "";
    String crudFatherOccupation = "";
    String crudMotherName = "";
    String crudMotherOccupation = "";
    String crudAddress = "";
    String crudImagePath = "";
    String crudImageAbsolutePath = "";
    String crudNumberofSiblings = "";
    String crudBirthRank = "";
    String crudDisabledPerson = "";
    String crudfourpsStatus = "";
    String crudHHGrantee = "";
    String crudHHRelation = "";
    String crudChildBeneficiary = "";
    String crudRegisteredVoter = "";
    String crudDate = "";
    Connection connection = null;
    DefaultTableModel model;

    /**
     * Creates new form residentsUIButton
     */
    public residentsUIButton() {
        initComponents();
        CurrentDateandTime();
        connection = databaseConnection();
        populateJTableFromMysqlDatabase();
        setTitle("Residents");
        //showTable();
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
    }

    public Connection databaseConnection() {
        try {
            Class.forName(JDBCDriver);
            System.out.println("Driver Loaded");
            Connection conn = DriverManager.getConnection(serverUrl, databaseUsername, databasePassword);
            System.out.println("Connected to Database");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //Store Database Results in an ArrayList
    public ArrayList<residentsUIButtonUpdateDelete> CRUDList(String searchTerm) {
        ArrayList<residentsUIButtonUpdateDelete> crudlist = new ArrayList<residentsUIButtonUpdateDelete>();
        String selectAllFromMysqlDatabase = "SELECT `household_id`, `resident_id`, `first_name`, `middle_name`, `last_name`, `gender`, `civil_status`, `birth_date`, `period`, `religion`, `disabled_person`, `fourps_status`, `HH_grantee`, `HH_relation`, `child_beneficiary`, `registered_voter`, `complete_address`, `father_name`, `father_occupation`, `mother_name`, `mother_occupation`, `number_of_siblings`, `birth_rank`, `image`, `image_view` FROM `residentsinfo_tbl` WHERE CONCAT(`household_id`, `resident_id`, `first_name`, `middle_name`, `last_name`, `gender`, `civil_status`, `birth_date`, `period`, `religion`, `disabled_person`, `fourps_status`, `HH_grantee`, `HH_relation`, `child_beneficiary`, `registered_voter`, `complete_address`, `father_name`, `father_occupation`, `mother_name`, `mother_occupation`, `number_of_siblings`, `birth_rank`, `image`, `image_view`) LIKE '%" + searchTerm + "%'";
        Statement statement = null;
        ResultSet resultset = null;
        residentsUIButtonUpdateDelete jCRUD;
        try {
            statement = connection.createStatement();
            resultset = statement.executeQuery(selectAllFromMysqlDatabase);
            while (resultset.next()) {
                jCRUD = new residentsUIButtonUpdateDelete();
                jCRUD.sethousehold_id(resultset.getInt("household_id"));
                jCRUD.setresident_id(resultset.getString("resident_id"));
                jCRUD.setfirst_name(resultset.getString("first_name"));
                jCRUD.setmiddle_name(resultset.getString("middle_name"));
                jCRUD.setlast_name(resultset.getString("last_name"));
                jCRUD.setgender(resultset.getString("gender"));
                jCRUD.setcivil_status(resultset.getString("civil_status"));
                jCRUD.setbirth_date(resultset.getDate("birth_date"));
                jCRUD.setperiod(resultset.getString("period"));
                jCRUD.setreligion(resultset.getString("religion"));
                jCRUD.setdisabled_person(resultset.getString("disabled_person"));
                jCRUD.setfourps_status(resultset.getString("fourps_status"));
                jCRUD.setHH_grantee(resultset.getString("HH_grantee"));
                jCRUD.setHH_relation(resultset.getString("HH_relation"));
                jCRUD.setchild_beneficiary(resultset.getString("child_beneficiary"));
                jCRUD.setregistered_voter(resultset.getString("registered_voter"));
                jCRUD.setcomplete_address(resultset.getString("complete_address"));
                jCRUD.setfather_name(resultset.getString("father_name"));
                jCRUD.setfather_occupation(resultset.getString("father_occupation"));
                jCRUD.setmother_name(resultset.getString("mother_name"));
                jCRUD.setmother_occupation(resultset.getString("mother_occupation"));
                jCRUD.setnumber_of_siblings(resultset.getString("number_of_siblings"));
                jCRUD.setbirth_rank(resultset.getString("birth_rank"));
                jCRUD.setimage(resultset.getString("image"));
                jCRUD.setimage_view(resultset.getBytes("image_view"));
                crudlist.add(jCRUD);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException sQLException) {
            }
        }
        return crudlist;

    }

    public void populateJTableFromMysqlDatabase() {
        ArrayList<residentsUIButtonUpdateDelete> populateJTable = CRUDList("");
        //model = (DefaultTableModel) residentui_tbl.getModel();
        //CLEAR JTABLE BEFORE ADDING NEW DATA
        //model.setRowCount(0);
        String[] columns = new String[]{"household_id", "resident_id", "first_name", "middle_name", "last_name", "gender", "civil_status", "birth_date", "period", "religion", "disabled_person", "fourps_status", "HH_grantee", "HH_relation", "child_beneficiary", "registered_voter", "complete_address", "father_name", "father_occupation", "mother_name", "mother_occupation", "number_of_siblings", "birth_rank", "image", "image_view"};
        Object[][] obj = new Object[populateJTable.size()][25];

        for (int i = 0; i < populateJTable.size(); i++) {
            obj[i][0] = populateJTable.get(i).gethousehold_id();
            obj[i][1] = populateJTable.get(i).getresident_id();
            obj[i][2] = populateJTable.get(i).getfirst_name();
            obj[i][3] = populateJTable.get(i).getmiddle_name();
            obj[i][4] = populateJTable.get(i).getlast_name();
            obj[i][5] = populateJTable.get(i).getgender();
            obj[i][6] = populateJTable.get(i).getcivil_status();
            obj[i][7] = populateJTable.get(i).getbirth_date();
            obj[i][8] = populateJTable.get(i).getperiod();
            obj[i][9] = populateJTable.get(i).getreligion();
            obj[i][10] = populateJTable.get(i).getdisabled_person();
            obj[i][11] = populateJTable.get(i).getfourps_status();
            obj[i][12] = populateJTable.get(i).getHH_grantee();
            obj[i][13] = populateJTable.get(i).getHH_relation();
            obj[i][14] = populateJTable.get(i).getchild_beneficiary();
            obj[i][15] = populateJTable.get(i).getregistered_voter();
            obj[i][16] = populateJTable.get(i).getcomplete_address();
            obj[i][17] = populateJTable.get(i).getfather_name();
            obj[i][18] = populateJTable.get(i).getfather_occupation();
            obj[i][19] = populateJTable.get(i).getmother_name();
            obj[i][20] = populateJTable.get(i).getmother_occupation();
            obj[i][21] = populateJTable.get(i).getnumber_of_siblings();
            obj[i][22] = populateJTable.get(i).getbirth_rank();
            obj[i][23] = populateJTable.get(i).getimage();
            obj[i][24] = populateJTable.get(i).getimage_view();

            if (populateJTable.get(i).getimage_view() != null) {

                ImageIcon imageIc = new ImageIcon(populateJTable.get(i).getimage_view());
                Image image = imageIc.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon imageIc2 = new ImageIcon(image);
                obj[i][24] = imageIc2;
            } else {
                obj[i][24] = null;
            }

            //obj[i][24] = populateJTable.get(i).getimage_view();
            //model.addRow(obj);
        }
        CustomTableModel ctModel = new CustomTableModel(columns, obj);
        residentui_tbl.setModel(ctModel);
        residentui_tbl.setRowHeight(100);

        residentui_tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        residentui_tbl.getColumnModel().getColumn(0).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(1).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(2).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(3).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(4).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(5).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(6).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(7).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(8).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(9).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(10).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(11).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(12).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(13).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(14).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(15).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(16).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(17).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(18).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(19).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(20).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(21).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(22).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(23).setPreferredWidth(150);
        residentui_tbl.getColumnModel().getColumn(24).setPreferredWidth(150);

    }

    public void CurrentDateandTime() {
        try {
            Timer timer;
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Calendar cal1 = new GregorianCalendar();
                    Calendar cal2 = cal1.getInstance();

                    String date = new SimpleDateFormat("MMM '-' dd '-' YYYY EEE").format(Calendar.getInstance().getTime()).toUpperCase();
                    String time = new SimpleDateFormat("hh ':' mm ':' ss").format(Calendar.getInstance().getTime());

                    String am_pm;
                    if (cal1.get(Calendar.AM_PM) == 0) {
                        am_pm = " AM";
                    } else {
                        am_pm = " PM";
                    }
                    date_text.setText(date);
                    time_text.setText(time + am_pm);
                }
            });
            timer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        date_text = new javax.swing.JLabel();
        time_text = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        HH_id = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Resident_id = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        tf_FirstName = new javax.swing.JTextField();
        cb_civilstatus = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tf_Address = new javax.swing.JTextArea();
        tf_FatherName = new javax.swing.JTextField();
        tf_FatherOccupation = new javax.swing.JTextField();
        tf_MotherName = new javax.swing.JTextField();
        tf_MotherOccupation = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        resident_image = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        tf_ImagePath = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        tf_MiddleName = new javax.swing.JTextField();
        tf_LastName = new javax.swing.JTextField();
        jRadioButtonDPYes = new javax.swing.JRadioButton();
        jRadioButtonDPNo = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jRadioButtonMale = new javax.swing.JRadioButton();
        jRadioButtonFemale = new javax.swing.JRadioButton();
        jComboBoxNoofSiblings = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        residentui_tbl = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Residents");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        date_text.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        date_text.setForeground(new java.awt.Color(255, 255, 255));
        date_text.setText("Date");
        jPanel1.add(date_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 10, 160, -1));

        time_text.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        time_text.setForeground(new java.awt.Color(255, 255, 255));
        time_text.setText("Time");
        jPanel1.add(time_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 10, 120, -1));

        jLabel5.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Household ID :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, -1));

        HH_id.setBackground(new java.awt.Color(0, 153, 153));
        HH_id.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        HH_id.setEnabled(false);
        HH_id.setOpaque(false);
        jPanel1.add(HH_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 209, -1));

        jLabel6.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Resident ID :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, -1, -1));

        Resident_id.setBackground(new java.awt.Color(0, 153, 153));
        Resident_id.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        Resident_id.setOpaque(false);
        jPanel1.add(Resident_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, 209, -1));

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back_to_20px.png"))); // NOI18N
        jLabel38.setText("Back");
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel38MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel38MouseExited(evt);
            }
        });
        jPanel4.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 20));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -5, 1230, 40));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("First Name :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 37, -1, -1));

        jLabel8.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Household Grantee :");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 91, -1, -1));

        jLabel9.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Child Beneficiary :");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 154, -1, -1));

        jLabel10.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Birth Date :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 180, -1, -1));

        jLabel11.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Age as of Now :");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 236, -1, -1));

        jLabel12.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Household Relationship :");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(326, 122, -1, -1));

        jLabel13.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Gender :");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(89, 131, -1, -1));

        jLabel14.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Disabled Person :");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 32, -1, -1));

        jLabel15.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Registered Voter :");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 181, -1, -1));

        jLabel16.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("4ps Status :");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 62, -1, -1));

        jLabel17.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Religion :");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 291, -1, -1));

        jLabel18.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Complete Address :");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 207, -1, -1));

        jLabel19.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Civil Status :");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 157, -1, -1));

        jLabel20.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Personal Information");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel21.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Family Background");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(616, 0, -1, -1));

        jLabel22.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Father Name :");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(664, 32, -1, -1));

        jLabel23.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Father Occupation :");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(616, 61, -1, -1));

        jLabel24.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Mother Name :");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(666, 90, -1, -1));

        jLabel25.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Mother Occupation :");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 119, -1, -1));

        jLabel26.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Number of Siblings :");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(661, 148, -1, -1));

        jLabel27.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Birth Rank :");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(723, 177, -1, -1));

        tf_FirstName.setBackground(new java.awt.Color(0, 102, 102));
        tf_FirstName.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        tf_FirstName.setOpaque(false);
        jPanel2.add(tf_FirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 34, 205, -1));

        cb_civilstatus.setBackground(new java.awt.Color(0, 102, 102));
        cb_civilstatus.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        cb_civilstatus.setForeground(new java.awt.Color(255, 255, 255));
        cb_civilstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Single", "Married", "Widowed", "Divorced" }));
        cb_civilstatus.setOpaque(false);
        jPanel2.add(cb_civilstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 154, 151, -1));

        jComboBox3.setBackground(new java.awt.Color(0, 102, 102));
        jComboBox3.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Catholic", "Christian", "Islam", "Hindu" }));
        jComboBox3.setOpaque(false);
        jPanel2.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(156, 288, 166, -1));

        tf_Address.setColumns(20);
        tf_Address.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        tf_Address.setRows(5);
        tf_Address.setOpaque(false);
        jScrollPane1.setViewportView(tf_Address);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 213, 449, 69));

        tf_FatherName.setBackground(new java.awt.Color(0, 102, 102));
        tf_FatherName.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        tf_FatherName.setOpaque(false);
        jPanel2.add(tf_FatherName, new org.netbeans.lib.awtextra.AbsoluteConstraints(772, 29, 214, -1));

        tf_FatherOccupation.setBackground(new java.awt.Color(0, 102, 102));
        tf_FatherOccupation.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        tf_FatherOccupation.setOpaque(false);
        jPanel2.add(tf_FatherOccupation, new org.netbeans.lib.awtextra.AbsoluteConstraints(772, 58, 214, -1));

        tf_MotherName.setBackground(new java.awt.Color(0, 102, 102));
        tf_MotherName.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        tf_MotherName.setOpaque(false);
        jPanel2.add(tf_MotherName, new org.netbeans.lib.awtextra.AbsoluteConstraints(774, 87, 212, -1));

        tf_MotherOccupation.setBackground(new java.awt.Color(0, 102, 102));
        tf_MotherOccupation.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        tf_MotherOccupation.setOpaque(false);
        jPanel2.add(tf_MotherOccupation, new org.netbeans.lib.awtextra.AbsoluteConstraints(774, 116, 212, -1));

        resident_image.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resident_image, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resident_image, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1004, 32, -1, -1));

        jLabel28.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Resident Image");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(1035, 11, -1, -1));

        jButton2.setBackground(new java.awt.Color(0, 102, 102));
        jButton2.setFont(new java.awt.Font("Consolas", 1, 10)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Take Picture");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1023, 261, 135, -1));

        jLabel30.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("00");
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(191, 236, -1, -1));

        jLabel31.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Years Old");
        jPanel2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 236, -1, -1));

        jButton6.setBackground(new java.awt.Color(0, 102, 102));
        jButton6.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Calculate Age");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(169, 259, 153, -1));

        jButton7.setBackground(new java.awt.Color(0, 102, 102));
        jButton7.setFont(new java.awt.Font("Consolas", 1, 10)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Upload Image");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1023, 234, 135, -1));

        jButton8.setBackground(new java.awt.Color(0, 102, 102));
        jButton8.setFont(new java.awt.Font("Consolas", 1, 10)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Select Image File");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1023, 207, -1, -1));

        jButton9.setBackground(new java.awt.Color(0, 102, 102));
        jButton9.setFont(new java.awt.Font("Consolas", 1, 10)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Open WebCam");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1023, 290, 135, -1));

        jLabel29.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Image Path :");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(449, 293, -1, -1));

        tf_ImagePath.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        tf_ImagePath.setEnabled(false);
        tf_ImagePath.setOpaque(false);
        jPanel2.add(tf_ImagePath, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 290, 449, -1));

        jLabel33.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Middle Name :");
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 66, -1, -1));

        jLabel34.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Last Name :");
        jPanel2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 95, -1, -1));

        tf_MiddleName.setBackground(new java.awt.Color(0, 153, 153));
        tf_MiddleName.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        tf_MiddleName.setOpaque(false);
        jPanel2.add(tf_MiddleName, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 63, 205, -1));

        tf_LastName.setBackground(new java.awt.Color(0, 153, 153));
        tf_LastName.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        tf_LastName.setOpaque(false);
        jPanel2.add(tf_LastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 92, 205, -1));

        jRadioButtonDPYes.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup2.add(jRadioButtonDPYes);
        jRadioButtonDPYes.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButtonDPYes.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonDPYes.setText("Yes");
        jPanel2.add(jRadioButtonDPYes, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 28, -1, -1));

        jRadioButtonDPNo.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup2.add(jRadioButtonDPNo);
        jRadioButtonDPNo.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButtonDPNo.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonDPNo.setText("No");
        jPanel2.add(jRadioButtonDPNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 28, -1, -1));

        jRadioButton3.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup3.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButton3.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton3.setText("Yes");
        jPanel2.add(jRadioButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 58, -1, -1));

        jRadioButton4.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup3.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButton4.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton4.setText("No");
        jPanel2.add(jRadioButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 58, -1, -1));

        jRadioButton5.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup4.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButton5.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton5.setText("Yes");
        jPanel2.add(jRadioButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 87, -1, -1));

        jRadioButton6.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup4.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButton6.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton6.setText("No");
        jPanel2.add(jRadioButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 87, -1, -1));

        jRadioButton7.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup5.add(jRadioButton7);
        jRadioButton7.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButton7.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton7.setText("Yes");
        jPanel2.add(jRadioButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 118, -1, -1));

        jRadioButton8.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup5.add(jRadioButton8);
        jRadioButton8.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButton8.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton8.setText("No");
        jPanel2.add(jRadioButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 118, -1, -1));

        jRadioButton9.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup6.add(jRadioButton9);
        jRadioButton9.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButton9.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton9.setText("Yes");
        jPanel2.add(jRadioButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 150, -1, -1));

        jRadioButton10.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup6.add(jRadioButton10);
        jRadioButton10.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButton10.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton10.setText("No");
        jPanel2.add(jRadioButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 150, -1, -1));

        jRadioButton11.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup7.add(jRadioButton11);
        jRadioButton11.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButton11.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton11.setText("Yes");
        jPanel2.add(jRadioButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 177, -1, -1));

        jRadioButton12.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup7.add(jRadioButton12);
        jRadioButton12.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButton12.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton12.setText("No");
        jPanel2.add(jRadioButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 177, -1, -1));

        jRadioButtonMale.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup1.add(jRadioButtonMale);
        jRadioButtonMale.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButtonMale.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonMale.setText("Male");
        jPanel2.add(jRadioButtonMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 127, -1, -1));

        jRadioButtonFemale.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup1.add(jRadioButtonFemale);
        jRadioButtonFemale.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jRadioButtonFemale.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonFemale.setText("Female");
        jPanel2.add(jRadioButtonFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 127, -1, -1));

        jComboBoxNoofSiblings.setBackground(new java.awt.Color(0, 102, 102));
        jComboBoxNoofSiblings.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jComboBoxNoofSiblings.setForeground(new java.awt.Color(255, 255, 255));
        jComboBoxNoofSiblings.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        jPanel2.add(jComboBoxNoofSiblings, new org.netbeans.lib.awtextra.AbsoluteConstraints(825, 145, 114, -1));

        jComboBox1.setBackground(new java.awt.Color(0, 102, 102));
        jComboBox1.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(823, 174, 116, -1));

        jDateChooser2.setBackground(new java.awt.Color(0, 153, 153));
        jDateChooser2.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser2.setDateFormatString("MMM dd, yyyy");
        jDateChooser2.setOpaque(false);
        jDateChooser2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jDateChooser2MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jDateChooser2MouseReleased(evt);
            }
        });
        jDateChooser2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooser2KeyReleased(evt);
            }
        });
        jPanel2.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 180, 202, -1));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 1180, 10));

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Consolas", 1, 16)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear_formatting_30px.png"))); // NOI18N
        jLabel32.setText("Clear");
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel32MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel32MouseExited(evt);
            }
        });
        jPanel5.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 30));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 350, 100, 50));

        jPanel6.setBackground(new java.awt.Color(0, 102, 102));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setFont(new java.awt.Font("Consolas", 1, 16)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_30px.png"))); // NOI18N
        jLabel35.setText("Save / Add Resident");
        jLabel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel35MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel35MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel35MouseExited(evt);
            }
        });
        jPanel6.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 350, 230, 50));

        jPanel7.setBackground(new java.awt.Color(0, 102, 102));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setFont(new java.awt.Font("Consolas", 1, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update_user_30px.png"))); // NOI18N
        jLabel36.setText("Update Resident");
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel36MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel36MouseExited(evt);
            }
        });
        jPanel7.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 180, 30));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 350, 190, 50));

        jPanel8.setBackground(new java.awt.Color(0, 102, 102));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setFont(new java.awt.Font("Consolas", 1, 16)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_30px.png"))); // NOI18N
        jLabel37.setText("Delete Resident");
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel37MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel37MouseExited(evt);
            }
        });
        jPanel8.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 350, 190, 50));

        jLabel4.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Search Resident :");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        jTextField4.setBackground(new java.awt.Color(0, 153, 153));
        jTextField4.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        jTextField4.setOpaque(false);
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 160, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 38, 1230, 420));

        residentui_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Household ID", "Resident ID", "Full Name", "Middle Name", "Last Name", "Gender", "Civil Status", "Birth Date", "Age", "Religion", "Disabled", "4ps Status", "HH Grantee", "HH Relationship", "Child Beneficiary", "Registered Voter", "Complete Address", "Father Name", "Father Occupation", "Mother Name", "Mother Occupation", "Number of Siblings", "Birth Rank", "Image Path", "Resident Image"
            }
        ));
        residentui_tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                residentui_tblMouseClicked(evt);
            }
        });
        residentui_tbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                residentui_tblKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(residentui_tbl);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 1230, 260));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        
        int selectedJTableRow;
    private void residentui_tblMouseClicked(java.awt.event.MouseEvent evt) {                                            
        // TODO add your handling code here:

        int selectedJTableRow = residentui_tbl.getSelectedRow();

        selectedJTableRowView(selectedJTableRow);

    }                                           

    private void jDateChooser1MouseExited(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:

    }                                         

    private void jDateChooser1KeyReleased(java.awt.event.KeyEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MMMMMM-dd");
        String s = sf.format(jDateChooser2.getDate());

        String arr[] = s.split("-");
        int year = Integer.parseInt(arr[0]);

        Month month = (Month.valueOf(arr[1].toUpperCase()));
        int day = Integer.parseInt(arr[2]);

        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(year, month, day);

        Period p = Period.between(birthday, today);

        jLabel30.setText(Integer.toString(p.getYears()));

    }                                        

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String currentDirectoryPath = "C:\\Users\\Public\\Pictures\\Sample Pictures";
        JFileChooser imageFileChooser = new JFileChooser();
        imageFileChooser.setDialogTitle("CHOOSE IMAGE");
        //Choose only images
        FileNameExtensionFilter imageFNEF = new FileNameExtensionFilter("IMAGES", "png", "jpeg", "jpg");
        imageFileChooser.setFileFilter(imageFNEF);
        int imageChooser = imageFileChooser.showOpenDialog(null);
        if (imageChooser == JFileChooser.APPROVE_OPTION) {
            File imageFile = imageFileChooser.getSelectedFile();
            crudImageAbsolutePath = imageFile.getAbsolutePath();
            tf_ImagePath.setText(crudImageAbsolutePath);

            ImageIcon imageIcon = new ImageIcon(crudImageAbsolutePath);
            //Resize Image To Fit JLabel
            Image imageResize = imageIcon.getImage().getScaledInstance(resident_image.getWidth(), resident_image.getHeight(), Image.SCALE_SMOOTH);
            resident_image.setIcon(new ImageIcon(imageResize));

        }
    }                                        

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Thread wcam = new Thread() {
           
            @Override
            public void run() {

                CvCapture cap = opencv_highgui.cvCreateCameraCapture(0);
                opencv_highgui.cvSetCaptureProperty(cap, opencv_highgui.CV_CAP_PROP_FRAME_WIDTH, 600);
                opencv_highgui.cvSetCaptureProperty(cap, opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT, 600);

                IplImage gimg = opencv_highgui.cvQueryFrame(cap);

                while (resident_image.isVisible() && (gimg = opencv_highgui.cvQueryFrame(cap)) != null) {

                    BufferedImage bfmg = gimg.getBufferedImage();
                    Image dimg = bfmg.getScaledInstance(resident_image.getWidth(), resident_image.getHeight(), Image.SCALE_SMOOTH);

                    ImageIcon icon = new ImageIcon(dimg);
                    resident_image.setIcon(icon);

                }

            }
        
        };
        wcam.start();
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         

        OpenCVFrameGrabber oGrab = new OpenCVFrameGrabber(0);
        
        try {

            oGrab.start();
            IplImage image = oGrab.grab();

            if (image != null) {
                cvSaveImage("campimg.jpeg", image);

                File f = new File("C:\\Users\\UnknownUser\\Documents\\GitHub\\BIMS Project\\campimg.jpeg");

                String path = f.getPath();

                tf_ImagePath.setText(path);

            }

            BufferedImage img1 = image.getBufferedImage();
            Image dimg = img1.getScaledInstance(resident_image.getWidth(), resident_image.getHeight(),
                    Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(dimg);
            resident_image.setIcon(imageIcon);

            oGrab.stop();

        } catch (FrameGrabber.Exception e) {
        }


    }                                        

    private void residentui_tblKeyReleased(java.awt.event.KeyEvent evt) {                                           
        // TODO add your handling code here:
        selectedJTableRow = residentui_tbl.getSelectedRow();
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            selectedJTableRowView(selectedJTableRow);
        }
    }                                          

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {                                        
        CustomTableModel wow = (CustomTableModel) residentui_tbl.getModel();
        TableRowSorter<CustomTableModel> tr = new TableRowSorter<>(wow);
        residentui_tbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(jTextField4.getText().trim()));
    }                                       

    private void jDateChooser2KeyReleased(java.awt.event.KeyEvent evt) {                                          

    }                                         

    private void jDateChooser2MouseExited(java.awt.event.MouseEvent evt) {                                          

    }                                         

    private void jDateChooser2MouseReleased(java.awt.event.MouseEvent evt) {                                            
        // TODO add your handling code here:
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MMMMMM-dd");
        String s = sf.format(jDateChooser2.getDate());
        String arr[] = s.split("-");
        int year = Integer.parseInt(arr[0]);
        Month month = (Month.valueOf(arr[1].toUpperCase()));
        int day = Integer.parseInt(arr[2]);
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(year, month, day);
        Period p = Period.between(birthday, today);
        jLabel30.setText(Integer.toString(p.getYears()));
    }                                           

    private void jLabel32MouseEntered(java.awt.event.MouseEvent evt) {                                      
        Color clr = new Color(0, 51, 51);
        jPanel5.setBackground(clr);
    }                                     

    private void jLabel32MouseExited(java.awt.event.MouseEvent evt) {                                     
        Color clr = new Color(0, 102, 102);
        jPanel5.setBackground(clr);
    }                                    

    private void jLabel35MouseEntered(java.awt.event.MouseEvent evt) {                                      
        Color clr = new Color(0, 51, 51);
        jPanel6.setBackground(clr);
    }                                     

    private void jLabel35MouseExited(java.awt.event.MouseEvent evt) {                                     
        Color clr = new Color(0, 102, 102);
        jPanel6.setBackground(clr);
    }                                    

    private void jLabel36MouseEntered(java.awt.event.MouseEvent evt) {                                      
        Color clr = new Color(0, 51, 51);
        jPanel7.setBackground(clr);
    }                                     

    private void jLabel36MouseExited(java.awt.event.MouseEvent evt) {                                     
        Color clr = new Color(0, 102, 102);
        jPanel7.setBackground(clr);
    }                                    

    private void jLabel37MouseEntered(java.awt.event.MouseEvent evt) {                                      
        Color clr = new Color(0, 51, 51);
        jPanel8.setBackground(clr);
    }                                     

    private void jLabel37MouseExited(java.awt.event.MouseEvent evt) {                                     
        Color clr = new Color(0, 102, 102);
        jPanel8.setBackground(clr);
    }                                    

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {                                      
        clearAllInputFields();
    }                                     

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {                                      
        int selectedJtableRow = residentui_tbl.getSelectedRow();

        if (selectedJtableRow == -1) {
            JOptionPane.showMessageDialog(null, "Please Select Row");
        } else {

            String deleSQLQuery = "DELETE FROM residentsinfo_tbl WHERE household_id = ?";
            PreparedStatement preparedstatement = null;

            try {
                preparedstatement = connection.prepareStatement(deleSQLQuery);
                preparedstatement.setInt(1, Integer.parseInt(HH_id.getText()));
                int deletedRow = preparedstatement.executeUpdate();
                if (deletedRow > 0) {
                    JOptionPane.showMessageDialog(null, "Row Deleted Successfully");
                    //Refresh JTable
                    populateJTableFromMysqlDatabase();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }                                     

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {                                      

        String ID = HH_id.getText();
        if (ID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fields is empty !");
        } else {
            String UpdateDataIntoMysqlDatabase = "UPDATE residentsinfo_tbl SET resident_id=?,first_name=?,middle_name=?,last_name=?,gender=?,civil_status=?,birth_date=?,period=?,religion=?,disabled_person=?,fourps_status=?,HH_grantee=?,HH_relation=?,child_beneficiary=?,registered_voter=?,complete_address=?,father_name=?,father_occupation=?,mother_name=?,mother_occupation=?,number_of_siblings=?,birth_rank=?,image=?,image_view=? WHERE household_id=?";
            PreparedStatement preparedStatement = null;
            InputStream imageIS = null;
            crudID = HH_id.getText();
            crudResidentID = Resident_id.getText();
            crudFirstName = tf_FirstName.getText();
            crudMiddleName = tf_MiddleName.getText();
            crudLastName = tf_LastName.getText();

            if (jRadioButtonMale.isSelected()) {
                crudGender = "Male";
            } else if (jRadioButtonFemale.isSelected()) {
                crudGender = "Female";
            }
            crudCivilStatus = cb_civilstatus.getSelectedItem().toString();
            if (jDateChooser2.getDate() != null) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                crudDate = dateFormat.format(jDateChooser2.getDate());

            } else {
                crudDate = "";
            }
            crudAge = jLabel30.getText();
            crudReligion = jComboBox3.getSelectedItem().toString();
            if (jRadioButtonDPYes.isSelected()) {
                crudDisabledPerson = "Yes";
            } else if (jRadioButtonDPNo.isSelected()) {
                crudDisabledPerson = "No";
            }
            if (jRadioButton3.isSelected()) {
                crudfourpsStatus = "Yes";
            } else if (jRadioButton4.isSelected()) {
                crudfourpsStatus = "No";
            }
            if (jRadioButton5.isSelected()) {
                crudHHGrantee = "Yes";
            } else if (jRadioButton6.isSelected()) {
                crudHHGrantee = "No";
            }
            if (jRadioButton7.isSelected()) {
                crudHHRelation = "Yes";
            } else if (jRadioButton8.isSelected()) {
                crudHHRelation = "No";
            }
            if (jRadioButton9.isSelected()) {
                crudChildBeneficiary = "Yes";
            } else if (jRadioButton10.isSelected()) {
                crudChildBeneficiary = "No";
            }
            if (jRadioButton11.isSelected()) {
                crudRegisteredVoter = "Yes";
            } else if (jRadioButton12.isSelected()) {
                crudRegisteredVoter = "No";
            }
            crudFatherName = tf_FatherName.getText();
            crudFatherOccupation = tf_FatherOccupation.getText();
            crudMotherName = tf_MotherName.getText();
            crudMotherOccupation = tf_MotherOccupation.getText();
            crudAddress = tf_Address.getText();
            /*File f
                = new File("C:\\Users\\Gay-Gay\\Documents\\NetBeansProjects\\thesis\\campimg.jpeg");
  
            String path = f.getPath();
            tf_ImagePath.setText(path);*/
            crudImagePath = tf_ImagePath.getText();
            crudNumberofSiblings = jComboBoxNoofSiblings.getSelectedItem().toString();
            crudBirthRank = jComboBox1.getSelectedItem().toString();

            try {
                imageIS = new FileInputStream(new File(crudImagePath));
                preparedStatement = connection.prepareStatement(UpdateDataIntoMysqlDatabase);
                preparedStatement.setString(1, crudResidentID);
                preparedStatement.setString(2, crudFirstName);
                preparedStatement.setString(3, crudMiddleName);
                preparedStatement.setString(4, crudLastName);
                preparedStatement.setString(5, crudGender);
                preparedStatement.setString(6, crudCivilStatus);
                preparedStatement.setString(7, crudDate);
                preparedStatement.setString(8, crudAge);
                preparedStatement.setString(9, crudReligion);
                preparedStatement.setString(10, crudDisabledPerson);
                preparedStatement.setString(11, crudfourpsStatus);
                preparedStatement.setString(12, crudHHGrantee);
                preparedStatement.setString(13, crudHHRelation);
                preparedStatement.setString(14, crudChildBeneficiary);
                preparedStatement.setString(15, crudRegisteredVoter);
                preparedStatement.setString(16, crudFatherName);
                preparedStatement.setString(17, crudFatherOccupation);
                preparedStatement.setString(18, crudMotherName);
                preparedStatement.setString(19, crudMotherOccupation);
                preparedStatement.setString(20, crudAddress);
                preparedStatement.setString(21, crudNumberofSiblings);
                preparedStatement.setString(22, crudBirthRank);
                preparedStatement.setString(23, crudImagePath);
                preparedStatement.setBlob(24, imageIS);
                preparedStatement.setInt(25, Integer.parseInt(crudID));
                int dataInserted = preparedStatement.executeUpdate();
                if (dataInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Update Success");
                    //REFRESH JTABLE AFTER INSERTING NEW ROW TO THE DATABASE
                    populateJTableFromMysqlDatabase();

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(residentsUIButton.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    } else {
                    }
                    if (imageIS != null) {
                        imageIS.close();

                    }
                } catch (SQLException | IOException sQLException) {

                }
            }
        }

    }                                     

    private void jLabel35MouseClicked(java.awt.event.MouseEvent evt) {                                      
        String insertDataIntoMysqlDatabase = "INSERT INTO `residentsinfo_tbl`( `resident_id`, `first_name`, `middle_name`, `last_name`, `gender`, `civil_status`, `birth_date`, `period`, `religion`, `disabled_person`, `fourps_status`, `HH_grantee`, `HH_relation`, `child_beneficiary`, `registered_voter`, `complete_address`, `father_name`, `father_occupation`, `mother_name`, `mother_occupation`, `number_of_siblings`, `birth_rank`, `image`, image_view) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        InputStream imageIS = null;
        crudResidentID = Resident_id.getText();
        crudFirstName = tf_FirstName.getText();
        crudMiddleName = tf_MiddleName.getText();
        crudLastName = tf_LastName.getText();

        if (jRadioButtonMale.isSelected()) {
            crudGender = "Male";
        } else if (jRadioButtonFemale.isSelected()) {
            crudGender = "Female";
        }
        crudCivilStatus = cb_civilstatus.getSelectedItem().toString();

        if (jDateChooser2.getDate() != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            crudDate = dateFormat.format(jDateChooser2.getDate());

        } else {
            crudDate = "";
        }

        crudAge = jLabel30.getText();
        crudReligion = jComboBox3.getSelectedItem().toString();
        if (jRadioButtonDPYes.isSelected()) {
            crudDisabledPerson = "Yes";
        } else if (jRadioButtonDPNo.isSelected()) {
            crudDisabledPerson = "No";
        }
        if (jRadioButton3.isSelected()) {
            crudfourpsStatus = "Yes";
        } else if (jRadioButton4.isSelected()) {
            crudfourpsStatus = "No";
        }
        if (jRadioButton5.isSelected()) {
            crudHHGrantee = "Yes";
        } else if (jRadioButton6.isSelected()) {
            crudHHGrantee = "No";
        }
        if (jRadioButton7.isSelected()) {
            crudHHRelation = "Yes";
        } else if (jRadioButton8.isSelected()) {
            crudHHRelation = "No";
        }
        if (jRadioButton9.isSelected()) {
            crudChildBeneficiary = "Yes";
        } else if (jRadioButton10.isSelected()) {
            crudChildBeneficiary = "No";
        }
        if (jRadioButton11.isSelected()) {
            crudRegisteredVoter = "Yes";
        } else if (jRadioButton12.isSelected()) {
            crudRegisteredVoter = "No";
        }
        crudAddress = tf_Address.getText();
        crudFatherName = tf_FatherName.getText();
        crudFatherOccupation = tf_FatherOccupation.getText();
        crudMotherName = tf_MotherName.getText();
        crudMotherOccupation = tf_MotherOccupation.getText();
        crudNumberofSiblings = jComboBoxNoofSiblings.getSelectedItem().toString();

        crudBirthRank = jComboBox1.getSelectedItem().toString();
        crudImageAbsolutePath = tf_ImagePath.getText();

        if (verifyfields()) {

            try {
                imageIS = new FileInputStream(new File(crudImageAbsolutePath));
                preparedStatement = connection.prepareStatement(insertDataIntoMysqlDatabase);
                preparedStatement.setString(1, crudResidentID);
                preparedStatement.setString(2, crudFirstName);
                preparedStatement.setString(3, crudMiddleName);
                preparedStatement.setString(4, crudLastName);
                preparedStatement.setString(5, crudGender);
                preparedStatement.setString(6, crudCivilStatus);
                preparedStatement.setString(7, crudDate);
                preparedStatement.setString(8, crudAge);
                preparedStatement.setString(9, crudReligion);
                preparedStatement.setString(10, crudDisabledPerson);
                preparedStatement.setString(11, crudfourpsStatus);
                preparedStatement.setString(12, crudHHGrantee);
                preparedStatement.setString(13, crudHHRelation);
                preparedStatement.setString(14, crudChildBeneficiary);
                preparedStatement.setString(15, crudRegisteredVoter);
                preparedStatement.setString(16, crudAddress);
                preparedStatement.setString(17, crudFatherName);
                preparedStatement.setString(18, crudFatherOccupation);
                preparedStatement.setString(19, crudMotherName);
                preparedStatement.setString(20, crudMotherOccupation);
                preparedStatement.setString(21, crudNumberofSiblings);
                preparedStatement.setString(22, crudBirthRank);
                preparedStatement.setString(23, crudImageAbsolutePath);

                preparedStatement.setBlob(24, imageIS);
                int dataInserted = preparedStatement.executeUpdate();
                if (dataInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Insert Success");

                    populateJTableFromMysqlDatabase();

                }

            } catch (SQLException | FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (imageIS != null) {
                        imageIS.close();

                    }
                } catch (SQLException | IOException sQLException) {

                }
            }
        }
    }                                     

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {                                      
        setVisible(false);
        new MainMenu().setVisible(true);
        this.dispose();
    }                                     

    private void jLabel38MouseEntered(java.awt.event.MouseEvent evt) {                                      
        Color clr = new Color(0, 51, 51);
        jPanel4.setBackground(clr);
    }                                     

    private void jLabel38MouseExited(java.awt.event.MouseEvent evt) {                                     
        Color clr = new Color(0, 153, 153);
        jPanel4.setBackground(clr);
    }                                    

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

 /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new residentsUIButton().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JTextField HH_id;
    private javax.swing.JTextField Resident_id;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.ButtonGroup buttonGroup9;
    private javax.swing.JComboBox<String> cb_civilstatus;
    private javax.swing.JLabel date_text;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBoxNoofSiblings;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JRadioButton jRadioButtonDPNo;
    private javax.swing.JRadioButton jRadioButtonDPYes;
    private javax.swing.JRadioButton jRadioButtonFemale;
    private javax.swing.JRadioButton jRadioButtonMale;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel resident_image;
    private javax.swing.JTable residentui_tbl;
    private javax.swing.JTextArea tf_Address;
    private javax.swing.JTextField tf_FatherName;
    private javax.swing.JTextField tf_FatherOccupation;
    private javax.swing.JTextField tf_FirstName;
    private javax.swing.JTextField tf_ImagePath;
    private javax.swing.JTextField tf_LastName;
    private javax.swing.JTextField tf_MiddleName;
    private javax.swing.JTextField tf_MotherName;
    private javax.swing.JTextField tf_MotherOccupation;
    private javax.swing.JLabel time_text;
    // End of variables declaration                   

    private void selectedJTableRowView(int i) {

        HH_id.setText(Integer.toString(CRUDList("").get(i).gethousehold_id()));

        Resident_id.setText(CRUDList("").get(i).getresident_id());

        tf_FirstName.setText(CRUDList("").get(i).getfirst_name());

        tf_MiddleName.setText(CRUDList("").get(i).getmiddle_name());

        tf_LastName.setText(CRUDList("").get(i).getlast_name());

        String gender = CRUDList("").get(i).getgender();
        if ("Male".equals(gender)) {
            jRadioButtonMale.setSelected(true);
        } else if ("Female".equals(gender)) {
            jRadioButtonFemale.setSelected(true);
        }

        String civil_status = CRUDList("").get(i).getcivil_status();
        for (int j = 0; j < cb_civilstatus.getItemCount(); j++) {
            if (cb_civilstatus.getItemAt(j).equalsIgnoreCase(civil_status)) {
                cb_civilstatus.setSelectedIndex(j);
            }
        }

        jLabel30.setText(CRUDList("").get(i).getperiod());

        Date dbDate;

        try {
            dbDate = new SimpleDateFormat("yyyy-mm-dd").parse(CRUDList("").get(i).getbirth_date().toString());
            jDateChooser2.setDate(dbDate);
        } catch (ParseException ex) {
            ex.getMessage();
        }

        String religion = CRUDList("").get(i).getreligion();
        for (int k = 0; k < jComboBox3.getItemCount(); k++) {
            if (jComboBox3.getItemAt(k).equalsIgnoreCase(religion)) {
                jComboBox3.setSelectedIndex(k);
            }
        }

        String disabled_person = CRUDList("").get(i).getgender();
        if ("Yes".equals(disabled_person)) {
            jRadioButtonDPYes.setSelected(true);
        } else if ("No".equals(disabled_person)) {
            jRadioButtonDPNo.setSelected(true);
        }

        String fourps_status = CRUDList("").get(i).getfourps_status();
        if ("Yes".equals(fourps_status)) {
            jRadioButton3.setSelected(true);
        } else if ("No".equals(fourps_status)) {
            jRadioButton4.setSelected(true);
        }

        String hhgrantee = CRUDList("").get(i).getHH_grantee();
        if ("Yes".equals(hhgrantee)) {
            jRadioButton5.setSelected(true);
        } else if ("No".equals(hhgrantee)) {
            jRadioButton6.setSelected(true);
        }

        String hhrelation = CRUDList("").get(i).getHH_relation();
        if ("Yes".equals(hhrelation)) {
            jRadioButton7.setSelected(true);
        } else if ("No".equals(hhrelation)) {
            jRadioButton8.setSelected(true);
        }

        String childbeneficiary = CRUDList("").get(i).getchild_beneficiary();
        if ("Yes".equals(childbeneficiary)) {
            jRadioButton9.setSelected(true);
        } else if ("No".equals(childbeneficiary)) {
            jRadioButton10.setSelected(true);
        }

        String regvoter = CRUDList("").get(i).getregistered_voter();
        if ("Yes".equals(regvoter)) {
            jRadioButton11.setSelected(true);
        } else if ("No".equals(regvoter)) {
            jRadioButton12.setSelected(true);
        }

        tf_Address.setText(CRUDList("").get(i).getcomplete_address());

        tf_FatherName.setText(CRUDList("").get(i).getfather_name());

        tf_FatherOccupation.setText(CRUDList("").get(i).getfather_occupation());

        tf_MotherName.setText(CRUDList("").get(i).getmother_name());

        tf_MotherOccupation.setText(CRUDList("").get(i).getmother_occupation());

        String NoofSiblings = CRUDList("").get(i).getbirth_rank();
        for (int l = 0; l < jComboBoxNoofSiblings.getItemCount(); l++) {
            if (jComboBoxNoofSiblings.getItemAt(l).equalsIgnoreCase(NoofSiblings)) {
                jComboBoxNoofSiblings.setSelectedIndex(l);
            }
        }

        String birthrank = CRUDList("").get(i).getbirth_rank();
        for (int l = 0; l < jComboBox1.getItemCount(); l++) {
            if (jComboBox1.getItemAt(l).equalsIgnoreCase(birthrank)) {
                jComboBox1.setSelectedIndex(l);
            }
        }

        tf_ImagePath.setText(CRUDList("").get(i).getimage());

        resident_image.setIcon(new ImageIcon(new ImageIcon(CRUDList("").get(i).getimage_view()).getImage().getScaledInstance(resident_image.getWidth(), resident_image.getHeight(), Image.SCALE_SMOOTH)));

    }

    private void clearAllInputFields() {
        HH_id.setText("");
        Resident_id.setText("");
        tf_FirstName.setText("");
        tf_MiddleName.setText("");
        tf_LastName.setText("");
        buttonGroup1.clearSelection();
        cb_civilstatus.setSelectedIndex(0);
        jDateChooser2.setDate(null);
        jLabel30.setText("");
        jComboBox3.setSelectedIndex(0);
        buttonGroup2.clearSelection();
        buttonGroup3.clearSelection();
        buttonGroup4.clearSelection();
        buttonGroup5.clearSelection();
        buttonGroup6.clearSelection();
        buttonGroup7.clearSelection();
        tf_Address.setText("");
        tf_FatherName.setText("");
        tf_FatherOccupation.setText("");
        tf_MotherName.setText("");
        tf_MotherOccupation.setText("");
        jComboBoxNoofSiblings.setSelectedIndex(0);
        jComboBox1.setSelectedIndex(0);
        tf_ImagePath.setText("");
        resident_image.setIcon(null);

    }

    public boolean verifyfields() {
        crudResidentID = Resident_id.getText();
        crudFirstName = tf_FirstName.getText();
        crudMiddleName = tf_MiddleName.getText();
        crudLastName = tf_LastName.getText();

        if (jRadioButtonMale.isSelected()) {
            crudGender = "Male";
        } else if (jRadioButtonFemale.isSelected()) {
            crudGender = "Female";
        }
        crudCivilStatus = cb_civilstatus.getSelectedItem().toString();

        if (jDateChooser2.getDate() != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            crudDate = dateFormat.format(jDateChooser2.getDate());

        } else {
            crudDate = "";
        }

        crudAge = jLabel30.getText();
        crudReligion = jComboBox3.getSelectedItem().toString();
        if (jRadioButtonDPYes.isSelected()) {
            crudDisabledPerson = "Yes";
        } else if (jRadioButtonDPNo.isSelected()) {
            crudDisabledPerson = "No";
        }
        if (jRadioButton3.isSelected()) {
            crudfourpsStatus = "Yes";
        } else if (jRadioButton4.isSelected()) {
            crudfourpsStatus = "No";
        }
        if (jRadioButton5.isSelected()) {
            crudHHGrantee = "Yes";
        } else if (jRadioButton6.isSelected()) {
            crudHHGrantee = "No";
        }
        if (jRadioButton7.isSelected()) {
            crudHHRelation = "Yes";
        } else if (jRadioButton8.isSelected()) {
            crudHHRelation = "No";
        }
        if (jRadioButton9.isSelected()) {
            crudChildBeneficiary = "Yes";
        } else if (jRadioButton10.isSelected()) {
            crudChildBeneficiary = "No";
        }
        if (jRadioButton11.isSelected()) {
            crudRegisteredVoter = "Yes";
        } else if (jRadioButton12.isSelected()) {
            crudRegisteredVoter = "No";
        }
        crudAddress = tf_Address.getText();
        crudFatherName = tf_FatherName.getText();
        crudFatherOccupation = tf_FatherOccupation.getText();
        crudMotherName = tf_MotherName.getText();
        crudMotherOccupation = tf_MotherOccupation.getText();
        crudNumberofSiblings = jComboBoxNoofSiblings.getSelectedItem().toString();

        crudBirthRank = jComboBox1.getSelectedItem().toString();
        crudImageAbsolutePath = tf_ImagePath.getText();

        if (crudResidentID.isEmpty() || crudFirstName.isEmpty() || crudMiddleName.isEmpty() || crudLastName.isEmpty() || crudGender.isEmpty() || crudCivilStatus.isEmpty() || crudDate.isEmpty() || crudAge.isEmpty() || crudReligion.isEmpty() || crudDisabledPerson.isEmpty() || crudfourpsStatus.isEmpty() || crudHHGrantee.isEmpty() || crudHHRelation.isEmpty() || crudChildBeneficiary.isEmpty() || crudRegisteredVoter.isEmpty() || crudFatherName.isEmpty() || crudFatherOccupation.isEmpty() || crudMotherName.isEmpty() || crudMotherOccupation.isEmpty() || crudAddress.isEmpty() || crudImageAbsolutePath.isEmpty() || crudNumberofSiblings.isEmpty() || crudBirthRank.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill all Fields!!");
            return false;
        } else {
            return true;

        }
    }

}

