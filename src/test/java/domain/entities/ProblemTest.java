package domain.entities;

import domain.entities.Problem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProblemTest {
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final int PROBLEM_NUMBER = 1;
    private static final int NEW_PROBLEM_NUMBER = 2;
    private static final String NAME = "problem1";
    private static final String NEW_NAME = "problem2";
    private static final String DESCRIPTION = "This is description 1.";
    private static final String NEW_DESCRIPTION = "This is description 2.";

    private Problem problem;

    @Before
    public void setUp() throws Exception {
        problem = new Problem(ID, PROBLEM_NUMBER, NAME, DESCRIPTION);
        //labProblem.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        problem =null;
    }

    @Test
    public void testGetProblemNumber() throws Exception {
        assertEquals("Problem numbers should be equal", PROBLEM_NUMBER, problem.getProblemNumber());
    }

    @Test
    public void testSetProblemNumber() throws Exception {
        problem.setProblemNumber(NEW_PROBLEM_NUMBER);
        assertEquals("Problem numbers should be equal", NEW_PROBLEM_NUMBER, problem.getProblemNumber());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, problem.getId());
    }

    @Test
    public void testSetId() throws Exception {
        problem.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, problem.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Names should be the same!", NAME, problem.getName());
    }

    @Test
    public void testSetName() throws Exception {
        problem.setName(NEW_NAME);
        assertEquals("Names should be the same!", NEW_NAME, problem.getName());
    }

    @Test
    public void testDescription() throws Exception {
        assertEquals("Descriptions should be identical!", DESCRIPTION, problem.getDescription());
    }

    @Test
    public void testSetDescription() throws Exception {
        problem.setDescription(NEW_DESCRIPTION);
        assertEquals("Descriptions should be identical!", NEW_DESCRIPTION, problem.getDescription());
    }
}
