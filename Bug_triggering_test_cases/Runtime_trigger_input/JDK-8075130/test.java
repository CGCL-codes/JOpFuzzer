
package info.guenot.bm.device.client;

import info.guenot.bm.client.ClientScene;
import info.guenot.bm.client.ISearchMenuBuilder;
import info.guenot.bm.device.model.Device;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by herve on 11/02/15.
 */
public class TestCoreDumpClient extends Application {

    public static void main(String[] args) {
launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene clientScene = new Scene(new VBox());
        VBox vBox = (VBox) clientScene.getRoot();
        final TabPane contentPane = new TabPane();
        MenuBar menuBar = new MenuBar();
        Menu searchMenu = new Menu("Search");
        Menu deviceMenu = new Menu("Device");
        MenuItem find = new MenuItem("Find by code");
        find.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                final TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Find device by code");
                dialog.setHeaderText("Please enter device code you want to search.");
                dialog.setContentText("Device code:");

                // Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String code = result.get();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Device not found");
                    alert.setContentText("\""+code+"\" could not be found.");
                    alert.showAndWait();
                }
            }
        });
        deviceMenu.getItems().addAll(find);
        searchMenu.getItems().add(deviceMenu);
        menuBar.getMenus().addAll(searchMenu);
        vBox.getChildren().addAll(menuBar, contentPane);
        VBox.setVgrow(contentPane, Priority.ALWAYS);

        primaryStage.setScene(clientScene);
        primaryStage.setTitle("BM Client");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }
}

