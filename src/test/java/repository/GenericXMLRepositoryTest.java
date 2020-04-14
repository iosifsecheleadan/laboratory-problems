package repository;

import domain.entities.Problem;
import domain.entities.Student;
import domain.validators.ProblemValidator;
import domain.validators.StudentValidator;
import domain.validators.Validator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenericXMLRepositoryTest {
    Validator<Student> studentValidator;
    Validator<Problem> labProblemValidator;
    Repository<Long, Student> studentRepository;
    Repository<Long, Problem> labProblemRepository;

    Student student1 = new Student(4L, "bcie2345", "Bogdan Costache", 1);
    Problem problem1 = new Problem(4L, 4, "Fourth Lab Problem", "This is the fourth lab problem");

    @Before
    public void setUp() {
        studentValidator = new StudentValidator();
        labProblemValidator = new ProblemValidator();
        studentRepository = new GenericXMLRepository<Student>(studentValidator, "./data/students.xml", "catalog.domain.entities.Student");
        labProblemRepository = new GenericXMLRepository<Problem>(labProblemValidator, "./data/labProblems.xml", "catalog.domain.LabProblem");
    }

    @Test
    public void testLoadData() {
        assertTrue(studentRepository.findOne(1L).isPresent());
        Student student = (Student) studentRepository.findOne(1L).get();
        assertEquals("siie1234", student.getSerialNumber());

        assertTrue(labProblemRepository.findOne(1L).isPresent());
        Problem problem = (Problem) labProblemRepository.findOne(1L).get();
        assertEquals(1, problem.getProblemNumber());
    }

    @Test
    public void testSave() {
        studentRepository.save(student1);
        Repository<Long, Student> studentRepository2 = new GenericXMLRepository<Student>(studentValidator, "./data/students.xml", "catalog.domain.entities.Student");
        assertTrue(studentRepository2.findOne(4L).isPresent());

        labProblemRepository.save(problem1);
        Repository<Long, Problem> labProblemRepository2 = new GenericXMLRepository<Problem>(labProblemValidator, "./data/labProblems.xml", "catalog.domain.LabProblem");
        assertTrue(labProblemRepository2.findOne(4L).isPresent());
    }

    @Test
    public void testUpdate() {
        Student updateStudent = new Student(1L, "siie1234", "Sechelea Iosif", 1);
        studentRepository.update(updateStudent);
        assertTrue(studentRepository.findOne(1L).isPresent());
        assertEquals(studentRepository.findOne(1L).get().getGroup(), 1);
    }

    @Test
    public void testDelete() {
        assertTrue(studentRepository.findOne(4L).isPresent());
        studentRepository.delete(4L);
        assertFalse(studentRepository.findOne(4L).isPresent());
        Repository<Long, Student> studentRepository2 = new GenericXMLRepository<Student>(studentValidator, "./data/students.xml", "catalog.domain.entities.Student");
        assertFalse(studentRepository2.findOne(4L).isPresent());
    }
}
