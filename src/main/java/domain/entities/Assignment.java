package domain.entities;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.List;

/**
 * Bridge class between Student and Problem
 * @see Student
 * @see Problem
 * @author vinczi
 */
public class Assignment
        extends BaseEntity<Long> {
    private Long studentID;
    private Long problemID;

    /**
     * Default Constructor
     */
    public Assignment() {}

    /**
     * Parametrized Constructor
     * @param ID Long
     * @param studentID Long
     * @param problemID Long
     */
    public Assignment(Long ID, Long studentID, Long problemID) {
        this.setId(ID);
        this.studentID = studentID;
        this.problemID = problemID;
    }

    /**
     * String Constructor
     * Assumes String is formatted like "ID,studentID,problemID".
     * @param string String
     * @throws IndexOutOfBoundsException If the String is not formatted correctly.
     * @throws NumberFormatException If the String does not contain appropriate values.
     */
    public Assignment(String string) throws IndexOutOfBoundsException, NumberFormatException{
        List<String> values = Arrays.asList(string.split(","));
        if (values.size() != 3) throw new IndexOutOfBoundsException("String not formatted accordingly");
        this.setId(Long.parseLong(values.get(0)));
        this.studentID = Long.parseLong(values.get(1));
        this.problemID = Long.parseLong(values.get(2));
    }

    /**
     * Get Student ID
     * @return Long
     */
    public Long getStudentID() {
        return this.studentID;
    }

    /**
     * Get Problem ID
     * @return Long
     */
    public Long getProblemID() {
        return this.problemID;
    }

    /**
     * Set Student ID
     * @param studentID ID
     */
    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    /**
     * Set Problem ID
     * @param problemID ID
     */
    public void setProblemID(Long problemID) {
        this.problemID = problemID;
    }

    /**
     * Check if Assignment is equal to given Object
     * @param that Object
     * @return true - If given Object is an Assignment and problem and student IDs are equal.
     * <br> false  - Otherwise
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        Assignment assignment = (Assignment) that;

        if (!this.problemID.equals(assignment.problemID)) return false;
        return this.studentID.equals(assignment.studentID);
    }

    /**
     * Get Assignment as XML formatted String
     * @return String
     */
    @Override
    public String toXML() {
        return "<Assignment>" +
                "<ID>" + Long.toString(this.getId()) + "</ID>\n" +
                "<studentID>" + Long.toString(this.getStudentID()) + "</studentID>\n" +
                "<problemID>" + Long.toString(this.getProblemID()) + "</problemID>\n" +
                "</Assignment>\n";
    }

    /**
     * Get Assignment as Element of given Document
     * @param document Document (org.w3c.dom.Document)
     * @return Element (org.w3c.dom.Element)
     */
    @Override
    public Element toXML(Document document) {
        Element newAssignment = document.createElement("Assignment");

        Element newID = document.createElement("ID");
        newID.appendChild(document.createTextNode(Long.toString(this.getId())));
        newAssignment.appendChild(newID);

        Element newStudentID = document.createElement("studentID");
        newStudentID.appendChild(document.createTextNode(Long.toString(this.getStudentID())));
        newAssignment.appendChild(newStudentID);

        Element newProblemID = document.createElement("problemID");
        newProblemID.appendChild(document.createTextNode(Long.toString(this.problemID)));
        newAssignment.appendChild(newProblemID);

        return newAssignment;
    }

    /**
     * Get Assignment String formatted with given separator
     * @param separator String
     * @return String
     */
    @Override
    public String toString(String separator) {
        return this.getId().toString() + separator
                + this.studentID.toString() + separator
                + this.problemID.toString();
    }

    /**
     * Get basic Assignment String
     * @return String
     */
    @Override
    public String toString() {
        return "Assignment{" +
                "studentID=" + studentID +
                ", problemID=" + problemID +
                '}';
    }
}
