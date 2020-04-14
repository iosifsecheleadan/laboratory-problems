package service;

import domain.entities.Problem;
import domain.validators.ProblemValidator;
import repository.GenericFileRepository;
import repository.Repository;
import org.junit.*;

import static org.junit.Assert.*;

public class ProblemRepoServiceTest {
    private Repository<Long, Problem> repo;
    private ProblemRepoService serv;
    private final Problem zero = new Problem(0L, 0, "ZeroTh", "You think just because it's small it's easy. But it ain't.");
    private final Problem one = new Problem(1L, 1, "The FirSt", "No not furry. Firstly.");
    private final Problem two = new Problem(2L, 2, "The SecoNd", "Time? What time? No time. Just money.");

    @Before
    public void setUp() {
        this.repo = new GenericFileRepository<Problem>(
                new ProblemValidator(),
                "./src/test/java/catalog/service/testLabProblems.txt",
                "domain.LabProblem");
        this.repo.findAll().forEach(labProblem -> this.repo.delete(labProblem.getId()));
        this.serv = new ProblemRepoService(this.repo);
    }

    @After
    public void tearDown() {
        this.repo.findAll().forEach(student -> this.repo.delete(student.getId()));
        this.repo = null;
        this.serv = null;
    }

    @Test
    public void testAddNGet() throws Exception {
        this.serv.addLabProblem(this.zero);
        assertTrue(this.serv.getAllLabProblems().contains(this.zero));
        assertEquals(1, this.serv.getAllLabProblems().size());
        this.serv.addLabProblem(this.one);
        this.serv.addLabProblem(this.two);
        assertTrue(this.serv.getAllLabProblems().contains(this.one));
        assertTrue(this.serv.getAllLabProblems().contains(this.two));
        assertEquals(3, this.serv.getAllLabProblems().size());
    }

    @Test
    public void testRemoveNGet() throws Exception {
        this.serv.addLabProblem(this.zero);
        this.serv.addLabProblem(this.one);
        this.serv.addLabProblem(this.two);
        assertEquals(3, this.serv.getAllLabProblems().size());
        this.serv.removeLabProblem(this.zero);
        assertFalse(this.serv.getAllLabProblems().contains(this.zero));
        this.serv.removeLabProblem(this.one);
        this.serv.removeLabProblem(this.two);
        assertEquals(0, this.serv.getAllLabProblems().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteLabProblem() {
        this.serv.removeLabProblem(new Problem(null, 0, null, null));
    }


    @Test
    public void testFilterName() throws Exception {
        this.serv.addLabProblem(this.zero);
        this.serv.addLabProblem(this.one);
        this.serv.addLabProblem(this.two);
        assertFalse(this.serv.filterByName("The").contains(this.zero));
        assertTrue(this.serv.filterByName("The").contains(this.one));
        assertTrue(this.serv.filterByName("The").contains(this.two));
    }

    @Test
    public void testFilterDescription() throws Exception {
        this.serv.addLabProblem(this.zero);
        this.serv.addLabProblem(this.one);
        this.serv.addLabProblem(this.two);
        assertFalse(this.serv.filterByDescription("No ").contains(this.zero));
        assertTrue(this.serv.filterByDescription("No ").contains(this.one));
        assertTrue(this.serv.filterByDescription("No ").contains(this.two));
    }

    @Test
    public void testGetProblemNumber() throws Exception{
        this.serv.addLabProblem(this.one);
        this.serv.addLabProblem(this.two);
        assertFalse(this.serv.getByProblemNumber(this.zero.getProblemNumber()).isPresent());
        assertTrue(this.serv.getByProblemNumber(this.one.getProblemNumber()).isPresent());
        assertEquals(this.serv.getByProblemNumber(this.one.getProblemNumber()).get(), this.one);
        assertTrue(this.serv.getByProblemNumber(this.two.getProblemNumber()).isPresent());
        assertEquals(this.serv.getByProblemNumber(this.two.getProblemNumber()).get(), this.two);
    }
}
