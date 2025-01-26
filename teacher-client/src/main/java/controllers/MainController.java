package controllers;

import entities.Student;
import entities.StudentsGroup;
import entities.Term;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import utils.ViewsNames;

import java.util.Objects;

import static apis.ApisController.*;
import static utils.SceneSwitcher.switchScene;

public class MainController {

    // ------------------------------------- GROUPS -------------------------------------
    @FXML
    private TableView<StudentsGroup> studentsGroupsTableView;

    @FXML
    private TableColumn<StudentsGroup, String> groupName;

    @FXML
    private TableColumn<StudentsGroup, String> groupDescription;

    @FXML
    private TableColumn<StudentsGroup, Integer> groupNumberOfStudents;

    @FXML
    private TableColumn<StudentsGroup, Void> groupOperations;

    // ------------------------------------- STUDENTS -------------------------------------
    @FXML
    private TableView<Student> studentsTableView;

    @FXML
    private TableColumn<Student, String> studentIndex;

    @FXML
    private TableColumn<Student, String> studentName;

    @FXML
    private TableColumn<Student, String> studentSurname;

    @FXML
    private TableColumn<Student, String> studentGroupName;

    @FXML
    private TableColumn<Student, Void> studentOperations;

    // ------------------------------------- TERMS -------------------------------------
    @FXML
    private TableView<Term> termsTableView;

    @FXML
    private TableColumn<Term, String> termName;

    @FXML
    private TableColumn<Term, String> termGroupName;

    @FXML
    private TableColumn<Term, String> termDate;

    @FXML
    private TableColumn<Term, String> termStartTime;

    @FXML
    private TableColumn<Term, String> termEndTime;

    @FXML
    private TableColumn<Term, Void> termOperations;

    @FXML
    private TabPane mainTabPane;

    private static final int iconSize = 16;

    static private int lastSelectedTabIndex = 0;

    private void reloadStudentsGroupsTable() {
        studentsTableView.getItems().clear();

        var students = studentsApiClient.getStudents();
        var studentsGroups = studentsGroupsApiClient.getStudentsGroups();

        for (var student : students) {

            for (var studentsGroup : studentsGroups) {
                if (student.getStudentGroupId() == null) {
                    student.setStudentGroupName("Student nie należy do żadnej grupy");
                    break;
                }
                if (Objects.equals(student.getStudentGroupId(), studentsGroup.getGroupId())) {
                    student.setStudentGroupName(studentsGroup.getGroupName());
                    break;
                }
            }
            studentsTableView.getItems().add(student);
        }

        studentsGroupsTableView.getItems().clear();

        for (var studentsGroup : studentsGroups) {
            int numerOfStudents = 0;
            for (var student : students) {
                if (student.getStudentGroupId() != null && student.getStudentGroupId().equals(studentsGroup.getGroupId())) {
                    numerOfStudents++;
                }
            }
            studentsGroup.setGroupNumberOfStudents(numerOfStudents);
            studentsGroupsTableView.getItems().add(studentsGroup);
        }

        termsTableView.getItems().clear();

        var terms = termsApiClient.getTerms();
        for (var term : terms) {
            var studentsGroup = studentsGroupsApiClient.getStudentsGroupById(term.getTermGroupId());
            term.setTermGroupName(studentsGroup.getGroupName());
            termsTableView.getItems().add(term);
        }

    }

    @FXML
    public void initialize() {
        // ustawianie ostatnio otworzonego panelu
        mainTabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            lastSelectedTabIndex = newValue.intValue();
        });

        mainTabPane.getSelectionModel().select(lastSelectedTabIndex);

        // -------------------------------- mapowanie do gui --------------------------------

        // Mapowanie kolumn na właściwości klasy Group
        groupName.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        groupDescription.setCellValueFactory(new PropertyValueFactory<>("groupDescription"));
        groupNumberOfStudents.setCellValueFactory(new PropertyValueFactory<>("groupNumberOfStudents"));

        // Mapowanie kolumn na właściwości klasy Student
        studentIndex.setCellValueFactory(new PropertyValueFactory<>("studentIndex"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentSurname.setCellValueFactory(new PropertyValueFactory<>("studentSurname"));
        studentGroupName.setCellValueFactory(new PropertyValueFactory<>("studentGroupName"));

        // Mapowanie kolumn na właściwości klasy Term
        termName.setCellValueFactory(new PropertyValueFactory<>("termName"));
        termGroupName.setCellValueFactory(new PropertyValueFactory<>("termGroupName"));
        termDate.setCellValueFactory(new PropertyValueFactory<>("termDate"));
        termStartTime.setCellValueFactory(new PropertyValueFactory<>("termStartTime"));
        termEndTime.setCellValueFactory(new PropertyValueFactory<>("termEndTime"));


        // -------------------------------- pobieranie danych z bazy --------------------------------

        reloadStudentsGroupsTable();

        // -------------------------------- dodawanie przyciskow --------------------------------
        addButtonsToGroupsTable();
        addButtonsToStudentsTable();
        addButtonsToTermsTable();
    }

    private void addButtonsToGroupsTable() {
        Callback<TableColumn<StudentsGroup, Void>, TableCell<StudentsGroup, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<StudentsGroup, Void> call(final TableColumn<StudentsGroup, Void> param) {
                return new TableCell<>() {
                    private final Button deleteGroupButton = new Button();
                    private final Button addStudentsButton = new Button();

                    {
                        ImageView addStudentsIcon = new ImageView(new Image(getClass().getResource("/icons/user.png").toExternalForm()));
                        addStudentsIcon.setFitWidth(MainController.iconSize);
                        addStudentsIcon.setFitHeight(iconSize);
                        addStudentsButton.setGraphic(addStudentsIcon);
                        Tooltip addStudentsButtonTooltip = new Tooltip("Dodaj studentów do grupy");
                        addStudentsButtonTooltip.setShowDelay(javafx.util.Duration.millis(500));
                        addStudentsButtonTooltip.setHideDelay(javafx.util.Duration.millis(200));
                        addStudentsButton.setTooltip(addStudentsButtonTooltip);

                        ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/icons/remove.png").toExternalForm()));
                        deleteIcon.setFitWidth(MainController.iconSize);
                        deleteIcon.setFitHeight(iconSize);
                        deleteGroupButton.setGraphic(deleteIcon);
                        Tooltip deleteGroupButtontooltip = new Tooltip("Usuń grupę");
                        deleteGroupButtontooltip.setShowDelay(javafx.util.Duration.millis(500));
                        deleteGroupButtontooltip.setHideDelay(javafx.util.Duration.millis(200));
                        deleteGroupButton.setTooltip(deleteGroupButtontooltip);

                        deleteGroupButton.setOnAction(event -> {
                            StudentsGroup data = getTableView().getItems().get(getIndex());
                            studentsGroupsApiClient.deleteStudentsGroup(data.getGroupId());
                            getTableView().getItems().remove(data);
                        });

                        addStudentsButton.setOnAction(event -> {
                            StudentsGroup data = getTableView().getItems().get(getIndex());
                            AddStudentsToGroupController.setGroupId(data.getGroupId());
                            AddStudentsToGroupController.setGroupName(data.getGroupName());
                            switchScene(event, ViewsNames.FXML_ADD_STUDENTS_TO_GROUP_VIEW, ViewsNames.WINDOW_NAME_ADD_STUDENTS_TO_GROUP_VIEW);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new javafx.scene.layout.HBox(5, addStudentsButton, deleteGroupButton));
                        }
                    }
                };
            }
        };

        groupOperations.setCellFactory(cellFactory);
    }

    private void addButtonsToStudentsTable() {
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Student, Void> call(final TableColumn<Student, Void> param) {
                return new TableCell<>() {
                    private final Button removeStudentFromGroupButton = new Button();
                    private final Button deleteStudentButton = new Button();

                    {
                        ImageView removeStudentFromGroupIcon = new ImageView(new Image(getClass().getResource("/icons/remove_from_group.png").toExternalForm()));
                        removeStudentFromGroupIcon.setFitWidth(iconSize);
                        removeStudentFromGroupIcon.setFitHeight(iconSize);
                        removeStudentFromGroupButton.setGraphic(removeStudentFromGroupIcon);
                        Tooltip removeStudentFromGroupButtontooltip = new Tooltip("Usuń studenta z grupy");
                        removeStudentFromGroupButtontooltip.setShowDelay(javafx.util.Duration.millis(500));
                        removeStudentFromGroupButtontooltip.setHideDelay(javafx.util.Duration.millis(200));
                        removeStudentFromGroupButton.setTooltip(removeStudentFromGroupButtontooltip);

                        ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/icons/remove.png").toExternalForm()));
                        deleteIcon.setFitWidth(iconSize);
                        deleteIcon.setFitHeight(iconSize);
                        deleteStudentButton.setGraphic(deleteIcon);
                        Tooltip deleteStudentButtontooltip = new Tooltip("Usuń studenta");
                        deleteStudentButtontooltip.setShowDelay(javafx.util.Duration.millis(500));
                        deleteStudentButtontooltip.setHideDelay(javafx.util.Duration.millis(200));
                        deleteStudentButton.setTooltip(deleteStudentButtontooltip);

                        removeStudentFromGroupButton.setOnAction(event -> {
                            Student data = getTableView().getItems().get(getIndex());
                            data.setStudentGroupId(null);
                            studentsApiClient.updateStudent(data);
                            studentsTableView.refresh();
                            reloadStudentsGroupsTable();
                        });

                        deleteStudentButton.setOnAction(event -> {
                            Student data = getTableView().getItems().get(getIndex());
                            studentsApiClient.deleteStudent(data.getStudentIndex());
                            getTableView().getItems().remove(data);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new javafx.scene.layout.HBox(5, removeStudentFromGroupButton, deleteStudentButton));
                        }
                    }
                };
            }
        };

        studentOperations.setCellFactory(cellFactory);
    }

    private void addButtonsToTermsTable() {
        Callback<TableColumn<Term, Void>, TableCell<Term, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Term, Void> call(final TableColumn<Term, Void> param) {
                return new TableCell<>() {
                    private final Button setPresenceButton = new Button();
                    private final Button deleteTermButton = new Button();

                    {
                        ImageView setPresenceIcon = new ImageView(new Image(getClass().getResource("/icons/list.png").toExternalForm()));
                        setPresenceIcon.setFitWidth(MainController.iconSize);
                        setPresenceIcon.setFitHeight(iconSize);
                        setPresenceButton.setGraphic(setPresenceIcon);
                        Tooltip setPresenceButtontooltip = new Tooltip("Ustaw obecności dla terminu");
                        setPresenceButtontooltip.setShowDelay(javafx.util.Duration.millis(500));
                        setPresenceButtontooltip.setHideDelay(javafx.util.Duration.millis(200));
                        setPresenceButton.setTooltip(setPresenceButtontooltip);

                        ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/icons/remove.png").toExternalForm()));
                        deleteIcon.setFitWidth(MainController.iconSize);
                        deleteIcon.setFitHeight(iconSize);
                        deleteTermButton.setGraphic(deleteIcon);
                        Tooltip deleteTermButtontooltip = new Tooltip("Usuń termin");
                        deleteTermButtontooltip.setShowDelay(javafx.util.Duration.millis(500));
                        deleteTermButtontooltip.setHideDelay(javafx.util.Duration.millis(200));
                        deleteTermButton.setTooltip(deleteTermButtontooltip);

                        deleteTermButton.setOnAction(event -> {
                            Term data = getTableView().getItems().get(getIndex());
                            termsApiClient.deleteTerm(data.getTermId());
                            getTableView().getItems().remove(data);
                        });

                        setPresenceButton.setOnAction(event -> {
                            Term data = getTableView().getItems().get(getIndex());
                            SetTermAttendanceController.setTermId(data.getTermId());
                            switchScene(event, ViewsNames.FXML_SET_TERM_PRESENCE_VIEW, ViewsNames.WINDOW_NAME_SET_TERM_PRESENCE_VIEW);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new javafx.scene.layout.HBox(5, setPresenceButton, deleteTermButton));
                        }
                    }
                };
            }
        };

        termOperations.setCellFactory(cellFactory);
    }

    @FXML
    protected void handleAddGroupButtonAction(javafx.event.ActionEvent event) {
        switchScene(event, ViewsNames.FXML_GROUP_FORM_VIEW, ViewsNames.WINDOW_NAME_GROUP_FORM_VIEW);
    }

    @FXML
    protected void handleAddStudentButtonAction(javafx.event.ActionEvent event) {
        switchScene(event, ViewsNames.FXML_STUDENT_FORM_VIEW, ViewsNames.WINDOW_NAME_STUDENT_FORM_VIEW);
    }

    @FXML
    protected void handleAddTermButtonAction(javafx.event.ActionEvent event) {
        switchScene(event, ViewsNames.FXML_TERM_FORM_VIEW, ViewsNames.WINDOW_NAME_TERM_FORM_VIEW);
    }

}