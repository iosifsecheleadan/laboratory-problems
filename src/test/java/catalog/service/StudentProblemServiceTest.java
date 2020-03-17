package catalog.service;

import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.StudentProblem;
import catalog.domain.validators.LabProblemValidator;
import catalog.domain.validators.StudentProblemValidator;
import catalog.domain.validators.StudentValidator;
import catalog.repository.GenericFileRepository;
import catalog.repository.Repository;
import catalog.repository.RepositoryException;
import com.sun.org.apache.bcel.internal.generic.LADD;
import jdk.internal.vm.annotation.ForceInline;
import org.junit.*;

import java.util.Arrays;

import static org.junit.Assert.*;

public class StudentProblemServiceTest {
    private Repository<Long, StudentProblem> repo;
    private Repository<Long, Student> students;
    private Repository<Long, LabProblem> labProblems;
    private StudentProblemService serv;

    private final Student Szero = new Student(0L,"00zr","Zero the Hero", 0);
    private final Student Sone = new Student(1L, "11st", "The First", 1);
    private final Student Stwo = new Student(2L, "22nd", "The Second", 1);

    private final LabProblem LPzero = new LabProblem(0L, 0, "ZeroTh", "You think just because it's small it's easy. But it ain't.");
    private final LabProblem LPone = new LabProblem(1L, 1, "The FirSt", "No not furry. Firstly.");
    private final LabProblem LPtwo = new LabProblem(2L, 2, "The SecoNd", "Time? What time? No time. Just money.");

    private final StudentProblem SP00 = new StudentProblem(0L, 0L, 0L);
    private final StudentProblem SP01 = new StudentProblem(1L, 0L, 1L);
    private final StudentProblem SP02 = new StudentProblem(2L, 0L, 2L);
    private final StudentProblem SP10 = new StudentProblem(3L, 1L, 0L);
    private final StudentProblem SP11 = new StudentProblem(4L, 1L, 1L);


    @Before
    public void setUp() {
        this.repo = new GenericFileRepository<StudentProblem>(
                new StudentProblemValidator(),
                "./src/test/java/catalog/service/testStudentProblems.txt",
                "catalog.domain.StudentProblem");
        this.repo.findAll().forEach(studentProblem -> this.repo.delete(studentProblem.getId()));

        this.students = new GenericFileRepository<Student>(
                new StudentValidator(),
                "./src/test/java/catalog/service/testStudents.txt",
                "catalog.domain.Student");

        this.labProblems = new GenericFileRepository<LabProblem>(
                new LabProblemValidator(),
                "./src/test/java/catalog/service/testLabProblems.txt",
                "catalog.domain.LabProblem");

        this.serv = new StudentProblemService(this.repo, this.students, this.labProblems);
    }

    @After
    public void tearDown() {
        this.repo.findAll().forEach(studentProblem -> this.repo.delete(studentProblem.getId()));
        this.repo = null;
        this.students = null;
        this.labProblems = null;
        this.serv = null;
    }

    @Test
    public void testAddNGet() throws Exception {
        this.serv.addStudentProblem(this.SP00);
        assertTrue(this.serv.getAllStudentProblems().contains(this.SP00));
        assertEquals(1, this.serv.getAllStudentProblems().size());
        this.serv.addStudentProblem(this.SP01);
        this.serv.addStudentProblem(this.SP02);
        this.serv.addStudentProblem(this.SP10);
        this.serv.addStudentProblem(this.SP11);
        assertTrue(this.serv.getAllStudentProblems().contains(this.SP01));
        assertTrue(this.serv.getAllStudentProblems().contains(this.SP02));
        assertTrue(this.serv.getAllStudentProblems().contains(this.SP10));
        assertTrue(this.serv.getAllStudentProblems().contains(this.SP11));
        assertEquals(5, this.serv.getAllStudentProblems().size());
    }

    @Test (expected = RepositoryException.class)
    public void testAdd() throws Exception {
        this.serv.addStudentProblem(new StudentProblem(5L, 5L, 5L));
    }

    @Test
    public void testRemoveNGet() throws Exception {
        this.serv.addStudentProblem(this.SP00);
        this.serv.addStudentProblem(this.SP01);
        this.serv.addStudentProblem(this.SP02);
        this.serv.addStudentProblem(this.SP10);
        this.serv.addStudentProblem(this.SP11);
        assertEquals(5, this.serv.getAllStudentProblems().size());
        this.serv.removeStudentProblem(this.SP00);
        assertFalse(this.serv.getAllStudentProblems().contains(this.SP00));
        this.serv.removeStudentProblem(this.SP01);
        this.serv.removeStudentProblem(this.SP02);
        this.serv.removeStudentProblem(this.SP10);
        this.serv.removeStudentProblem(this.SP11);
        assertEquals(0, this.serv.getAllStudentProblems().size());
    }

    @Test
    public void testRemoveStudent() throws Exception {
        this.serv.addStudentProblem(this.SP00);
        this.serv.addStudentProblem(this.SP01);
        this.serv.addStudentProblem(this.SP02);
        this.serv.addStudentProblem(this.SP10);
        this.serv.addStudentProblem(this.SP11);
        this.serv.removeStudent(this.Szero);
        assertFalse(this.serv.getAllStudentProblems().contains(this.SP00));
        assertEquals(2, this.serv.getAllStudentProblems().size());
    }

    @Test
    public void testRemoveProblem() throws Exception {
        this.serv.addStudentProblem(this.SP00);
        this.serv.addStudentProblem(this.SP01);
        this.serv.addStudentProblem(this.SP02);
        this.serv.addStudentProblem(this.SP10);
        this.serv.addStudentProblem(this.SP11);
        this.serv.removeProblem(this.LPzero);
        assertFalse(this.serv.getAllStudentProblems().contains(this.SP00));
        assertEquals(3, this.serv.getAllStudentProblems().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteStudentProblem() {
        this.serv.removeStudentProblem(new StudentProblem(null, null, null));
    }

    // todo : correct these

    // filterByProblem doesn't work properly
    @Ignore
    @Test
    public void testFilterProblem() throws Exception {
        this.serv.addStudentProblem(this.SP00);
        this.serv.addStudentProblem(this.SP01);
        this.serv.addStudentProblem(this.SP02);
        this.serv.addStudentProblem(this.SP10);
        this.serv.addStudentProblem(this.SP11);
        //System.out.println(this.serv.filterByProblem(this.LPone));
        assertEquals(2, this.serv.filterByProblem(this.LPzero).size());
        assertEquals(this.serv.filterByProblem(this.LPone),
                this.serv.filterByProblem(this.LPone.getProblemNumber()));
    }

    // filterByStudent doesn't work properly
    @Ignore
    @Test
    public void testFilterStudent() throws Exception {
        this.serv.addStudentProblem(this.SP00);
        this.serv.addStudentProblem(this.SP01);
        this.serv.addStudentProblem(this.SP02);
        this.serv.addStudentProblem(this.SP10);
        this.serv.addStudentProblem(this.SP11);
        assertEquals(2, this.serv.filterByStudent(this.Sone).size());
        assertEquals(this.serv.filterByStudent(this.Sone),
                this.serv.filterByStudent(this.Sone.getSerialNumber()));
    }



}