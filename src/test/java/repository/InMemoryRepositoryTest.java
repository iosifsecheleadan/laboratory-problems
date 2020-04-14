package repository;

import domain.entities.Student;
import domain.validators.StudentValidator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import domain.validators.ValidatorException;

import static org.junit.Assert.*;

/**
 * @author radu.
 */
public class InMemoryRepositoryTest {
    InMemoryRepository<Long, Student> studentRepo;
    StudentValidator studentValidator;
    private final Student zero = new Student(0L,"00zr","Zero the Hero", 0);
    private final Student one = new Student(1L, "11st", "The First", 1);
    private final Student two = new Student(2L, "22nd", "The Second", 1);

    @Test
    @Before
    public void setUp() throws Exception {
        studentValidator = new StudentValidator();
        studentRepo = new InMemoryRepository<Long, Student>(studentValidator);

        studentRepo.save(zero);
        studentRepo.save(two);
    }

    @Test
    public void testFindOne() throws Exception {
        //System.out.println(studentRepo.findOne(1L).isPresent());
        assertTrue(studentRepo.findOne(0L).isPresent());
        assertTrue(studentRepo.findOne(2L).isPresent());
        assertFalse(studentRepo.findOne(1L).isPresent());
    }

    @Test
    public void testFindAll() throws Exception {
        //fail("Not yet tested");
        int i = 0;
        Iterable<Student> students = studentRepo.findAll();

        for(Student s : students) {
            i++;
        }

        assertEquals(i, 2);
    }

    @Test
    public void testSave() throws Exception {
        studentRepo.save(one);
        assertTrue(studentRepo.findOne(1L).isPresent());
    }

    @Ignore
    @Test(expected = ValidatorException.class)
    public void testSaveException() throws Exception {
        fail("Not yet tested");
    }

    @Test
    public void testDelete() throws Exception {
        studentRepo.delete(0L);
        assertFalse(studentRepo.findOne(0L).isPresent());
        studentRepo.delete(2L);
        assertFalse(studentRepo.findOne(2L).isPresent());
    }

    @Test
    public void testUpdate() throws Exception {
        zero.setGroup(1);
        studentRepo.update(zero);
        assertEquals(studentRepo.findOne(0L).get().getGroup(), 1);
    }

    @Ignore
    @Test(expected = ValidatorException.class)
    public void testUpdateException() throws Exception {
        fail("Not yet tested");
    }
}