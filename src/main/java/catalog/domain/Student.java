package catalog.domain;

import java.util.Arrays;
import java.util.List;

/**
 * @author radu.
 *
 */
public class Student extends BaseEntity<Long>{
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

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
}
