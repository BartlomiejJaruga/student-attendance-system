package controllers;

import entities.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import utils.ViewsNames;

import static apis.ApisController.studentsApiClient;
import static utils.AlertHandler.showAlert;
import static utils.SceneSwitcher.switchScene;

public class StudentFormController {

    @FXML
    private TextField studentIndexField;

    @FXML
    private TextField studentNameField;

    @FXML
    private TextField studentSurnameField;

    @FXML
    protected void handleSaveButtonAction(javafx.event.ActionEvent event) {
        String index = studentIndexField.getText();
        String name = studentNameField.getText();
        String surname = studentSurnameField.getText();

        if (index != null && !index.trim().isEmpty() && name != null && !name.trim().isEmpty() && surname != null && !surname.trim().isEmpty()) {
            Student newStudent = new Student(index, name, surname, null);
            studentsApiClient.addStudent(newStudent);
            showAlert(AlertType.INFORMATION, "Sukces", "Dodano studenta");
            studentIndexField.clear();
            studentNameField.clear();
            studentSurnameField.clear();
            switchScene(event, ViewsNames.FXML_MAIN_VIEW, ViewsNames.WINDOW_NAME_MAIN_VIEW);
        } else {
            showAlert(AlertType.WARNING, "Błąd", "Proszę wypełnić wszystkie pola.");
        }
    }

    @FXML
    protected void handleCancelButtonAction(javafx.event.ActionEvent event) {
        switchScene(event, ViewsNames.FXML_MAIN_VIEW, ViewsNames.WINDOW_NAME_MAIN_VIEW);
    }

}
