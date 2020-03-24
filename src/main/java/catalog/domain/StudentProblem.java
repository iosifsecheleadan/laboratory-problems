package catalog.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StudentProblem<ID extends Comparable<ID>>
        extends BaseEntity<ID> {
    private ID studentID;
    private ID problemID;

    public StudentProblem() {}

    public StudentProblem(ID id, ID studentID, ID problemID) {
        this.setId(id);
        this.studentID = studentID;
        this.problemID = problemID;
    }

    public StudentProblem(String string) {
        List<String> values = Arrays.asList(string.split(","));
        this.setId((ID) values.get(0));
        this.studentID = (ID) values.get(1);
        this.problemID = (ID) values.get(2);
    }

    public ID getStudentID() {
        return this.studentID;
    }

    public ID getProblemID() {
        return this.problemID;
    }

    // todo : like student

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        StudentProblem<ID> assignment = (StudentProblem<ID>) other;

        if (!this.problemID.equals(assignment.problemID)) return false;
        return this.studentID.equals(assignment.studentID);
    }

    @Override
    public String toString() {
        return "StudentProblem{" +
                "studentID=" + studentID +
                ", problemID=" + problemID +
                '}';
    }

    @Override
    public String toString(String separator) {
        return this.getId().toString() + separator
                + this.studentID.toString() + separator
                + this.problemID.toString();
    }
}
