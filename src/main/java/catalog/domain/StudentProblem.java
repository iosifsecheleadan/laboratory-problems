package catalog.domain;

import org.w3c.dom.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class StudentProblem extends BaseEntity<Long> {
    private Long studentID;
    private Long problemID;

    public StudentProblem() {
    }

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

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public void setProblemID(Long problemID) {
        this.problemID = problemID;
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
    public String toXML() {
        return "<StudentProblem>" +
                "<ID>" + Long.toString(this.getId()) + "</ID>\n" +
                "<studentID>" + Long.toString(this.getStudentID()) + "</studentID>\n" +
                "<problemID>" + Long.toString(this.getProblemID()) + "</problemID>\n" +
                "</StudentProblem>\n";
    }

    @Override
    public Element toXML(Document document) {
        Element newStudentProblem = (Element) document.createElement("StudentProblem");

        Element newID = (Element) document.createElement("ID");
        newID.appendChild(document.createTextNode(Long.toString(this.getId())));
        newStudentProblem.appendChild(newID);

        Element newStudentID = (Element) document.createElement("studentID");
        newStudentID.appendChild(document.createTextNode(Long.toString(this.getStudentID())));
        newStudentProblem.appendChild(newStudentID);

        Element newProblemID = (Element) document.createElement("problemID");
        newProblemID.appendChild(document.createTextNode(Long.toString(this.problemID)));
        newStudentProblem.appendChild(newProblemID);

        return newStudentProblem;
    }
}
