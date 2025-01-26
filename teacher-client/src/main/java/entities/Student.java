package entities;

import java.io.Serializable;

public class Student implements Serializable {
    private String studentIndex;
    private String studentName;
    private String studentSurname;
    private Long studentGroupId;
    private String studentGroupName;

    public Student(String studentIndex, String studentName, String studentSurname, Long studentGroupId) {
        this.studentIndex = studentIndex;
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.studentGroupId = studentGroupId;
    }

    public Student() {
    }

    public String getStudentIndex() {
        return studentIndex;
    }

    public String getStudentGroupName() {
        return studentGroupName;
    }

    public void setStudentGroupName(String studentGroupName) {
        this.studentGroupName = studentGroupName;
    }

    public void setStudentIndex(String studentIndex) {
        this.studentIndex = studentIndex;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public Long getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(Long studentGroupId) {
        this.studentGroupId = studentGroupId;
    }

    @Override
    public String toString() {
        return studentIndex + " " + studentName + " " + studentSurname + " " + studentGroupId;
    }

}
