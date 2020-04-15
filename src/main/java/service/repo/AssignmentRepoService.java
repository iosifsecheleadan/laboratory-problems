package service.repo;

import domain.entities.Problem;
import domain.entities.Student;
import domain.entities.Assignment;
import domain.validators.ValidatorException;
import repository.sort.GenericDataBaseRepository;
import repository.Repository;
import repository.RepositoryException;
import service.AssignmentService;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of AssignmentService with Repository as Collection
 * @author sechelea
 */
public class AssignmentRepoService
        implements AssignmentService {
    private Repository<Long, Assignment> repository;
    private Repository<Long, Student> studentRepository;
    private Repository<Long, Problem> problemRepository;

    /**
     * Parametrized Constructor
     * @param repository Assignment Repository
     * @param studentRepository Student Repository
     * @param problemRepository Problem Repository
     */
    public AssignmentRepoService(Repository<Long, Assignment> repository,
                                 Repository<Long, Student> studentRepository,
                                 Repository<Long, Problem> problemRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.problemRepository = problemRepository;
    }

    public void addAssignment(Assignment assignment) throws ValidatorException, RepositoryException {
        try {
            assert (this.studentRepository.findOne(assignment.getStudentID()).isPresent()); //  if student.isPresent
            assert (this.problemRepository.findOne(assignment.getProblemID()).isPresent()); //      and problem.isPresent
            this.repository.save(assignment);                                               //          save()
        } catch (AssertionError ignored) {                                                      //  else
            throw new RepositoryException("student or problem does not exist");                 //      throw Error
        }
    }

    public void updateAssignment(Assignment assignment) { this.repository.update(assignment); }

    public void removeAssignment(Assignment assignment) { this.repository.delete(assignment.getId()); }

    public void removeStudent(Student student) {
        Iterable<Assignment> assignments = this.repository.findAll();
        StreamSupport.stream(assignments.spliterator(), false)
                .filter(assignment -> this.repository.findOne(assignment.getId()).isPresent() &&
                        this.repository.findOne(assignment.getId()).get().getStudentID().equals(student.getId()))
                .forEach(assgnment -> this.repository.delete(assgnment.getId()));
    }

    public void removeProblem(Problem problem) {
        Iterable<Assignment> assignments = this.repository.findAll();
        StreamSupport.stream(assignments.spliterator(), false)
                .filter(assignment -> this.repository.findOne(assignment.getId()).isPresent() &&
                        this.repository.findOne(assignment.getId()).get().getProblemID().equals(problem.getId()))
                .forEach(assignment -> this.repository.delete(assignment.getId()));
    }

    public Set<Assignment> getAllAssignments() {
        Iterable<Assignment> studentProblems = this.repository.findAll();
        return StreamSupport.stream(studentProblems.spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Student> filterByProblem(Problem problem) {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .filter(assignment -> assignment.getProblemID().equals(problem.getId()))
                .map(assignment -> this.studentRepository.findOne(assignment.getStudentID()).isPresent() ?
                        this.studentRepository.findOne(assignment.getStudentID()).get() : null)
                .collect(Collectors.toSet());
    }

    public Set<Student> filterByProblem(int problemNumber) {
        return StreamSupport.stream(this.problemRepository.findAll().spliterator(), false)
                .filter(current -> current.getProblemNumber() == problemNumber).findAny()
                .map(this::filterByProblem).orElse(null);
    }

    public Set<Problem> filterByStudent(Student student) {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .filter(assignment -> assignment.getStudentID().equals(student.getId()))
                .map(assignment -> this.problemRepository.findOne(assignment.getProblemID()).isPresent() ?
                        this.problemRepository.findOne(assignment.getProblemID()).get() : null)
                .collect(Collectors.toSet());
    }

    public Set<Problem> filterByStudent(String serialNumber) {
        return StreamSupport.stream(this.studentRepository.findAll().spliterator(), false)
                .filter(current -> current.getSerialNumber().equals(serialNumber)).findAny()
                .map(this::filterByStudent).orElse(null);
    }

    public void close() {
        if (this.repository instanceof GenericDataBaseRepository) {
            ((GenericDataBaseRepository<Assignment>) this.repository).close();
            ((GenericDataBaseRepository<Student>) this.studentRepository).close();
            ((GenericDataBaseRepository<Problem>) this.problemRepository).close();
        }
    }

}
