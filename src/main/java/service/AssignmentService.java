package service;

import domain.entities.Assignment;
import domain.entities.Problem;
import domain.entities.Student;
import domain.validators.ValidatorException;

import java.util.Set;

/**
 * Interface for CRUD and filter operations on a Collection of Assignments
 * @see Assignment
 * @author sechelea
 */
public interface AssignmentService {
    /**
     * Add an Assignment
     * @param assignment Assignment
     * @throws ValidatorException If given Assignment is invalid
     */
    public void addAssignment(Assignment assignment) throws ValidatorException;

    /**
     * Replace Assignment that has given Assignment's ID, with given Assignment
     * @param assignment Assignment
     * @throws ValidatorException If given Assignment is invalid
     */
    public void updateAssignment(Assignment assignment) throws ValidatorException;

    /**
     * Remove given Assignment
     * @param assignment Assignment
     */
    public void removeAssignment(Assignment assignment);

    /**
     * Remove all Assignments of given Student
     * <br> Remove given Student
     * @param student Student
     */
    public void removeStudent(Student student);

    /**
     * Remove all Assignments of given Problem
     * <br> Remove given Problem
     * @param problem Problem
     */
    public void removeProblem(Problem problem);

    /**
     * Get All Assignments
     * @return Set
     */
    public Set<Assignment> getAllAssignments();

    /**
     * Get All Students that are assigned given Problem
     * @param problem Problem
     * @return Set
     */
    public Set<Student> filterByProblem(Problem problem);

    /**
     * Get All Students that are assigned Problem with given number
     * @param problemNumber int
     * @return Set
     */
    public Set<Student> filterByProblem(int problemNumber);

    /**
     * Get All Problems that are assigned to given Student
     * @param student Student
     * @return Set
     */
    public Set<Problem> filterByStudent(Student student);

    /**
     * Get All Problems that are assigned to Student with given serial number
     * @param serialNumber String
     * @return Set
     */
    public Set<Problem> filterByStudent(String serialNumber);

    /**
     * Close any connections, open files, etc...
     */
    public void close();

}
