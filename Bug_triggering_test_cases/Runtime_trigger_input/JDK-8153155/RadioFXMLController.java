

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Salman on 16-Mar-16.
 */

public class RadioFXMLController {
    Main main = new Main();

    @FXML
    private Label radioLabel;

    @FXML
    private Label plName;

    @FXML
    private Label arName;

    @FXML
    Button buttonGenerate = new Button();

//    buttonGenerate.setOnAction(new MyButtonHandler());

    @FXML
    TextField playlistNameInsert = new TextField();     //User Input field for Playlist Name

    @FXML
    TextField artistNameInsert = new TextField();       //User Input field for Artist Name


    @FXML
    public void GotoRadio(ActionEvent event) throws IOException {   //Radio Button Pressed
        System.out.println("Radio button is pressed");
        radioLabel.setText("Radio Generator  ");
        plName.setText("Playlist Name : ");
        arName.setText("Artist Name : ");
        playlistNameInsert.setVisible(true);
        artistNameInsert.setVisible(true);
        buttonGenerate.setVisible(true);
//        main.IsConnected();     //prints whole db on terminal from main
    }


    @FXML
    public void saveRadio(ActionEvent event) throws IOException{    //Save Button Pressed
        SearchArtCreate();      //prints the genre of user input artist
    }

    public void SearchArtCreate(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:track.sqlite");
            if (conn == null){
                try {
                    System.out.println("Cannot connect to database");
                    conn.isClosed();
                    System.exit(1);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            else{       //successful run
                Statement artStmt;
                try{
                    conn.setAutoCommit(false);
                    System.out.println("Connection opened");

                    artStmt = conn.createStatement();
                    ResultSet rs = artStmt.executeQuery("SELECT * FROM tracklist WHERE artist = '" + artistNameInsert.getText() + "'");

                    //temp var for external use (works fine)
                    String storeGenre = "";
                    while(rs.next()){
                        String genre = rs.getString("genre");
                        storeGenre = genre;        // assigning genre to temp var
                    }
                    System.out.println(storeGenre);    //display check of genre of user input artist(non repetitive)


                    //creating table for radio playlist: (works fine)
                    String makeTable = "CREATE TABLE ' " + playlistNameInsert.getText() + " ' (id INT PRIMARY KEY NOT NULL," + "title TEXT NOT NULL," + "artist TEXT NOT NULL," +"genre TEXT NOT NULL," + "album TEXT NOT NULL)";
                    String storeTableName = playlistNameInsert.getText();

                    /*********** Main Logic | variable on hand: storeGenre = from user selected artist**************/
                    //setting up db for saving into playlist.db (works fine)
                    Class.forName("org.sqlite.JDBC");
                    Connection connect = DriverManager.getConnection("jdbc:sqlite:playlist.sqlite");
                    System.out.println("Playlist db opened");
                    Statement saveStatement = connect.createStatement();
                    saveStatement.executeUpdate(makeTable);

                    try {
                        ResultSet getAllSameGenre = artStmt.executeQuery("SELECT * FROM tracklist WHERE genre = '" + storeGenre + "' ORDER BY RANDOM() LIMIT 10");
                        Statement finalRecord = connect.createStatement();

                        String title = "";
                        String artist = "";
                        String genre = "";
                        String album = "";

                        while(getAllSameGenre.next()){
                            int id = 1;

                            title = getAllSameGenre.getString("title");
                            artist = getAllSameGenre.getString("artist");
                            genre = getAllSameGenre.getString("genre");
                            album = getAllSameGenre.getString("album");

                            System.out.println(title);
                            System.out.println(artist);      /*THESE FOUR PRINTS THE 10 RANDOM TRACKS */
                            System.out.println(genre);       /*FROM ResultSet getAllSameGenre */
                            System.out.println(album);

                            String record = "INSERT INTO " + storeTableName + " (id , title, artist , genre , album )" +  "VALUES ('" +id+","+title+","+artist+","+genre+","+album + "'); ";
                            finalRecord.executeUpdate(record);

                            id ++;
                        }
                        saveStatement.close();
                        connect.close();
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    /***********************************************************************************************/
                    rs.close();
                    artStmt.close();
                    conn.close();
                }
                catch (Exception e){
                    System.err.println(e.getClass().getName() + ":" + e.getMessage());
                    System.exit(0);
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}