package service.repo;

import domain.validators.ValidatorException;
import repository.sort.GenericDataBaseRepository;
import repository.Repository;
import domain.entities.Student;
import service.StudentService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of StudentService with Repository as Collection
 * @author sechelea
 */
public class StudentRepoService
        implements StudentService {
    private Repository<Long, Student> repository;

    /**
     * Parametrized Constructor
     * @param repository Repository
     */
    public StudentRepoService(Repository<Long, Student> repository) {
        this.repository = repository;
    }

    public void addStudent(Student student) throws ValidatorException {
        this.repository.save(student);
    }

    public void updateStudent(Student student) throws ValidatorException{
        this.repository.update(student);

    }
    public Set<Student> getAllStudents() {
        Iterable<Student> students = repository.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Student> filterByName(String string) {
        Iterable<Student> students = repository.findAll();
        Set<Student> filtered= new HashSet<>();

        students.forEach(filtered::add);
        filtered.removeIf(student -> !student.getName().contains(string));

        return filtered;
    }

    public Set<Student> filterByGroup(int group) {
        Iterable<Student> students = repository.findAll();
        Set<Student> filtered = new HashSet<>();

        students.forEach(filtered::add);
        filtered.removeIf(student -> student.getGroup() != group);

        return filtered;
    }

    public void removeStudent(Student student) {
        this.repository.delete(student.getId());
    }

    public Optional<Student> getBySerialNumber(String serialNumber) {
        Iterable<Student> students = this.repository.findAll();
        return StreamSupport.stream(students.spliterator(), false)
                .filter(student -> student.getSerialNumber().equals(serialNumber))
                .findAny();
    }

    public void close() {
        if (this.repository instanceof GenericDataBaseRepository) {
            ((GenericDataBaseRepository<Student>) this.repository).close();
        }
    }

}
