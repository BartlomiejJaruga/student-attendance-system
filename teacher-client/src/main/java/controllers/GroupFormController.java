package controllers;

import entities.StudentsGroup;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.AlertHandler;
import utils.ViewsNames;

import static apis.ApisController.studentsGroupsApiClient;
import static utils.AlertHandler.showAlert;
import static utils.SceneSwitcher.switchScene;

public class GroupFormController {

    @FXML
    private TextField groupNameField;

    @FXML
    private TextArea groupDescriptionField;

    @FXML
    protected void handleSaveButtonAction(javafx.event.ActionEvent event) {
        String name = groupNameField.getText();
        String description = groupDescriptionField.getText();

        if (name != null && !name.trim().isEmpty() && description != null && !description.trim().isEmpty()) {
            StudentsGroup newGroup = new StudentsGroup(name.trim(), description.trim(), 0);
            studentsGroupsApiClient.addGroup(newGroup);
            showAlert(Alert.AlertType.INFORMATION, AlertHandler.SUCCESS, "Dodano grupę");
            groupNameField.clear();
            groupDescriptionField.clear();
            switchScene(event, ViewsNames.FXML_MAIN_VIEW, ViewsNames.WINDOW_NAME_MAIN_VIEW);
        } else {
            showAlert(Alert.AlertType.WARNING, AlertHandler.FAILURE, AlertHandler.FILL_ALL_FIELDS_ALERT);
        }
    }

    @FXML
    protected void handleCancelButtonAction(javafx.event.ActionEvent event) {
        switchScene(event, ViewsNames.FXML_MAIN_VIEW, ViewsNames.WINDOW_NAME_MAIN_VIEW);
    }

}
