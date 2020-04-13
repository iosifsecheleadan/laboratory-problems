package catalog.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AssignmentTest {
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final Long STUDENT_ID = new Long(1);
    private static final Long NEW_STUDENT_ID = new Long(2);
    private static final Long LAB_PROBLEM_ID = new Long(3);
    private static final Long NEW_LAB_PROBLEM_ID = new Long(4);

    private Assignment assignment;

    @Before
    public void setUp() throws Exception {
        assignment = new Assignment(ID, STUDENT_ID, LAB_PROBLEM_ID);
    }

    @After
    public void tearDown() throws Exception {
        assignment =null;
    }

    @Test
    public void testGetStudentID() throws Exception {
        assertEquals("Student ID's should be equal!", STUDENT_ID, assignment.getStudentID());
    }

    @Test
    public void testSetStudentID() throws Exception {
        assignment.setStudentID(NEW_STUDENT_ID);
        assertEquals("Student ID's should be equal!", NEW_STUDENT_ID, assignment.getStudentID());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal!", ID, assignment.getId());
    }

    @Test
    public void testSetId() throws Exception {
        assignment.setId(NEW_ID);
        assertEquals("Ids should be equal!", NEW_ID, assignment.getId());
    }

    @Test
    public void testGetProblemID() throws Exception {
        assertEquals("Ids should be equal!", LAB_PROBLEM_ID, assignment.getProblemID());
    }

    @Test
    public void testSetProblemID() throws Exception {
        assignment.setProblemID(NEW_LAB_PROBLEM_ID);
        assertEquals("Names should be the same!", NEW_LAB_PROBLEM_ID, assignment.getProblemID());
    }
}
