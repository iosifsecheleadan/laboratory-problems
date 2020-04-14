package repository.spring;

import domain.entities.Assignment;
import domain.entities.Problem;
import domain.entities.Student;
import domain.validators.AssignmentValidator;
import domain.validators.ProblemValidator;
import domain.validators.StudentValidator;
import domain.validators.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public GenericJDBCTemplateRepository<Student> studentRepository() {
        return new GenericJDBCTemplateRepository<Student>(studentValidator,
                host, password, user, dataBaseName, studentTable, studentClass);
    }

    @Bean
    public GenericJDBCTemplateRepository<Problem> problemRepository() {
        return new GenericJDBCTemplateRepository<Problem>(problemValidator,
                host, password, user, dataBaseName, problemTable, problemClass);
    }

    @Bean
    public GenericJDBCTemplateRepository<Assignment> assignmentRepository() {
        return new GenericJDBCTemplateRepository<Assignment>(assignmentValidator,
                host, password, user, dataBaseName, assignmentTable, assignmentClass);
    }
}
