package apis;

import entities.Student;
import org.springframework.http.ResponseEntity;


public class StudentsApiClient extends ApiClient {

    private final String STUDENTS_URL = "/students";

    public void addStudent(Student newStudent) {
        String url = BASE_URL + STUDENTS_URL;
        ResponseEntity<Student> response = restTemplate.postForEntity(url, newStudent, Student.class);
    }

    public void deleteStudent(String studentIndex) {
        String url = BASE_URL + STUDENTS_URL + "/" + studentIndex;
        restTemplate.delete(url);
    }

    public Student[] getStudents() {
        String url = BASE_URL + STUDENTS_URL;
        ResponseEntity<Student[]> response = restTemplate.getForEntity(url, Student[].class);
        return response.getBody();
    }

    public void updateStudent(Student student) {
        String url = BASE_URL + STUDENTS_URL + "/" + student.getStudentIndex();
        restTemplate.put(url, student);
    }

}
