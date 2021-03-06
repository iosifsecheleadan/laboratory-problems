package domain.entities;

import domain.entities.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StudentTest {
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final String SERIAL_NUMBER = "sn01";
    private static final String NEW_SERIAL_NUMBER = "sn02";
    private static final String NAME = "studentName";
    private static final String NEW_NAME = "studentName2";
    private static final int GROUP = 123;
    private static final int NEW_GROUP = 124;

    private Student student;

    @Before
    public void setUp() throws Exception {
        student = new Student(ID, SERIAL_NUMBER, NAME, GROUP);
        //student.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        student=null;
    }

    @Test
    public void testGetSerialNumber() throws Exception {
        assertEquals("Serial numbers should be equal", SERIAL_NUMBER, student.getSerialNumber());
    }

    @Test
    public void testSetSerialNumber() throws Exception {
        student.setSerialNumber(NEW_SERIAL_NUMBER);
        assertEquals("Serial numbers should be equal", NEW_SERIAL_NUMBER, student.getSerialNumber());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, student.getId());
    }

    @Test
    public void testSetId() throws Exception {
        student.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, student.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Names should be the same!", NAME, student.getName());
    }

    @Test
    public void testSetName() throws Exception {
        student.setName(NEW_NAME);
        assertEquals("Names should be the same!", NEW_NAME, student.getName());
    }

    @Test
    public void testGetGroup() throws Exception {
        assertEquals("Groups should be identical!", GROUP, student.getGroup());
    }

    @Test
    public void testSetGroup() throws Exception {
        student.setGroup(NEW_GROUP);
        assertEquals("Groups should be identical!", NEW_GROUP, student.getGroup());
    }
}