package service;

import domain.entities.Problem;
import domain.entities.Student;
import domain.entities.Assignment;
import domain.validators.ProblemValidator;
import domain.validators.AssignmentValidator;
import domain.validators.StudentValidator;
import repository.inMemory.GenericFileRepository;
import repository.Repository;
import repository.RepositoryException;
import org.junit.*;
import service.repo.AssignmentRepoService;

import static org.junit.Assert.*;

public class AssignmentRepoServiceTest {
    private Repository<Long, Assignment> repo;
    private Repository<Long, Student> students;
    private Repository<Long, Problem> problems;
    private AssignmentRepoService serv;

    private final Student Szero = new Student(0L,"00zr","Zero the Hero", 0);
    private final Student Sone = new Student(1L, "11st", "The First", 1);
    private final Student Stwo = new Student(2L, "22nd", "The Second", 1);

    private final Problem Pzero = new Problem(0L, 0, "ZeroTh", "You think just because it's small it's easy. But it ain't.");
    private final Problem Pone = new Problem(1L, 1, "The FirSt", "No not furry. Firstly.");
    private final Problem Ptwo = new Problem(2L, 2, "The SecoNd", "Time? What time? No time. Just money.");

    private final Assignment SP00 = new Assignment(0L, 0L, 0L);
    private final Assignment SP01 = new Assignment(1L, 0L, 1L);
    private final Assignment SP02 = new Assignment(2L, 0L, 2L);
    private final Assignment SP10 = new Assignment(3L, 1L, 0L);
    private final Assignment SP11 = new Assignment(4L, 1L, 1L);


    @Before
    public void setUp() {
        this.repo = new GenericFileRepository<Assignment>(
                new AssignmentValidator(),
                "./src/test/java/service/testStudentProblems.txt",
                "domain.entities.Assignment");
        this.repo.findAll().forEach(studentProblem -> this.repo.delete(studentProblem.getId()));

        this.students = new GenericFileRepository<Student>(
                new StudentValidator(),
                "./src/test/java/service/testStudents.txt",
                "domain.entities.Student");
        this.students.save(Szero); this.students.save(Sone); this.students.save(Stwo);

        this.problems = new GenericFileRepository<Problem>(
                new ProblemValidator(),
                "./src/test/java/service/testLabProblems.txt",
                "domain.entities.Problem");
        this.problems.save(Pzero); this.problems.save(Pone); this.problems.save(Ptwo);

        this.serv = new AssignmentRepoService(this.repo, this.students, this.problems);
    }

    @After
    public void tearDown() {
        this.repo.findAll().forEach(studentProblem -> this.repo.delete(studentProblem.getId()));
        this.repo = null;
        this.students = null;
        this.problems = null;
        this.serv = null;
    }

    @Test
    public void testAddNGet() throws Exception {
        this.serv.addAssignment(this.SP00);
        assertTrue(this.serv.getAllAssignments().contains(this.SP00));
        assertEquals(1, this.serv.getAllAssignments().size());
        this.serv.addAssignment(this.SP01);
        this.serv.addAssignment(this.SP02);
        this.serv.addAssignment(this.SP10);
        this.serv.addAssignment(this.SP11);
        assertTrue(this.serv.getAllAssignments().contains(this.SP01));
        assertTrue(this.serv.getAllAssignments().contains(this.SP02));
        assertTrue(this.serv.getAllAssignments().contains(this.SP10));
        assertTrue(this.serv.getAllAssignments().contains(this.SP11));
        assertEquals(5, this.serv.getAllAssignments().size());
    }

    @Test (expected = RepositoryException.class)
    public void testAdd() throws Exception {
        this.serv.addAssignment(new Assignment(5L, 5L, 5L));
    }

    @Test
    public void testRemoveNGet() throws Exception {
        this.serv.addAssignment(this.SP00);
        this.serv.addAssignment(this.SP01);
        this.serv.addAssignment(this.SP02);
        this.serv.addAssignment(this.SP10);
        this.serv.addAssignment(this.SP11);
        assertEquals(5, this.serv.getAllAssignments().size());
        this.serv.removeAssignment(this.SP00);
        assertFalse(this.serv.getAllAssignments().contains(this.SP00));
        this.serv.removeAssignment(this.SP01);
        this.serv.removeAssignment(this.SP02);
        this.serv.removeAssignment(this.SP10);
        this.serv.removeAssignment(this.SP11);
        assertEquals(0, this.serv.getAllAssignments().size());
    }

    @Test
    public void testRemoveStudent() throws Exception {
        this.serv.addAssignment(this.SP00);
        this.serv.addAssignment(this.SP01);
        this.serv.addAssignment(this.SP02);
        this.serv.addAssignment(this.SP10);
        this.serv.addAssignment(this.SP11);
        this.serv.removeStudent(this.Szero);
        assertFalse(this.serv.getAllAssignments().contains(this.SP00));
        assertEquals(2, this.serv.getAllAssignments().size());
    }

    @Test
    public void testRemoveProblem() throws Exception {
        this.serv.addAssignment(this.SP00);
        this.serv.addAssignment(this.SP01);
        this.serv.addAssignment(this.SP02);
        this.serv.addAssignment(this.SP10);
        this.serv.addAssignment(this.SP11);
        this.serv.removeProblem(this.Pzero);
        assertFalse(this.serv.getAllAssignments().contains(this.SP00));
        assertEquals(3, this.serv.getAllAssignments().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteStudentProblem() {
        this.serv.removeAssignment(new Assignment(null, null, null));
    }

    @Test
    public void testFilterProblem() throws Exception {
        this.serv.addAssignment(this.SP00);
        this.serv.addAssignment(this.SP01);
        this.serv.addAssignment(this.SP02);
        this.serv.addAssignment(this.SP10);
        this.serv.addAssignment(this.SP11);
        //System.out.println(this.serv.filterByProblem(this.LPone));
        assertEquals(2, this.serv.filterByProblem(this.Pzero).size());
        assertEquals(this.serv.filterByProblem(this.Pone),
                this.serv.filterByProblem(this.Pone.getProblemNumber()));
    }

    @Test
    public void testFilterStudent() throws Exception {
        this.serv.addAssignment(this.SP00);
        this.serv.addAssignment(this.SP01);
        this.serv.addAssignment(this.SP02);
        this.serv.addAssignment(this.SP10);
        this.serv.addAssignment(this.SP11);
        assertEquals(2, this.serv.filterByStudent(this.Sone).size());
        assertEquals(this.serv.filterByStudent(this.Sone),
                this.serv.filterByStudent(this.Sone.getSerialNumber()));
    }



}