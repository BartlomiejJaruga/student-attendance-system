package apis;

import entities.StudentsGroup;
import org.springframework.http.ResponseEntity;

public class StudentsGroupsApiClient extends ApiClient {

    private final String GROUPS_URL = "/groups";

    public void addGroup(StudentsGroup newStudentsGroup) {
        String url = BASE_URL + GROUPS_URL;
        ResponseEntity<StudentsGroup> response = restTemplate.postForEntity(url, newStudentsGroup, StudentsGroup.class);
    }

    public void deleteStudentsGroup(Long groupId) {
        String url = BASE_URL + GROUPS_URL + "/" + groupId;
        restTemplate.delete(url);
    }

    public StudentsGroup[] getStudentsGroups() {
        String url = BASE_URL + GROUPS_URL;
        ResponseEntity<StudentsGroup[]> response = restTemplate.getForEntity(url, StudentsGroup[].class);
        return response.getBody();
    }

    public StudentsGroup getStudentsGroupById(Long groupId) {
        String url = BASE_URL + GROUPS_URL + "/" + groupId;
        ResponseEntity<StudentsGroup> response = restTemplate.getForEntity(url, StudentsGroup.class);
        return response.getBody();
    }

}
