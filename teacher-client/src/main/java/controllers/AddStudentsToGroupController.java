package controllers;

import entities.Student;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.ViewsNames;

import static apis.ApisController.studentsApiClient;
import static utils.SceneSwitcher.switchScene;

public class AddStudentsToGroupController {

    @FXML
    private TableView<Student> studentsTableView;

    @FXML
    private TableColumn<Student, String> studentIndex;

    @FXML
    private TableColumn<Student, String> studentName;

    @FXML
    private TableColumn<Student, String> studentSurname;

    static private Long groupId = null;

    static private String groupName = null;

    static public void setGroupId(Long newGroupId) {
        groupId = newGroupId;
    }

    static public void setGroupName(String newGroupName) {
        groupName = newGroupName;
    }

    @FXML
    protected void initialize() {
        studentsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        studentIndex.setCellValueFactory(new PropertyValueFactory<>("studentIndex"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentSurname.setCellValueFactory(new PropertyValueFactory<>("studentSurname"));

        var students = studentsApiClient.getStudents();
        for (var student : students) {
            if (student.getStudentGroupId() == null) {
                studentsTableView.getItems().add(student);
            }
        }
    }

    @FXML
    protected void handleSaveButtonAction(javafx.event.ActionEvent event) {
        var studentsInTable = studentsTableView.getSelectionModel().getSelectedItems();

        for (var student : studentsInTable) {
            student.setStudentGroupId(groupId);
            student.setStudentGroupName(groupName);
            studentsApiClient.updateStudent(student);
        }

        switchScene(event, ViewsNames.FXML_MAIN_VIEW, ViewsNames.WINDOW_NAME_MAIN_VIEW);
    }

    @FXML
    protected void handleCancelButtonAction(javafx.event.ActionEvent event) {
        switchScene(event, ViewsNames.FXML_MAIN_VIEW, ViewsNames.WINDOW_NAME_MAIN_VIEW);
    }

}
