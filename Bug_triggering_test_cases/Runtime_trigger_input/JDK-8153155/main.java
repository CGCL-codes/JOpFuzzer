import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Salman on 16-Mar-16.
 */

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws IOException{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Plookify");
        primaryStage.setResizable(false);

        MainView();
    }

    // FXML loader for RadioFXML.fxml
    public void MainView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("RadioFXML.fxml"));
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Make Connection
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:track.sqlite");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    //Verify Database Connection
    public void IsConnected(){
        Connection connect = Connector();
        if (connect == null){
            try {
                System.out.println("Cannot connect to database");
                connect.isClosed();
                System.exit(1);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Statement stmt;
            try {
                connect.setAutoCommit(false);
                System.out.println("Connection opened");

                stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM tracklist;");
                while(rs.next()){

                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String artist = rs.getString("artist");
                    String genre = rs.getString("genre");
                    String album = rs.getString("album");

                    System.out.println("id = " + id);
                    System.out.println("title = " + title);
                    System.out.println("artist = " + artist);
                    System.out.println("genre = " + genre);
                    System.out.println("album = " + album);
                }
                rs.close();
                stmt.close();
                connect.close();

            }
            catch (Exception e){
                System.err.println(e.getClass().getName() + ":" + e.getMessage());
                System.exit(0);
            }
        }
    }

    //Main method
    public static void main(String[] args) {
        launch(args);
    }


}



