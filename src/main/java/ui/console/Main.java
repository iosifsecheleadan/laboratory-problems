package ui.console;

import domain.LabProblem;
import domain.Student;
import domain.Assignment;

import domain.validators.LabProblemValidator;
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
    public static void main(String args[]) {

        String studentClass = "domain.Student";
        String labProblemClass = "domain.LabProblem";
        String assignmentClass = "domain.Assignment";

        String studentFile = "./data/students.txt";
        String labProblemFile = "./data/labProblems.txt";
        String assignmentFile = "./data/assignments.txt";

        String studentXML = "./data/students.xml";
        String labProblemXML = "./data/labProblems.xml";
        String assignmentXML = "./data/assignments.xml";

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

        Repository<Long, Student> studentRepository = null;
        Repository<Long, LabProblem> labProblemRepository = null;
        Repository<Long, Assignment> assignmentRepository = null;

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (studentRepository == null) {
            System.out.println("Choose where to store your data [file | xml | database]");
            try {
                switch (console.readLine()) {
                    case "file": {
                        studentRepository = new GenericFileRepository<>(studentValidator,
                                studentFile, studentClass);
                        labProblemRepository = new GenericFileRepository<>(labProblemValidator,
                                labProblemFile, labProblemClass);
                        assignmentRepository = new GenericFileRepository<>(assignmentValidator,
                                assignmentFile, assignmentClass);
                        break;
                    } case "xml": {
                        studentRepository = new GenericXMLRepository<>(studentValidator,
                                studentXML, studentClass);
                        labProblemRepository = new GenericXMLRepository<>(labProblemValidator,
                                labProblemXML, labProblemClass);
                        assignmentRepository = new GenericXMLRepository<>(assignmentValidator,
                                assignmentXML, assignmentClass);
                        break;
                    } case "database": {
                        studentRepository = new GenericDataBaseRepository<>(studentValidator,
                                host, password, user, dataBaseName, studentTable, studentClass);
                        labProblemRepository = new GenericDataBaseRepository<>(labProblemValidator,
                                host, password, user, dataBaseName, labProblemTable, labProblemClass);
                        assignmentRepository = new GenericDataBaseRepository<>(assignmentValidator,
                                host, password, user, dataBaseName, assignmentTable, assignmentClass);
                        break;
                    } default : System.out.println("Wrong input. Try again."); break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        StudentService studentService = new StudentService(studentRepository);
        LabProblemService labProblemService = new LabProblemService(labProblemRepository);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository,
                studentRepository, labProblemRepository);

        Console userInterface = new Console(studentService, labProblemService, assignmentService);
        userInterface.runConsole();

        System.out.println("Class dismissed!");
    }
}
