package ui.tcp.server;

import domain.Assignment;
import domain.LabProblem;
import domain.Student;
import domain.validators.AssignmentValidator;
import domain.validators.LabProblemValidator;
import domain.validators.StudentValidator;
import domain.validators.Validator;
import repository.GenericDataBaseRepository;
import repository.Repository;
import service.AssignmentService;
import service.LabProblemService;
import service.StudentService;

public class Server {
    private static final int PORT = 1504;

    public static void main(String[] args) {
        String studentClass = "domain.Student";
        String labProblemClass = "domain.LabProblem";
        String assignmentClass = "domain.Assignment";

        String studentTable = "Student";
        String labProblemTable = "LabProblem";
        String assignmentTable = "Assignment";

        String host = "localhost";
        String password = "ubbfmi2018";
        String user = "school";
        String dataBaseName = "mppLabProbs";

        Validator<Student> studentValidator = new StudentValidator();
        Validator<LabProblem> labProblemValidator = new LabProblemValidator();
        Validator<Assignment> assignmentValidator = new AssignmentValidator();

        Repository<Long, Student> studentRepository = new GenericDataBaseRepository<>(studentValidator,
                host, password, user, dataBaseName, studentTable, studentClass);
        Repository<Long, LabProblem> labProblemRepository = new GenericDataBaseRepository<>(labProblemValidator,
                host, password, user, dataBaseName, labProblemTable, labProblemClass);
        Repository<Long, Assignment> assignmentRepository = new GenericDataBaseRepository<>(assignmentValidator,
                host, password, user, dataBaseName, assignmentTable, assignmentClass);

        StudentService studentService = new StudentService(studentRepository);
        LabProblemService labProblemService = new LabProblemService(labProblemRepository);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository, studentRepository,
                                                                    labProblemRepository);

        TCPServer tcpServer = new TCPServer(PORT, studentService, labProblemService, assignmentService);
        tcpServer.start();
    }
}
