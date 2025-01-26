package entities;

public class Term {

    private Long termId;
    private String termName;
    private Long termGroupId;
    private String termDate;
    private String termStartTime;
    private String termEndTime;
    private String termGroupName;

    public Term(String termName, Long termGroup, String termDate, String termStartTime, String termEndTime) {
        this.termName = termName;
        this.termGroupId = termGroup;
        this.termDate = termDate;
        this.termStartTime = termStartTime;
        this.termEndTime = termEndTime;
    }

    public Term() {
    }


    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Long getTermGroupId() {
        return termGroupId;
    }

    public void setTermGroupId(Long termGroupId) {
        this.termGroupId = termGroupId;
    }

    public String getTermDate() {
        return termDate;
    }

    public void setTermDate(String termDate) {
        this.termDate = termDate;
    }

    public String getTermStartTime() {
        return termStartTime;
    }

    public void setTermStartTime(String termStartTime) {
        this.termStartTime = termStartTime;
    }

    public String getTermEndTime() {
        return termEndTime;
    }

    public void setTermEndTime(String termEndTime) {
        this.termEndTime = termEndTime;
    }

    public void setTermGroupName(String termGroupName) {
        this.termGroupName = termGroupName;
    }

    public String getTermGroupName() {
        return termGroupName;
    }

    @Override
    public String toString() {
        return "[" + termName + "] " + termGroupId + " " + termStartTime + " " + termEndTime;
    }

}
