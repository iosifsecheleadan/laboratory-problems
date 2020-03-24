package catalog.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StudentProblem
        extends BaseEntity<Long> {
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
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        StudentProblem assignment = (StudentProblem) other;

        if (!this.problemID.equals(assignment.problemID)) return false;
        return this.studentID.equals(assignment.studentID);
    }

    @Override
    public String toString(String separator) {
        return this.getId().toString() + separator
                + this.studentID.toString() + separator
                + this.problemID.toString();
    }

    @Override
    public <Type extends BaseEntity<Long>> int compareTo(Type that, String attribute) throws IllegalAccessException {
        if(this == that) return 0;
        if (that == null || this.getClass() != that.getClass()) throw new IllegalAccessException("cannot compare different types");
        StudentProblem studentProblem = (StudentProblem) that;

        switch (attribute) {
            case "studentID": return (int) (this.studentID - studentProblem.studentID);
            case "problemID": return (int) (this.problemID - studentProblem.problemID);
            default: throw new IllegalAccessException("invalid attribute");
        }
    }
}
