package catalog.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class StudentProblem extends BaseEntity<Long> {
    private Long studentID;
    private Long problemID;

    public StudentProblem() {}

    public StudentProblem(Long ID, Long studentID, Long problemID) {
        this.setId(ID);
        this.studentID = studentID;
        this.problemID = problemID;
    }

    public StudentProblem(String string) {
        List<String> values = Arrays.asList(string.split(","));
        this.setId(Long.parseLong(values.get(0)));
        this.studentID = Long.parseLong(values.get(1));
        this.problemID = Long.parseLong(values.get(2));
    }

    public Long getStudentID() {
        return this.studentID;
    }

    public Long getProblemID() {
        return this.problemID;
    }

    // todo : like student


    @Override
    public String toString(String separator) {
        return this.getId().toString() + separator
                + this.studentID.toString() + separator
                + this.problemID.toString();
    }
}
