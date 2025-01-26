package entities;

import java.io.Serializable;

public class StudentsGroup implements Serializable {

    private Long groupId;
    private String groupName;
    private String groupDescription;
    private Integer groupNumberOfStudents;

    public StudentsGroup(String groupName, String groupDescription, Integer groupNumberOfStudents) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupNumberOfStudents = groupNumberOfStudents;
    }

    public StudentsGroup() {
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Integer getGroupNumberOfStudents() {
        return groupNumberOfStudents;
    }

    public void setGroupNumberOfStudents(int groupNumberOfStudents) {
        this.groupNumberOfStudents = groupNumberOfStudents;
    }

    @Override
    public String toString() {
        return "[" + groupName + "] " + groupDescription + " (" + groupNumberOfStudents + ")";
    }
}
