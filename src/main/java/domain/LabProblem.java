package domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.List;

public class LabProblem
        extends BaseEntity<Long> {
    private int problemNumber;
    private String name;
    private String description;

    public LabProblem(){}

    public LabProblem(Long ID, int problemNumber, String name, String description) {
        this.setId(ID);
        this.problemNumber = problemNumber;
        this.name = name;
        this.description = description;
    }

    public LabProblem(String string) {
        List<String> values = Arrays.asList(string.split(","));
        this.setId(Long.parseLong(values.get(0)));
        this.problemNumber = Integer.parseInt(values.get(1));
        this.name = values.get(2);
        this.description = values.get(3);
    }

    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public int getProblemNumber() {
        return this.problemNumber;
    }

    public void setName(String name) { this.name = name; }
    public void setProblemNumber(int problemNumber) { this.problemNumber = problemNumber; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        LabProblem problem = (LabProblem) other;

        if (this.problemNumber != problem.problemNumber) return false;
        if (!this.description.equals(problem.description)) return false;
        return this.name.equals(problem.name);
    }

    @Override
    public String toString() {
        return "LabProblem{" +
                "problemNumber=" + problemNumber +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public String toString(String separator) {
        return this.getId().toString() + separator
                + String.valueOf(this.problemNumber) + separator
                + this.name.toString() + separator
                + this.description.toString();
    }

    @Override
    public String toXML() {
        return "<LabProblem>" +
                "<ID>" + Long.toString(this.getId()) + "</ID>\n" +
                "<problemNumber>" + Integer.toString(this.getProblemNumber()) + "</problemNumber>\n" +
                "<name>" + this.getName() + "</name>\n" +
                "<description>" + this.getDescription() + "</description>\n" +
                "</LabProblem>\n";
    }

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
}
