package ui.rmi.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import domain.entities.Assignment;
import domain.entities.Problem;
import domain.entities.Student;

import domain.validators.AssignmentValidator;
import domain.validators.ProblemValidator;
import domain.validators.StudentValidator;
import domain.validators.Validator;

import repository.jdbcTemplate.JdbcTemplateConfig;
import repository.sort.GenericDataBaseRepository;
import repository.Repository;

import service.AssignmentService;
import service.ProblemService;
import service.StudentService;
import service.repo.AssignmentRepoService;
import service.repo.ProblemRepoService;
import service.repo.StudentRepoService;

/**
 * Server Configuration Class
 * @author sechelea
 */
@Configuration
public class ServerConfiguration {
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

    private static final AnnotationConfigApplicationContext jdbcConfig =
            new AnnotationConfigApplicationContext(JdbcTemplateConfig.class);

    private static final Repository<Long, Student> studentRepository = (Repository<Long, Student>)
            jdbcConfig.getBean("studentRepository");
    private static final Repository<Long, Problem> problemRepository = (Repository<Long, Problem>)
            jdbcConfig.getBean("problemRepository");
    private static final Repository<Long, Assignment> assignmentRepository = (Repository<Long, Assignment>)
            jdbcConfig.getBean("assignmentRepository");

    private static final StudentService studentService = new StudentRepoService(studentRepository);
    private static final ProblemService problemService = new ProblemRepoService(problemRepository);
    private static final AssignmentService assignmentService = new AssignmentRepoService(assignmentRepository,
            studentRepository, problemRepository);

    /**
     * Student Service Bean
     * @return RmiServiceExporter
     */
    @Bean
    public RmiServiceExporter studentRmiServiceExporter() {

        RmiServiceExporter service = new RmiServiceExporter();
        service.setServiceName("StudentService");
        service.setServiceInterface(StudentService.class);
        service.setService(studentService);
        return service;
    }

    /**
     * Problem Service Bean
     * @return RmiServiceExporter
     */
    @Bean
    public RmiServiceExporter problemRmiServiceExporter() {
        RmiServiceExporter service = new RmiServiceExporter();
        service.setServiceName("ProblemService");
        service.setServiceInterface(ProblemService.class);
        service.setService(problemService);
        return service;
    }

    /**
     * Assignment Service Bean
     * @return RmiServiceExporter
     */
    @Bean
    public RmiServiceExporter assignmentRmiServiceExporter() {
        RmiServiceExporter service = new RmiServiceExporter();
        service.setServiceName("AssignmentService");
        service.setServiceInterface(AssignmentService.class);
        service.setService(assignmentService);
        return service;
    }

}
