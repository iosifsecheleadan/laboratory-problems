package catalog.domain;

public class StudentProblem extends BaseEntity<Long> {
    private Long studentID;
    private Long problemID;

    public Long getStudentID() {
        return this.studentID;
    }

    public Long getProblemID() {
        return this.problemID;
    }
    // todo : like student
    // todo : Long studentID
    // todo : Long problemID
}
