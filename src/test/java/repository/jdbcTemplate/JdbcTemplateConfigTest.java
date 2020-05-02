package repository.jdbcTemplate;

import domain.entities.Assignment;
import domain.entities.Problem;
import domain.entities.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;

import static org.junit.Assert.*;

public class JdbcTemplateConfigTest {
    private Repository<Long, Student> students;
    private Repository<Long, Problem> problems;
    private Repository<Long, Assignment> assignments;
    private JdbcTemplateConfig config;

    @Before
    public void setUp() {
        this.config = new JdbcTemplateConfig();
        this.students = config.studentRepository();
        this.problems = config.problemRepository();
        this.assignments = config.assignmentRepository();
    }

    @Test
    public void test() {
        assertTrue(this.students instanceof GenericJdbcTemplateRepository);
        assertTrue(this.problems instanceof GenericJdbcTemplateRepository);
        assertTrue(this.assignments instanceof GenericJdbcTemplateRepository);
    }

    @After
    public void tearDown() {
        this.students = null;
        this.problems = null;
        this.assignments = null;
        this.config = null;
    }
}
