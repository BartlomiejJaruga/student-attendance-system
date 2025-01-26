package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    static public void switchScene(javafx.event.ActionEvent event, String sceneFXMLName, String windowTitle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneSwitcher.class.getResource("/teacherclient/" + sceneFXMLName));
            Parent root = fxmlLoader.load();

            // Pobierz bieżący Stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Zapisz aktualne wymiary okna
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();

            // Ustaw nową scenę
            stage.setScene(new Scene(root));
            stage.setTitle(windowTitle);

            // Przywróć poprzedni rozmiar okna
            stage.setWidth(currentWidth);
            stage.setHeight(currentHeight);

            // Pokaż okno
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load view " + sceneFXMLName);
        }
    }

}
