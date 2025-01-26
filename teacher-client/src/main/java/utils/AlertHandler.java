package utils;

import javafx.scene.control.Alert;


public class AlertHandler {

    public static String INVALID_INPUT_ALERT = "Niepoprawne dane";
    public static String FILL_ALL_FIELDS_ALERT = "Proszę wypełnić wszytkie pola";
    public static String FAILURE = "Porażka";
    public static String SUCCESS = "Sukces";

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
