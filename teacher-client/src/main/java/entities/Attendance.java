package entities;

public class Attendance {

    private Long attendanceId;
    private String studentIndex;
    private Long groupId;
    private Long termId;
    private String attendanceStatus;

    static public final String ATTENDANCE_STATUS_PRESENT = "Obecny";
    static public final String ATTENDANCE_STATUS_ABSENT = "Nieobecny";
    static public final String ATTENDANCE_STATUS_EXCUSED = "Usprawiedliwony";

    public Attendance(Long attendanceId, String studentIndex, Long groupId, Long termId, String attendanceStatus) {
        this.attendanceId = attendanceId;
        this.studentIndex = studentIndex;
        this.groupId = groupId;
        this.termId = termId;
        this.attendanceStatus = attendanceStatus;
    }

    public Attendance(String studentIndex, Long groupId, Long termId, String attendanceStatus) {
        this.studentIndex = studentIndex;
        this.groupId = groupId;
        this.termId = termId;
        this.attendanceStatus = attendanceStatus;
    }

    public Attendance() {
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getStudentIndex() {
        return studentIndex;
    }

    public void setStudentIndex(String studentIndex) {
        this.studentIndex = studentIndex;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public String toString() {
        return attendanceId + " " + studentIndex + " " + groupId + " " + termId + " " + attendanceStatus;
    }

}
