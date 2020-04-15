package domain.entities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.List;

/**
 * @author vinczi
 */
public class Problem
        extends BaseEntity<Long> {
    private int problemNumber;
    private String name;
    private String description;

    /**
     * Default Constructor
     */
    public Problem() {}

    /**
     * Parametrized Constructor
     * @param ID Long
     * @param problemNumber int
     * @param name String
     * @param description String
     */
    public Problem(Long ID, int problemNumber, String name, String description) {
        this.setId(ID);
        this.problemNumber = problemNumber;
        this.name = name;
        this.description = description;
    }

    /**
     * String Constructor
     * Assumes String is formatted like "ID,problemNumber,name,description".
     * @param string String
     * @throws IndexOutOfBoundsException If the String is not formatted correctly.
     * @throws NumberFormatException If the String does not contain appropriate values.
     */
    public Problem(String string) throws IndexOutOfBoundsException, NumberFormatException{
        List<String> values = Arrays.asList(string.split(","));
        if (values.size() != 4) throw new IndexOutOfBoundsException("String not formatted accordingly");
        this.setId(Long.parseLong(values.get(0)));
        this.problemNumber = Integer.parseInt(values.get(1));
        this.name = values.get(2);
        this.description = values.get(3);
    }

    /**
     * Get Problem number
     * @return int
     */
    public int getProblemNumber() { return this.problemNumber; }

    /**
     * Get Problem name
     * @return String
     */
    public String getName() { return this.name; }

    /**
     * Get Problem description
     * @return String
     */
    public String getDescription() { return this.description; }


    /**
     * Set Problem number
     * @param problemNumber int
     */
    public void setProblemNumber(int problemNumber) { this.problemNumber = problemNumber; }

    /**
     * Set Problem name
     * @param name String
     */
    public void setName(String name) { this.name = name; }

    /**
     * Set Problem description
     * @param description String
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Check if Problem is equal to given Object
     * @param that Object
     * @return true - If given Object is a Problem and problem number, name and description are equal.
     * <br> false - Otherwise
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        Problem problem = (Problem) that;

        if (this.problemNumber != problem.problemNumber) return false;
        if (!this.description.equals(problem.description)) return false;
        return this.name.equals(problem.name);
    }

    /**
     * Get Problem as XML formatted String
     * @return String
     */
    @Override
    public String toXML() {
        return "<LabProblem>" +
                "<ID>" + Long.toString(this.getId()) + "</ID>\n" +
                "<problemNumber>" + Integer.toString(this.getProblemNumber()) + "</problemNumber>\n" +
                "<name>" + this.getName() + "</name>\n" +
                "<description>" + this.getDescription() + "</description>\n" +
                "</LabProblem>\n";
    }

    /**
     * Get Problem as Element of given Document
     * @param document Document (org.w3c.dom.Document)
     * @return Element (org.w3c.dom.Element)
     */
    @Override
    public Element toXML(Document document) {
        Element newLabProblem = (Element) document.createElement("LabProblem");

        Element newID = (Element) document.createElement("ID");
        newID.appendChild(document.createTextNode(Long.toString(this.getId())));
        newLabProblem.appendChild(newID);

        Element newProblemNumber = (Element) document.createElement("problemNumber");
        newProblemNumber.appendChild(document.createTextNode(Integer.toString(this.getProblemNumber())));
        newLabProblem.appendChild(newProblemNumber);

        Element newName = (Element) document.createElement("name");
        newName.appendChild(document.createTextNode(this.getName()));
        newLabProblem.appendChild(newName);

        Element newDescription = (Element) document.createElement("description");
        newDescription.appendChild(document.createTextNode(this.getDescription()));
        newLabProblem.appendChild(newDescription);

        return newLabProblem;
    }

    /**
     * Get Problem String formatted with given separator
     * @param separator String
     * @return String
     */
    @Override
    public String toString(String separator) {
        return this.getId().toString() + separator
                + String.valueOf(this.problemNumber) + separator
                + this.name.toString() + separator
                + this.description.toString();
    }

    /**
     * Get basic Problem String
     * @return String
     */
    @Override
    public String toString() {
        return "LabProblem{" +
                "problemNumber=" + problemNumber +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
