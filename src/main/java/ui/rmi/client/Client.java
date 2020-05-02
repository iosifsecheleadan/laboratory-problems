package ui.rmi.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.AssignmentService;
import service.ProblemService;
import service.StudentService;
import ui.console.Console;

/**
 * Run Client in Console with Server configured Service
 * @see ClientConfiguration
 * @author sechelea
 */
public class Client {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ClientConfiguration.class);

        StudentService studentService = context.getBean(StudentService.class);
        ProblemService problemService =  context.getBean(ProblemService.class);
        AssignmentService assignmentService =  context.getBean(AssignmentService.class);

        Console userInterface = new Console(studentService, problemService, assignmentService);
        userInterface.run();
    }
}
