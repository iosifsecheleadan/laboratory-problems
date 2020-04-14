package ui.rmi.client;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.AssignmentService;
import service.ProblemService;
import service.StudentService;
import ui.console.Console;

public class Client {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ClientConfiguration.class);

        StudentService studentService = (StudentService) context.getBean(StudentService.class);
        ProblemService problemService = (ProblemService) context.getBean(ProblemService.class);
        AssignmentService assignmentService = (AssignmentService) context.getBean(AssignmentService.class);

        Console userInterface = new Console(studentService, problemService, assignmentService);
        userInterface.runConsole();
    }
}
