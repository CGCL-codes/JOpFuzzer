

import javafx.application.Application;
import javafx.stage.Stage;

public class CrashOnStart extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        throw new IllegalStateException("Argh!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

