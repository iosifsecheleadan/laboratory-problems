package ui.tcp.server;

import domain.entities.Assignment;
import domain.entities.Problem;
import domain.entities.Student;

import domain.validators.AssignmentValidator;
import domain.validators.ProblemValidator;
import domain.validators.StudentValidator;
import domain.validators.Validator;

import repository.GenericDataBaseRepository;
import repository.Repository;

import service.AssignmentRepoService;
import service.ProblemRepoService;
import service.StudentRepoService;
import service.AssignmentService;
import service.ProblemService;
import service.StudentService;

public class Server {
    private static final int PORT = 1504;

    public static void main(String[] args) {
        String studentClass = "domain.entities.Student";
        String labProblemClass = "domain.entities.Problem";
        String assignmentClass = "domain.entities.Assignment";

        String studentTable = "Student";
        String labProblemTable = "LabProblem";
        String assignmentTable = "Assignment";

        String host = "localhost";
        String password = "ubbfmi2018";
        String user = "school";
        String dataBaseName = "mppLabProbs";

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Problem> labProblemValidator = new ProblemValidator();
        Validator<Assignment> assignmentValidator = new AssignmentValidator();

        Repository<Long, Student> studentRepository = new GenericDataBaseRepository<>(studentValidator,
                host, password, user, dataBaseName, studentTable, studentClass);
        Repository<Long, Problem> labProblemRepository = new GenericDataBaseRepository<>(labProblemValidator,
                host, password, user, dataBaseName, labProblemTable, labProblemClass);
        Repository<Long, Assignment> assignmentRepository = new GenericDataBaseRepository<>(assignmentValidator,
                host, password, user, dataBaseName, assignmentTable, assignmentClass);

        StudentService studentService = new StudentRepoService(studentRepository);
        ProblemService problemService = new ProblemRepoService(labProblemRepository);
        AssignmentService assignmentService = new AssignmentRepoService(assignmentRepository, studentRepository,
                                                                    labProblemRepository);

        TCPServer tcpServer = new TCPServer(PORT, studentService, problemService, assignmentService);
        tcpServer.start();
    }
}
