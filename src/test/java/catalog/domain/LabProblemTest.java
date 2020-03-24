package catalog.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LabProblemTest {
    // todo : like labProblem
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final int PROBLEM_NUMBER = 1;
    private static final int NEW_PROBLEM_NUMBER = 2;
    private static final String NAME = "problem1";
    private static final String NEW_NAME = "problem2";
    private static final String DESCRIPTION = "This is description 1.";
    private static final String NEW_DESCRIPTION = "This is description 2.";

    private LabProblem labProblem;

    // todo : write tests

    @Before
    public void setUp() throws Exception {
        labProblem = new LabProblem(ID, PROBLEM_NUMBER, NAME, DESCRIPTION);
        //labProblem.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        labProblem=null;
    }

    @Test
    public void testGetProblemNumber() throws Exception {
        assertEquals("Problem numbers should be equal", PROBLEM_NUMBER, labProblem.getProblemNumber());
    }

    @Test
    public void testSetProblemNumber() throws Exception {
        labProblem.setProblemNumber(NEW_PROBLEM_NUMBER);
        assertEquals("Problem numbers should be equal", NEW_PROBLEM_NUMBER, labProblem.getProblemNumber());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, labProblem.getId());
    }

    @Test
    public void testSetId() throws Exception {
        labProblem.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, labProblem.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Names should be the same!", NAME, labProblem.getName());
    }

    @Test
    public void testSetName() throws Exception {
        labProblem.setName(NEW_NAME);
        assertEquals("Names should be the same!", NEW_NAME, labProblem.getName());
    }

    @Test
    public void testDescription() throws Exception {
        assertEquals("Descriptions should be identical!", DESCRIPTION, labProblem.getDescription());
    }

    @Test
    public void testSetDescription() throws Exception {
        labProblem.setDescription(NEW_DESCRIPTION);
        assertEquals("Descriptions should be identical!", NEW_DESCRIPTION, labProblem.getDescription());
    }
}
