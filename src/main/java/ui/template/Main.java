package ui.template;

import domain.entities.Assignment;
import domain.entities.Problem;
import domain.entities.Student;

import repository.Repository;
import repository.jdbcTemplate.JdbcTemplateConfig;

import service.AssignmentService;
import service.ProblemService;
import service.StudentService;
import service.repo.AssignmentRepoService;
import service.repo.ProblemRepoService;
import service.repo.StudentRepoService;
import ui.console.Console;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Run program in Console with JdbcTemplate configured Repository
 * @see JdbcTemplateConfig
 * @author sechelea
 */
public class Main {
    public static void main(String[] args) {
        // TODO Specifications for whole project
        // TODO Tests for whole project

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
        userInterface.run();
    }
}
