package catalog.domain;

import java.util.Arrays;
import java.util.List;

/**
 * @author radu.
 *
 */
public class Student
        extends BaseEntity<Long>{
    private String serialNumber;
    private String name;
    private int group;

    public Student() {
    }

    public Student(Long ID, String serialNumber, String name, int group) {
        this.setId(ID);
        this.serialNumber = serialNumber;
        this.name = name;
        this.group = group;
    }

    public Student(String string) {
        List<String> values = Arrays.asList(string.split(","));
        this.setId(Long.parseLong(values.get(0)));
        this.serialNumber = values.get(1);
        this.name = values.get(2);
        this.group = Integer.parseInt(values.get(3));
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Student student = (Student) other;

        if (group != student.group) return false;
        if (!serialNumber.equals(student.serialNumber)) return false;
        return name.equals(student.name);
    }

    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + group;
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", group=" + group +
                "} " + super.toString();
    }

    @Override
    public String toString(String separator) {
        return this.getId().toString() + separator
                + this.serialNumber.toString() + separator
                + this.name.toString() + separator
                + String.valueOf(this.group);

    }

    @Override
    public <Type extends BaseEntity<Long>> int compareTo(Type that, String attribute) throws IllegalAccessException {
        if(this == that) return 0;
        if (that == null || this.getClass() != that.getClass()) throw new IllegalAccessException("cannot compare different types");
        Student student = (Student) that;

        switch (attribute) {
            case "serialNumber": return this.serialNumber.compareTo(student.serialNumber);
            case "name": return this.name.compareTo(student.name);
            case "group": return this.group - student.group;
            default: throw new IllegalAccessException("invalid attribute");
        }
    }
}
