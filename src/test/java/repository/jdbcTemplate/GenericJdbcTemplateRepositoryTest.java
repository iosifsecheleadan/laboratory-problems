package repository.jdbcTemplate;

import domain.entities.Student;
import domain.validators.StudentValidator;
import domain.validators.Validator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenericJdbcTemplateRepositoryTest {
    private GenericJdbcTemplateRepository<Student> students;
    private Student student;

    @Before
    public void setUp() {
        Validator<Student> validator = new StudentValidator();
        this.students = new GenericJdbcTemplateRepository<>(validator,
                "localhost", "ubbfmi2018", "school", "mppLabProbs", "Student",
                "domain.entities.Student");
        this.student = new Student(100L, "erty456", "name", 4567);
    }

    @Test
    public void testSave() {
        assertFalse(students.findOne(student.getId()).isPresent());
        students.save(student);
        assertTrue(students.findOne(student.getId()).isPresent());
        students.delete(student.getId());
    }

    @Test
    public void testDelete() {
        assertFalse(students.findOne(student.getId()).isPresent());
        students.save(student);
        assertTrue(students.findOne(student.getId()).isPresent());
        students.delete(student.getId());
        assertFalse(students.findOne(student.getId()).isPresent());
    }

    @Test
    public void testUpdate() {
        assertFalse(students.findOne(student.getId()).isPresent());
        students.save(student);
        students.update(new Student(student.getId(), "3456", "dertyu", 678));
        assertNotEquals(student, students.findOne(student.getId()).get());
        students.delete(student.getId());
    }
}
