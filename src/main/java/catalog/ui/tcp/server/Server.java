package catalog.ui.tcp.server;

import catalog.domain.Assignment;
import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.validators.AssignmentValidator;
import catalog.domain.validators.LabProblemValidator;
import catalog.domain.validators.StudentValidator;
import catalog.domain.validators.Validator;
import catalog.repository.GenericDataBaseRepository;
import catalog.repository.Repository;
import catalog.service.AssignmentService;
import catalog.service.LabProblemService;
import catalog.service.StudentService;

public class Server {
    private static final int PORT = 1504;

    public static void main(String[] args) {
        String studentClass = "catalog.domain.Student";
        String labProblemClass = "catalog.domain.LabProblem";
        String assignmentClass = "catalog.domain.Assignment";

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
