package catalog;

import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.StudentProblem;
import catalog.domain.validators.LabProblemValidator;
import catalog.domain.validators.StudentProblemValidator;
import catalog.domain.validators.StudentValidator;
import catalog.domain.validators.Validator;
import catalog.repository.*;
import catalog.service.LabProblemService;
import catalog.service.StudentProblemService;
import catalog.service.StudentService;
import catalog.ui.Console;

/**
 * Created by radu.
 */

public class Main {
    public static void main(String args[]) {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new GenericFileRepository<Student>(studentValidator, "./data/students.txt","catalog.domain.Student");
        StudentService studentService = new StudentService(studentRepository);

        Validator<LabProblem> labProblemValidator = new LabProblemValidator();
        Repository<Long, LabProblem> labProblemRepository = new GenericFileRepository<LabProblem>(labProblemValidator, "./data/labProblems.txt", "catalog.domain.LabProblem");
        LabProblemService labProblemService = new LabProblemService(labProblemRepository);

        Validator<StudentProblem> studentProblemValidator = new StudentProblemValidator();
        Repository<Long, StudentProblem> studentProblemRepository = new GenericFileRepository<StudentProblem>(studentProblemValidator, "./data/studentProblems.txt", "catalog.domain.StudentProblem");
        StudentProblemService studentProblemService = new StudentProblemService(studentProblemRepository, studentRepository, labProblemRepository);

        Console console = new Console(studentService, labProblemService, studentProblemService);
        console.runConsole();

        System.out.println("Class dismissed!");
    }
}
