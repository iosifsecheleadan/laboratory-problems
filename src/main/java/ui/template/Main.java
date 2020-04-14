package ui.template;

import domain.entities.Assignment;
import domain.entities.Problem;
import domain.entities.Student;

import repository.Repository;
import repository.spring.JdbcTemplateConfig;

import service.*;

import ui.console.Console;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(JdbcTemplateConfig.class);
        Repository<Long, Student> studentRepository = (Repository<Long, Student>) context.getBean("studentRepository");
        Repository<Long, Problem> labProblemRepository = (Repository<Long, Problem>) context.getBean("problemRepository");
        Repository<Long, Assignment> assignmentRepository = (Repository<Long, Assignment>) context.getBean("assignmentRepository");

        StudentService studentService = new StudentRepoService(studentRepository);
        ProblemService problemService = new ProblemRepoService(labProblemRepository);
        AssignmentService assignmentService = new AssignmentRepoService(assignmentRepository,
                studentRepository, labProblemRepository);

        Console userInterface = new Console(studentService, problemService, assignmentService);
        userInterface.runConsole();
    }
}
