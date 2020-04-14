package ui.console;

import domain.entities.Problem;
import domain.entities.Student;
import domain.entities.Assignment;

import domain.validators.ProblemValidator;
import domain.validators.AssignmentValidator;
import domain.validators.StudentValidator;
import domain.validators.Validator;

import repository.GenericDataBaseRepository;
import repository.GenericXMLRepository;
import repository.Repository;
import repository.GenericFileRepository;

import service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        String studentClass = "domain.entities.Student";
        String problemClass = "domain.entities.Problem";
        String assignmentClass = "domain.entities.Assignment";

        String studentFile = "./data/students.txt";
        String problemFile = "./data/labProblems.txt";
        String assignmentFile = "./data/assignments.txt";

        String studentXML = "./data/students.xml";
        String labProblemXML = "./data/labProblems.xml";
        String assignmentXML = "./data/assignments.xml";

        String studentTable = "Student";
        String problemTable = "LabProblem";
        String assignmentTable = "Assignment";

        String host = "localhost";
        String password = "ubbfmi2018";
        String user = "school";
        String dataBaseName = "mppLabProbs";

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Problem> problemValidator = new ProblemValidator();
        Validator<Assignment> assignmentValidator = new AssignmentValidator();

        Repository<Long, Student> studentRepository = null;
        Repository<Long, Problem> labProblemRepository = null;
        Repository<Long, Assignment> assignmentRepository = null;

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (studentRepository == null) {
            System.out.println("Choose where to store your data [file | xml | database]");
            try {
                switch (console.readLine()) {
                    case "file": {
                        studentRepository = new GenericFileRepository<>(studentValidator,
                                studentFile, studentClass);
                        labProblemRepository = new GenericFileRepository<>(problemValidator,
                                problemFile, problemClass);
                        assignmentRepository = new GenericFileRepository<>(assignmentValidator,
                                assignmentFile, assignmentClass);
                        break;
                    } case "xml": {
                        studentRepository = new GenericXMLRepository<>(studentValidator,
                                studentXML, studentClass);
                        labProblemRepository = new GenericXMLRepository<>(problemValidator,
                                labProblemXML, problemClass);
                        assignmentRepository = new GenericXMLRepository<>(assignmentValidator,
                                assignmentXML, assignmentClass);
                        break;
                    } case "database": {
                        studentRepository = new GenericDataBaseRepository<>(studentValidator,
                                host, password, user, dataBaseName, studentTable, studentClass);
                        labProblemRepository = new GenericDataBaseRepository<>(problemValidator,
                                host, password, user, dataBaseName, problemTable, problemClass);
                        assignmentRepository = new GenericDataBaseRepository<>(assignmentValidator,
                                host, password, user, dataBaseName, assignmentTable, assignmentClass);
                        break;
                    } default : System.out.println("Wrong input. Try again."); break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        StudentService studentService = new StudentRepoService(studentRepository);
        ProblemService problemService = new ProblemRepoService(labProblemRepository);
        AssignmentService assignmentService = new AssignmentRepoService(assignmentRepository,
                studentRepository, labProblemRepository);

        Console userInterface = new Console(studentService, problemService, assignmentService);
        userInterface.runConsole();

        System.out.println("Class dismissed!");
    }
}
