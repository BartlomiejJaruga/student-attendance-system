package controllers;

import entities.Attendance;
import entities.Student;
import entities.StudentsGroup;
import entities.Term;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import utils.ViewsNames;

import java.util.ArrayList;
import java.util.Objects;

import static apis.ApisController.*;
import static utils.SceneSwitcher.switchScene;

public class SetTermAttendanceController {

    @FXML
    private TableView<Student> studentsTableView;

    @FXML
    private TableColumn<Student, String> studentIndex;

    @FXML
    private TableColumn<Student, String> studentName;

    @FXML
    private TableColumn<Student, String> studentSurname;

    @FXML
    private TableColumn<Student, Void> studentAttendance;

    private ArrayList<Attendance> termAttendances;

    private ArrayList<Attendance> tempTermAttendances;

    static private Long termId;

    @FXML
    private Label termNameLabel;

    @FXML
    private Label termGroupLabel;

    @FXML
    private Label termDateLabel;

    @FXML
    private Label termStartTimeLabel;

    @FXML
    private Label termEndTimeLabel;

    public static void setTermId(Long newTermId) {
        termId = newTermId;
    }

    @FXML
    protected void initialize() {

        var terms = termsApiClient.getTerms();
        Term currentTerm = null;
        for (var term : terms) {
            if (term.getTermId().equals(termId)) {
                currentTerm = term;
                break;
            }
        }
        // pobieramy grupe z id terminu rownym temu terminowi ktory teraz edytujemy
        StudentsGroup termGroup = studentsGroupsApiClient.getStudentsGroupById(currentTerm.getTermGroupId());

        termNameLabel.setText(currentTerm.getTermName());
        termGroupLabel.setText(termGroup.getGroupName());
        termDateLabel.setText(currentTerm.getTermDate());
        termStartTimeLabel.setText(currentTerm.getTermStartTime());
        termEndTimeLabel.setText(currentTerm.getTermEndTime());

        studentIndex.setCellValueFactory(new PropertyValueFactory<>("studentIndex"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentSurname.setCellValueFactory(new PropertyValueFactory<>("studentSurname"));

        termAttendances = new ArrayList<>();

        var students = studentsApiClient.getStudents();
        ArrayList<Student> termStudents = new ArrayList<>();

        tempTermAttendances = new ArrayList<>();

        for (var student : students) {
            if (Objects.equals(student.getStudentGroupId(), termGroup.getGroupId())) {
                termStudents.add(student);
                tempTermAttendances.add(new Attendance(student.getStudentIndex(), termGroup.getGroupId(), termId, null));
            }
        }

        var attendancesFromDatabase = attendancesApiClient.getAttendances();

        for (var attendanceFromDatabase : attendancesFromDatabase) {
            if (attendanceFromDatabase.getTermId().equals(termId)) {
                termAttendances.add(attendanceFromDatabase);
            }
        }

        for (var attendance : termAttendances) {
            for (var tempTermAttendance : tempTermAttendances) {
                if (Objects.equals(attendance.getStudentIndex(), tempTermAttendance.getStudentIndex())) {
                    tempTermAttendance.setAttendanceStatus(attendance.getAttendanceStatus());
                }
            }
        }

        for (var student : termStudents) {
            studentsTableView.getItems().add(student);
        }

        configurePresenceColumn();

    }

    private void configurePresenceColumn() {
        studentAttendance.setCellFactory(col -> new TableCell<>() {
            private final RadioButton presentButton = new RadioButton(Attendance.ATTENDANCE_STATUS_PRESENT);
            private final RadioButton absentButton = new RadioButton(Attendance.ATTENDANCE_STATUS_ABSENT);
            private final RadioButton excusedButton = new RadioButton(Attendance.ATTENDANCE_STATUS_EXCUSED);
            private final ToggleGroup presenceGroup = new ToggleGroup();

            {
                presentButton.setToggleGroup(presenceGroup);
                absentButton.setToggleGroup(presenceGroup);
                excusedButton.setToggleGroup(presenceGroup);

                // Listener zmiany zaznaczenia
                presenceGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                    Attendance attendance = getIndex() < tempTermAttendances.size() ? tempTermAttendances.get(getIndex()) : null;

                    if (newValue == presentButton) {
                        attendance.setAttendanceStatus(Attendance.ATTENDANCE_STATUS_PRESENT);
                        tempTermAttendances.get(getIndex()).setAttendanceStatus(Attendance.ATTENDANCE_STATUS_PRESENT);
                    } else if (newValue == absentButton) {
                        attendance.setAttendanceStatus(Attendance.ATTENDANCE_STATUS_ABSENT);
                        tempTermAttendances.get(getIndex()).setAttendanceStatus(Attendance.ATTENDANCE_STATUS_ABSENT);
                    } else if (newValue == excusedButton) {
                        attendance.setAttendanceStatus(Attendance.ATTENDANCE_STATUS_EXCUSED);
                        tempTermAttendances.get(getIndex()).setAttendanceStatus(Attendance.ATTENDANCE_STATUS_EXCUSED);
                    }
                });
            }


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {

                    Attendance attendance = getIndex() < tempTermAttendances.size() ? tempTermAttendances.get(getIndex()) : null;
                    if (attendance != null) {
                        String attendanceStatus = attendance.getAttendanceStatus();
                        if (attendanceStatus == null) {
                            presenceGroup.selectToggle(null);
                        } else {
                            switch (attendanceStatus) {
                                case Attendance.ATTENDANCE_STATUS_PRESENT:
                                    presentButton.setSelected(true);
                                    break;
                                case Attendance.ATTENDANCE_STATUS_ABSENT:
                                    absentButton.setSelected(true);
                                    break;
                                case Attendance.ATTENDANCE_STATUS_EXCUSED:
                                    excusedButton.setSelected(true);
                                    break;
                                default:
                                    presenceGroup.getSelectedToggle().setSelected(false);
                                    break;
                            }
                        }
                    } else {
                        presenceGroup.selectToggle(null);
                    }

                    setGraphic(new HBox(10, presentButton, absentButton, excusedButton));
                }
            }
        });
    }

    @FXML
    protected void handleSaveButtonAction(javafx.event.ActionEvent event) {
        for (var tempTermAttendance : tempTermAttendances) {
            boolean attendanceForStudentExists = false;
            for (var attendanceFromDatabase : termAttendances) {
                if (Objects.equals(tempTermAttendance.getStudentIndex(), attendanceFromDatabase.getStudentIndex())) {
                    attendanceForStudentExists = true;
                    tempTermAttendance.setAttendanceId(attendanceFromDatabase.getAttendanceId());
                    attendancesApiClient.updateAttendance(tempTermAttendance);
                    break;
                }
            }
            if (!attendanceForStudentExists) {
                attendancesApiClient.addAttendance(tempTermAttendance);
            }

        }
        switchScene(event, ViewsNames.FXML_MAIN_VIEW, ViewsNames.WINDOW_NAME_MAIN_VIEW);
    }

    @FXML
    protected void handleCancelButtonAction(javafx.event.ActionEvent event) {
        switchScene(event, ViewsNames.FXML_MAIN_VIEW, ViewsNames.WINDOW_NAME_MAIN_VIEW);
    }

}
