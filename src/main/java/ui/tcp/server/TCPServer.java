package ui.tcp.server;

import service.AssignmentService;
import service.LabProblemService;
import service.StudentService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private final int PORT;
    private final StudentService studentService;
    private final LabProblemService labProblemService;
    private final AssignmentService assignmentService;

    public TCPServer(int port, StudentService studentService, LabProblemService labProblemService, AssignmentService assignmentService) {
        this.PORT = port;
        this.studentService = studentService;
        this.labProblemService = labProblemService;
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
                Service service = new Service(client, this.studentService, this.labProblemService, this.assignmentService);
                service.start();
            }
        } catch (IOException e) {
            System.out.println("Could not set up Server on Port: " + this.PORT);
        }
    }
}
