package catalog.repository;

import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.validators.StudentValidator;
import catalog.domain.validators.Validator;
import catalog.service.StudentService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenericFileRepositoryTest {
    // todo : write tests for all functions
    Validator<Student> studentValidator;
    Repository<Long, Student> studentRepository;

    @Before
    public void setUp(){
        studentValidator = new StudentValidator();
        studentRepository = new GenericFileRepository<Student>(studentValidator, "./data/students.txt","catalog.domain.Student");
    }

    @Test
    public void testLoadData() throws Exception {
        assertTrue(studentRepository.findOne(1L).isPresent());
        assertTrue(studentRepository.findOne(2L).isPresent());
        assertTrue(studentRepository.findOne(3L).isPresent());
    }

    @Test
    public void testDelete() {
        assertTrue(studentRepository.findOne(4L).isPresent());
        studentRepository.delete(4L);
        assertFalse(studentRepository.findOne(4L).isPresent());
        studentRepository.save(new Student(4L, "bcie2345", "Bogdan Costache", 1));
        assertTrue(studentRepository.findOne(4L).isPresent());
    }

    @Test
    public void testSave() {
        studentRepository.save(new Student(5L, "abie2445", "Alexandru Badalescu", 1));
        studentRepository.save(new Student(6L, "mdie3345", "Mircea Dinescu", 2));

        Repository<Long, Student> studentRepository2 = new GenericFileRepository<Student>(studentValidator, "./data/students.txt","catalog.domain.Student");

        assertTrue(studentRepository2.findOne(5L).isPresent());
        assertTrue(studentRepository2.findOne(6L).isPresent());

        studentRepository.delete(5L);
        studentRepository.delete(6L);
    }

    @Test
    public void testUpdate() {
        Student student = (Student) studentRepository.findOne(1L).get();
        student.setGroup(5);
        studentRepository.update(student);

        Repository<Long, Student> studentRepository2 = new GenericFileRepository<Student>(studentValidator, "./data/students.txt","catalog.domain.Student");
        assertEquals(studentRepository2.findOne(1L).get().getGroup(), 5);

        student.setGroup(1);
        studentRepository.update(student);
        //assertEquals(studentRepository2.findOne(1L).get().getGroup(), 1);
    }
}
