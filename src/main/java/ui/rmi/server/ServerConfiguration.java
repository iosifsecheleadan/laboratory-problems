package ui.rmi.server;

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

import repository.GenericDataBaseRepository;
import repository.Repository;

import service.*;

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

    private static final Repository<Long, Student> studentRepository = new GenericDataBaseRepository<Student>(studentValidator,
            host, password, user, dataBaseName, studentTable, studentClass);
    private static final Repository<Long, Problem> problemRepository = new GenericDataBaseRepository<Problem>(problemValidator,
            host, password, user, dataBaseName, problemTable, problemClass);
    private static final Repository<Long, Assignment> assignmentRepository = new GenericDataBaseRepository<Assignment>(assignmentValidator,
            host, password, user, dataBaseName, assignmentTable, assignmentClass);

    private static final StudentService studentService = new StudentRepoService(studentRepository);
    private static final ProblemService problemService = new ProblemRepoService(problemRepository);
    private static final AssignmentService assignmentService = new AssignmentRepoService(assignmentRepository,
            studentRepository, problemRepository);

    @Bean
    public RmiServiceExporter studentRmiServiceExporter() {
        RmiServiceExporter service = new RmiServiceExporter();
        service.setServiceName("StudentService");
        service.setServiceInterface(StudentService.class);
        service.setService(studentService);
        // service.setService(new StudentRepoService(studentRepository));
        return service;
    }
    @Bean
    public StudentService studentService() {
        return new StudentRepoService(studentRepository);
    }

    @Bean
    public RmiServiceExporter problemRmiServiceExporter() {
        RmiServiceExporter service = new RmiServiceExporter();
        service.setServiceName("ProblemService");
        service.setServiceInterface(ProblemService.class);
        service.setService(problemService);
        // service.setService(new ProblemRepoService(problemRepository));
        return service;
    }
    @Bean
    public ProblemService problemService() {
        return new ProblemRepoService(problemRepository);
    }

    @Bean
    public RmiServiceExporter assignmentRmiServiceExporter() {
        RmiServiceExporter service = new RmiServiceExporter();
        service.setServiceName("AssignmentService");
        service.setServiceInterface(AssignmentService.class);
        service.setService(assignmentService);
        // service.setService(new AssignmentRepoService(assignmentRepository, studentRepository, problemRepository));
        return service;
    }
    @Bean
    public AssignmentService assignmentService() {
        return new AssignmentRepoService(assignmentRepository, studentRepository, problemRepository);
    }
}
