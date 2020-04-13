package service;

import domain.LabProblem;
import domain.Student;
import domain.Assignment;
import repository.GenericDataBaseRepository;
import repository.Repository;
import repository.RepositoryException;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class AssignmentService {
    private Repository<Long, Assignment> repository;
    private Repository<Long, Student> studentRepository;
    private Repository<Long, LabProblem> problemRepository;

    public AssignmentService(Repository<Long, Assignment> repository,
                             Repository<Long, Student> studentRepository,
                             Repository<Long, LabProblem> problemRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.problemRepository = problemRepository;
    }

    public void addAssignment(Assignment assignment) throws RepositoryException {
        try {
            assert (this.studentRepository.findOne(assignment.getStudentID()).isPresent()); //  if student.isPresent
            assert (this.problemRepository.findOne(assignment.getProblemID()).isPresent()); //      and problem.isPresent
            this.repository.save(assignment);                                               //          save()
        } catch (AssertionError ignored) {                                                      //  else
            throw new RepositoryException("student or problem does not exist");                 //      throw Error
        }
    }

    public Set<Assignment> getAllAssignments() {
        Iterable<Assignment> studentProblems = this.repository.findAll();
        return StreamSupport.stream(studentProblems.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Returns all Students who have been assigned the given LabProblem.
     * @param labProblem LabProblem
     * @return Set<Student>
     */
    public Set<Student> filterByProblem(LabProblem labProblem) {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .filter(assignment -> assignment.getProblemID().equals(labProblem.getId()))
                .map(assignment -> this.studentRepository.findOne(assignment.getStudentID()).isPresent() ?
                        this.studentRepository.findOne(assignment.getStudentID()).get() : null)
                .collect(Collectors.toSet());
    }

    /**
     * Returns all Students who have been assigned a LabProblem with given problemNumber
     * @param problemNumber int
     * @return Set <Student>
     */
    public Set<Student> filterByProblem(int problemNumber) {
        return StreamSupport.stream(this.problemRepository.findAll().spliterator(), false)
                .filter(current -> current.getProblemNumber() == problemNumber).findAny()
                .map(this::filterByProblem).orElse(null);
    }

    /**
     * Returns all LabProblems that have been assigned to Student with given serialNumber
     * @param serialNumber String
     * @return Set <LabProblem>
     */
    public Set<LabProblem> filterByStudent(String serialNumber) {
        return StreamSupport.stream(this.studentRepository.findAll().spliterator(), false)
                .filter(current -> current.getSerialNumber().equals(serialNumber)).findAny()
                .map(this::filterByStudent).orElse(null);
    }

    /**
     * Returns all LabProblems that have been assigned to the given Student.
     * @param student Student
     * @return Set<LabProblem>
     */
    public Set<LabProblem> filterByStudent(Student student) {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .filter(assignment -> assignment.getStudentID().equals(student.getId()))
                .map(assignment -> this.problemRepository.findOne(assignment.getProblemID()).isPresent() ?
                        this.problemRepository.findOne(assignment.getProblemID()).get() : null)
                .collect(Collectors.toSet());
    }

    public void removeAssignment(Assignment assignment) {
        this.repository.delete(assignment.getId());
    }

    /**
     * Removes all Assignments of given Student.
     * @param student Student
     */
    public void removeStudent(Student student) {
        Iterable<Assignment> assignments = this.repository.findAll();
        StreamSupport.stream(assignments.spliterator(), false)
                .filter(assignment -> this.repository.findOne(assignment.getId()).isPresent() &&
                        this.repository.findOne(assignment.getId()).get().getStudentID().equals(student.getId()))
                .forEach(assgnment -> this.repository.delete(assgnment.getId()));
    }

    /**
     * Removes all Assignments of given LabProblem.
     * @param problem LabProblem
     */
    public void removeProblem(LabProblem problem) {
        Iterable<Assignment> assignments = this.repository.findAll();
        StreamSupport.stream(assignments.spliterator(), false)
                .filter(assignment -> this.repository.findOne(assignment.getId()).isPresent() &&
                        this.repository.findOne(assignment.getId()).get().getProblemID().equals(problem.getId()))
                .forEach(assignment -> this.repository.delete(assignment.getId()));
    }

    public void close() {
        if (this.repository instanceof GenericDataBaseRepository) {
            ((GenericDataBaseRepository<Assignment>) this.repository).close();
            ((GenericDataBaseRepository<Student>) this.studentRepository).close();
            ((GenericDataBaseRepository<LabProblem>) this.problemRepository).close();
        }
    }

    public void updateAssignment(Assignment assignment) {
        this.repository.update(assignment);
    }
}
