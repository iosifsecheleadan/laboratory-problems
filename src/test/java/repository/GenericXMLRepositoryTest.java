package catalog.repository;

import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.validators.LabProblemValidator;
import catalog.domain.validators.StudentValidator;
import catalog.domain.validators.Validator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenericXMLRepositoryTest {
    Validator<Student> studentValidator;
    Validator<LabProblem> labProblemValidator;
    Repository<Long, Student> studentRepository;
    Repository<Long, LabProblem> labProblemRepository;

    Student student1 = new Student(4L, "bcie2345", "Bogdan Costache", 1);
    LabProblem problem1 = new LabProblem(4L, 4, "Fourth Lab Problem", "This is the fourth lab problem");

    @Before
    public void setUp() {
        studentValidator = new StudentValidator();
        labProblemValidator = new LabProblemValidator();
        studentRepository = new GenericXMLRepository<Student>(studentValidator, "./data/students.xml", "catalog.domain.Student");
        labProblemRepository = new GenericXMLRepository<LabProblem>(labProblemValidator, "./data/labProblems.xml", "catalog.domain.LabProblem");
    }

    @Test
    public void testLoadData() {
        assertTrue(studentRepository.findOne(1L).isPresent());
        Student student = (Student) studentRepository.findOne(1L).get();
        assertEquals("siie1234", student.getSerialNumber());

        assertTrue(labProblemRepository.findOne(1L).isPresent());
        LabProblem labProblem = (LabProblem) labProblemRepository.findOne(1L).get();
        assertEquals(1, labProblem.getProblemNumber());
    }

    @Test
    public void testSave() {
        studentRepository.save(student1);
        Repository<Long, Student> studentRepository2 = new GenericXMLRepository<Student>(studentValidator, "./data/students.xml", "catalog.domain.Student");
        assertTrue(studentRepository2.findOne(4L).isPresent());

        labProblemRepository.save(problem1);
        Repository<Long, LabProblem> labProblemRepository2 = new GenericXMLRepository<LabProblem>(labProblemValidator, "./data/labProblems.xml", "catalog.domain.LabProblem");
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
        Repository<Long, Student> studentRepository2 = new GenericXMLRepository<Student>(studentValidator, "./data/students.xml", "catalog.domain.Student");
        assertFalse(studentRepository2.findOne(4L).isPresent());
    }
}
