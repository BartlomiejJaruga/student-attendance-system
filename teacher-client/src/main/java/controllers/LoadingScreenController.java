package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import utils.ViewsNames;

import static apis.ApisController.studentsApiClient;
import static utils.SceneSwitcher.switchScene;

public class LoadingScreenController {

    @FXML
    private Label message;

    @FXML
    private Button reconnect;

    @FXML
    private void handleReconnectButtonAction(javafx.event.ActionEvent event) {
        try {
            studentsApiClient.getStudents();
            switchScene(event, ViewsNames.FXML_MAIN_VIEW, ViewsNames.WINDOW_NAME_MAIN_VIEW);
        } catch (Exception e) {
            message.setText("Failed to connect to the server, try connect again!");
            reconnect.setText("Reconnect");
        }
    }

}
