package domain.entities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.List;

/**
 * @author vinczi
 */
public class Student
        extends BaseEntity<Long>{
    private String serialNumber;
    private String name;
    private int group;

    /**
     * Default Constructor
     */
    public Student() {}

    /**
     * Parametrized Constructor
     * @param ID Long
     * @param serialNumber String
     * @param name String
     * @param group int
     */
    public Student(Long ID, String serialNumber, String name, int group) {
        this.setId(ID);
        this.serialNumber = serialNumber;
        this.name = name;
        this.group = group;
    }

    /**
     * String Constructor
     * Assumes String is formatted like "ID,serialNumber,name,group".
     * @param string String
     * @throws IndexOutOfBoundsException If the String is not formatted correctly.
     * @throws NumberFormatException If the String does not contain appropriate values.
     */
    public Student(String string) {
        List<String> values = Arrays.asList(string.split(","));
        if (values.size() != 4) throw new IndexOutOfBoundsException("String not formatted accordingly");
        this.setId(Long.parseLong(values.get(0)));
        this.serialNumber = values.get(1);
        this.name = values.get(2);
        this.group = Integer.parseInt(values.get(3));
    }

    /**
     * Get Student serial number
     * @return String
     */
    public String getSerialNumber() { return serialNumber; }

    /**
     * Get Student name
     * @return String
     */
    public String getName() { return name; }

    /**
     * Get Student group
     * @return int
     */
    public int getGroup() { return group; }

    /**
     * Set Student serial number
     * @param serialNumber String
     */
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    /**
     * Set Student name
     * @param name String
     */
    public void setName(String name) { this.name = name; }

    /**
     * Set Student group
     * @param group int
     */
    public void setGroup(int group) { this.group = group; }

    /**
     * Check if Student is equal to given Object
     * @param that Object
     * @return true - If given Object is a Student and student serial number, name and group are equal.
     * <br> false - Otherwise
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        Student student = (Student) that;

        if (group != student.group) return false;
        if (!serialNumber.equals(student.serialNumber)) return false;
        return name.equals(student.name);
    }

    /**
     * Get Student as XML formatted String
     * @return String
     */
    @Override
    public String toXML() {
        return "<Student>" +
                "<ID>" + Long.toString(this.getId()) + "</ID>\n" +
                "<serialNumber>" + this.getSerialNumber() + "</serialNumber>\n" +
                "<name>" + this.getName() + "</name>\n" +
                "<group>" + Integer.toString(getGroup()) + "</group>\n" +
                "</Student>\n";
    }

    /**
     * Get Student as Element of given Document
     * @param document Document (org.w3c.dom.Document)
     * @return Element (org.w3c.dom.Element)
     */
    @Override
    public Element toXML(Document document) {
        Element newStudent = (Element) document.createElement("Student");

        Element newID = (Element) document.createElement("ID");
        newID.appendChild(document.createTextNode(Long.toString(this.getId())));
        newStudent.appendChild(newID);

        Element newSerial = (Element) document.createElement("serialNumber");
        newSerial.appendChild(document.createTextNode(this.getSerialNumber()));
        newStudent.appendChild(newSerial);

        Element newName = (Element) document.createElement("name");
        newName.appendChild(document.createTextNode(this.getName()));
        newStudent.appendChild(newName);

        Element newGroup = (Element) document.createElement("group");
        newGroup.appendChild(document.createTextNode(Integer.toString(this.getGroup())));
        newStudent.appendChild(newGroup);

        return newStudent;
    }

    /**
     * Get Student String formatted with given separator
     * @param separator String
     * @return String
     */
    @Override
    public String toString(String separator) {
        return this.getId().toString() + separator
                + this.serialNumber.toString() + separator
                + this.name.toString() + separator
                + String.valueOf(this.group);

    }

    /**
     * Get basic Student String
     * @return String
     */
    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", group=" + group +
                "} " + super.toString();
    }
}
