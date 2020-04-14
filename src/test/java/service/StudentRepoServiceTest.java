package service;

import domain.entities.Student;
import domain.validators.StudentValidator;
import repository.GenericFileRepository;
import repository.Repository;
import org.junit.*;

import static org.junit.Assert.*;


public class StudentRepoServiceTest {
    private Repository<Long, Student> repo;
    private StudentRepoService serv;
    private final Student zero = new Student(0L,"00zr","Zero the Hero", 0);
    private final Student one = new Student(1L, "11st", "The First", 1);
    private final Student two = new Student(2L, "22nd", "The Second", 1);

    @Before
    public void setUp() throws Exception {
        this.repo = new GenericFileRepository<Student>(
                new StudentValidator(),
                "./src/test/java/catalog/service/testStudents.txt",
                "domain.entities.Student");
        this.repo.findAll().forEach(student -> this.repo.delete(student.getId()));
        this.serv = new StudentRepoService(this.repo);
    }

    @After
    public void tearDown() throws Exception {
        this.repo.findAll().forEach(student -> this.repo.delete(student.getId()));
        this.repo = null;
        this.serv = null;
    }

    @Test
    public void testAddNGet() throws Exception {
        this.serv.addStudent(this.zero);
        assertTrue(this.serv.getAllStudents().contains(this.zero));
        assertEquals(1, this.serv.getAllStudents().size());
        this.serv.addStudent(this.one);
        this.serv.addStudent(this.two);
        assertTrue(this.serv.getAllStudents().contains(this.one));
        assertTrue(this.serv.getAllStudents().contains(this.two));
        assertEquals(3, this.serv.getAllStudents().size());
    }

    @Test
    public void testRemoveNGet() throws Exception {
        this.serv.addStudent(this.zero);
        this.serv.addStudent(this.one);
        this.serv.addStudent(this.two);
        this.serv.removeStudent(this.zero);
        assertFalse(this.serv.getAllStudents().contains(this.zero));
        this.serv.removeStudent(this.one);
        this.serv.removeStudent(this.two);
        assertEquals(0, this.serv.getAllStudents().size());
    }

    // Only for <? extends InMemoryRepository>
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteStudent() {
        this.serv.removeStudent(new Student(null, null, null, 0));
    }

    @Test
    public void testFilterName() throws Exception {
        this.serv.addStudent(this.zero);
        this.serv.addStudent(this.one);
        this.serv.addStudent(this.two);
        assertTrue(this.serv.filterByName("the").contains(this.zero));
        assertFalse(this.serv.filterByName("the").contains(this.one));
        assertFalse(this.serv.filterByName("the").contains(this.two));
    }

    @Test
    public void testFilterGroup() throws Exception {
        this.serv.addStudent(this.zero);
        this.serv.addStudent(this.one);
        this.serv.addStudent(this.two);
        assertFalse(this.serv.filterByGroup(1).contains(this.zero));
        assertTrue(this.serv.filterByGroup(1).contains(this.one));
        assertTrue(this.serv.filterByGroup(1).contains(this.two));
    }

    @Test
    public void testGetSerialNumber() {
        this.serv.addStudent(this.one);
        this.serv.addStudent(this.two);
        assertFalse(this.serv.getBySerialNumber(this.zero.getSerialNumber()).isPresent());
        assertTrue(this.serv.getBySerialNumber(this.one.getSerialNumber()).isPresent());
        assertEquals(this.serv.getBySerialNumber(this.one.getSerialNumber()).get(), this.one);
        assertTrue(this.serv.getBySerialNumber(this.two.getSerialNumber()).isPresent());
        assertEquals(this.serv.getBySerialNumber(this.two.getSerialNumber()).get(), this.two);
    }
}
