package domain.validators;

import domain.entities.Assignment;
import domain.entities.Problem;
import domain.entities.Student;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ValidatorTests {

    @Test
    public void testStudentValidator() {
        Validator<Student> validator = new StudentValidator();
        Student student = new Student(0L, "serial67", "Name", 0);
        validator.validate(student);
        student.setId(-1L);
        try {
            validator.validate(student);
            fail("Student was valid when he should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }
        student.setId(1L);
        student.setSerialNumber(null);
        try {
            validator.validate(student);
            fail("Student was valid when he should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }
        student.setSerialNumber("bla345678bla");
        student.setName(null);
        try {
            validator.validate(student);
            fail("Student was valid when he should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }
        student.setName("Whatever");
        student.setGroup(-1);
        try {
            validator.validate(student);
            fail("Student was valid when he should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testProblemValidator() {
        Validator<Problem> validator = new ProblemValidator();
        Problem problem = new Problem(0L, 0, "problem", "description");
        validator.validate(problem);
        problem.setId(-1L);
        try {
            validator.validate(problem);
            fail("Problem was valid when he should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }
        problem.setId(1L);
        problem.setProblemNumber(-1);
        try {
            validator.validate(problem);
            fail("Problem was valid when he should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }
        problem.setProblemNumber(1);
        problem.setName(null);
        try {
            validator.validate(problem);
            fail("Student was valid when he should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }
        problem.setName("Whatever");
        problem.setDescription(null);
        try {
            validator.validate(problem);
            fail("Student was valid when he should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAssignmentValidator() {
        Validator<Assignment> validator = new AssignmentValidator();
        Assignment assignment = new Assignment(0L, 1L, 2L);
        validator.validate(assignment);
        assignment.setId(-1L);
        try {
            validator.validate(assignment);
            fail("Assignment was valid when it should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }
        assignment.setId(0L);
        assignment.setStudentID(-1L);
        try {
            validator.validate(assignment);
            fail("Assignment was valid when it should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }
        assignment.setStudentID(1L);
        assignment.setProblemID(-1L);
        try {
            validator.validate(assignment);
            fail("Assignment was valid when it should not have been!");
        } catch (ValidatorException e) {
            assertTrue(true);
        }
    }
}
