package catalog.service;

import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.Assignment;
import catalog.repository.Repository;
import catalog.repository.RepositoryException;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class StudentProblemService {
    private Repository<Long, Assignment> repository;
    private Repository<Long, Student> studentRepository;
    private Repository<Long, LabProblem> problemRepository;

    public StudentProblemService(Repository<Long, Assignment> repository,
                                 Repository<Long, Student> studentRepository,
                                 Repository<Long, LabProblem> problemRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.problemRepository = problemRepository;
    }

    public void addStudentProblem(Assignment assignment) throws RepositoryException {
        try {
            assert (this.studentRepository.findOne(assignment.getStudentID()).isPresent()); //  if student.isPresent
            assert (this.problemRepository.findOne(assignment.getProblemID()).isPresent()); //      and problem.isPresent
            this.repository.save(assignment);                                               //          save()
        } catch (AssertionError ignored) {                                                      //  else
            throw new RepositoryException("student or problem does not exist");                 //      throw Error
        }
    }

    public Set<Assignment> getAllStudentProblems() {
        Iterable<Assignment> studentProblems = this.repository.findAll();
        return StreamSupport.stream(studentProblems.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Returns all Students who have been assigned the given LabProblem.
     * @param labProblem LabProblem
     * @return Set<Student>
     */
    public Set<Student> filterByProblem(LabProblem labProblem) {
        Iterable<Student> students = this.studentRepository.findAll();

        return StreamSupport.stream(students.spliterator(), false)                              //  for student in studentRepo
                .filter(student -> this.repository.findOne(student.getId()).isPresent() &&              //      if repo.getStudent(student)
                                        this.repository.findOne(student.getId()).get().getProblemID()   //              .getProblemID
                                            .equals(labProblem.getId()))                                //              == labProblem.getID
                .collect(Collectors.toSet());                                                           //          toReturn.add(student)
        /*
        // Equivalent iterative
        Set<Student> studentsWithProblem = new HashSet<>();
        for(Student student : this.studentRepository.findAll()) {
            if (this.repository.findOne(student.getId()).isPresent() &&
                    this.repository.findOne(student.getId()).get().getProblemID()
                            .equals(labProblem.getId())) {
                studentsWithProblem.add(student);
            }
        }
        return studentsWithProblem;
         */
    }

    /**
     * Returns all Students who have been assigned a LabProblem with given problemNumber
     * @param problemNumber int
     * @return Set <Student>
     */
    public Set<Student> filterByProblem(int problemNumber) {
        Iterable<Student> students = this.studentRepository.findAll();

        return StreamSupport.stream(students.spliterator(), false)                      //  for student in studentRepo
                .filter(student -> this.repository.findOne(student.getId()).isPresent() &&      //      if repo.getStudent(student)
                        this.problemRepository.findOne(                                         //      if problemRepo.get(
                                this.repository.findOne(student.getId()).get().getProblemID())  //          repo.getStudent(
                                .isPresent() &&                                                 //              student)
                        this.problemRepository.findOne(                                         //                  .getProblemID)
                                this.repository.findOne(student.getId()).get().getProblemID()   //              ==
                        ).get().getProblemNumber() == problemNumber)                            //              problemNumber
                .collect(Collectors.toSet());                                                   //          toReturn.add(Student)
    }

    /**
     * Returns all LabProblems that have been assigned to Student with given serialNumber
     * @param serialNumber String
     * @return Set <LabProblem>
     */
    public Set<LabProblem> filterByStudent(String serialNumber) {
        Iterable<LabProblem> problems = this.problemRepository.findAll();

        return StreamSupport.stream(problems.spliterator(), false)
                .filter(problem -> this.repository.findOne(problem.getId()).isPresent() &&
                        this.studentRepository.findOne(
                                this.repository.findOne(problem.getId()).get().getStudentID())
                                .isPresent() &&
                        this.studentRepository.findOne(
                                this.repository.findOne(problem.getId()).get().getStudentID()
                        ).get().getSerialNumber().equals(serialNumber))
                .collect(Collectors.toSet());
    }

    /**
     * Returns all LabProblems that have been assigned to the given Student.
     * @param student Student
     * @return Set<LabProblem>
     */
    public Set<LabProblem> filterByStudent(Student student) {
        Iterable<LabProblem> problems = this.problemRepository.findAll();

        return StreamSupport.stream(problems.spliterator(), false)
                .filter(problem -> this.repository.findOne(problem.getId()).isPresent() &&
                                        this.repository.findOne(problem.getId()).get().getStudentID()
                                            .equals(student.getId()))
                .collect(Collectors.toSet());
    }

    public void removeStudentProblem(Assignment assignment) {
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
}
