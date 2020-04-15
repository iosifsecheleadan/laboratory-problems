package service.blaa;

import domain.entities.Student;
import domain.validators.ValidatorException;

import java.util.Optional;
import java.util.Set;

public interface StudentService {
    public void addStudent(Student student) throws ValidatorException;
    public void updateStudent(Student student) throws ValidatorException;
    public void removeStudent(Student student);

    public Set<Student> getAllStudents();
    public Set<Student> filterByName(String name);
    public Set<Student> filterByGroup(int group);
    public Optional<Student> getBySerialNumber(String serialNumber);

    public void close();
}
