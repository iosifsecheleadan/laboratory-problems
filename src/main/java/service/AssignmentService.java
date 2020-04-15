package service.blaa;

import domain.entities.Assignment;
import domain.entities.Problem;
import domain.entities.Student;
import repository.RepositoryException;

import java.util.Set;

public interface AssignmentService {

    public void addAssignment(Assignment assignment) throws RepositoryException;
    public void removeAssignment(Assignment assignment);
    public void updateAssignment(Assignment assignment);

    public void removeStudent(Student student);
    public void removeProblem(Problem problem);

    public Set<Assignment> getAllAssignments();
    public Set<Student> filterByProblem(Problem problem);
    public Set<Student> filterByProblem(int problemNumber);
    public Set<Problem> filterByStudent(String serialNumber);
    public Set<Problem> filterByStudent(Student student);

    public void close();

}
