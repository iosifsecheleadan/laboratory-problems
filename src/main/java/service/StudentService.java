package service;

import domain.entities.Student;
import domain.validators.ValidatorException;

import java.util.Optional;
import java.util.Set;

/**
 * Interface for CRUD and filter operations on a Collection of Students
 * @see Student
 * @author sechelea
 */
public interface StudentService {
    /**
     * Add a Student
     * @param student Student
     * @throws ValidatorException If given Student is invalid
     */
    public void addStudent(Student student) throws ValidatorException;

    /**
     * Replace Student that has given Student's ID with given Student
     * @param student Student
     * @throws ValidatorException If given Student is invalid
     */
    public void updateStudent(Student student) throws ValidatorException;

    /**
     * Remove given Student
     * @param student Student
     */
    public void removeStudent(Student student);

    /**
     * Get All Students
     * @return Set
     */
    public Set<Student> getAllStudents();

    /**
     * Get All Students that have given string in their Name
     * @param name String
     * @return Set
     */
    public Set<Student> filterByName(String name);

    /**
     * Get All Students that have given Group
     * @param group int
     * @return Set
     */
    public Set<Student> filterByGroup(int group);

    /**
     * Get Student with given Serial Number
     * @param serialNumber String
     * @return Optional
     */
    public Optional<Student> getBySerialNumber(String serialNumber);

    /**
     * Close any connections, open files, etc...
     */
    public void close();
}
