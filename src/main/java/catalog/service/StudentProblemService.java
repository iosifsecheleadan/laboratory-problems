package catalog.service;

import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.StudentProblem;
import catalog.domain.validators.StudentProblemValidator;
import catalog.repository.InMemoryRepository;
import catalog.repository.Repository;
import catalog.repository.RepositoryException;

import java.nio.channels.IllegalBlockingModeException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class StudentProblemService {
    private Repository<Long, StudentProblem> repository;
    private Repository<Long, Student> studentRepository;
    private Repository<Long, LabProblem> problemRepository;

    public StudentProblemService(Repository<Long, StudentProblem> repository,
                                 Repository<Long, Student> studentRepository,
                                 Repository<Long, LabProblem> problemRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.problemRepository = problemRepository;
    }

    public void addStudentProblem(StudentProblem studentProblem) throws RepositoryException {
        try {
            assert (this.studentRepository.findOne(studentProblem.getStudentID()).isPresent()); //  if student.isPresent
            assert (this.problemRepository.findOne(studentProblem.getProblemID()).isPresent()); //      and problem.isPresent
            this.repository.save(studentProblem);                                               //          save()
        } catch (AssertionError ignored) {                                                      //  else
            throw new RepositoryException("student or problem does not exist");                 //      throw Error
        }
    }

    public Set<StudentProblem> getAllStudentProblems() {
        Iterable<StudentProblem> studentProblems = this.repository.findAll();
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
}
