package apis;

import entities.Attendance;
import org.springframework.http.ResponseEntity;

public class AttendancesApiClient extends ApiClient {

    private final String ATTENDANCES_URL = "/attendances";

    public void addAttendance(Attendance newAttendance) {
        String url = BASE_URL + ATTENDANCES_URL;
        ResponseEntity<Attendance> response = restTemplate.postForEntity(url, newAttendance, Attendance.class);
    }

    public Attendance[] getAttendances() {
        String url = BASE_URL + ATTENDANCES_URL;
        ResponseEntity<Attendance[]> response = restTemplate.getForEntity(url, Attendance[].class);
        return response.getBody();
    }

    public void updateAttendance(Attendance attendance) {
        String url = BASE_URL + ATTENDANCES_URL + "/" + attendance.getAttendanceId();
        restTemplate.put(url, attendance);
    }

}
