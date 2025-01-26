package teacherclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.ViewsNames;

import java.io.IOException;

public class MainApplication extends Application {

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(ViewsNames.FXML_CONNECTION_VIEW));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMaximized(true);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setTitle(ViewsNames.WINDOW_NAME_CONNECTION_VIEW);
        stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("/icons/app_icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}