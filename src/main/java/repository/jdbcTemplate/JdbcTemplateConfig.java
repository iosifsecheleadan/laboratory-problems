package repository.jdbcTemplate;

import domain.entities.Assignment;
import domain.entities.Problem;
import domain.entities.Student;
import domain.validators.AssignmentValidator;
import domain.validators.ProblemValidator;
import domain.validators.StudentValidator;
import domain.validators.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Class for student, problem and assignment GenericJdbcTemplateRepositories
 * @author sechelea
 */
@Configuration
public class JdbcTemplateConfig {
    private static final String studentClass = "domain.entities.Student";
    private static final String problemClass = "domain.entities.Problem";
    private static final String assignmentClass = "domain.entities.Assignment";

    private static final String studentTable = "Student";
    private static final String problemTable = "LabProblem";
    private static final String assignmentTable = "Assignment";

    private static final String host = "localhost";
    private static final String password = "ubbfmi2018";
    private static final String user = "school";
    private static final String dataBaseName = "mppLabProbs";

    private static final Validator<Student> studentValidator = new StudentValidator();
    private static final Validator<Problem> problemValidator = new ProblemValidator();
    private static final Validator<Assignment> assignmentValidator = new AssignmentValidator();

    /**
     * Student Repository Bean
     * @see Student
     * @return GenericJdbcTemplateRepository
     */
    @Bean
    public GenericJdbcTemplateRepository<Student> studentRepository() {
        return new GenericJdbcTemplateRepository<Student>(studentValidator,
                host, password, user, dataBaseName, studentTable, studentClass);
    }

    /**
     * Problem Repository Bean
     * @see Problem
     * @return GenericJdbcTemplateRepository
     */
    @Bean
    public GenericJdbcTemplateRepository<Problem> problemRepository() {
        return new GenericJdbcTemplateRepository<Problem>(problemValidator,
                host, password, user, dataBaseName, problemTable, problemClass);
    }

    /**
     * Assignment Repository Bean
     * @see Assignment
     * @return GenericJdbcTemplateRepository
     */
    @Bean
    public GenericJdbcTemplateRepository<Assignment> assignmentRepository() {
        return new GenericJdbcTemplateRepository<Assignment>(assignmentValidator,
                host, password, user, dataBaseName, assignmentTable, assignmentClass);
    }
}
