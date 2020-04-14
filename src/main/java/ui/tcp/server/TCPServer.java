package ui.tcp.server;

import service.AssignmentService;
import service.ProblemService;
import service.StudentService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private final int PORT;
    private final StudentService studentService;
    private final ProblemService problemService;
    private final AssignmentService assignmentService;

    public TCPServer(int port, StudentService studentService, ProblemService problemService, AssignmentService assignmentService) {
        this.PORT = port;
        this.studentService = studentService;
        this.problemService = problemService;
        this.assignmentService = assignmentService;
    }

    /**
     * Handle incoming commands one after the other, sequentially.
     */
    public void start() {
        try (ServerSocket server = new ServerSocket(this.PORT)) {
            System.out.println("Server running at port: " + this.PORT);
            while (true) {
                Socket client = server.accept();
                Service service = new Service(client, this.studentService, this.problemService, this.assignmentService);
                service.start();
            }
        } catch (IOException e) {
            System.out.println("Could not set up Server on Port: " + this.PORT);
        }
    }
}
