package catalog.ui;

import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.StudentProblem;
import catalog.domain.validators.LaboratoryExeption;
import catalog.domain.validators.ValidatorException;
import catalog.repository.RepositoryException;
import catalog.service.LabProblemService;
import catalog.service.StudentProblemService;
import catalog.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author radu.
 */
public class Console {
    private StudentService studentService;
    private LabProblemService labProblemService;
    private StudentProblemService studentProblemService;

    private BufferedReader console;

    // todo : add, read, filter, print LabProblem like student
    // todo : add, read, filter, print StudentProblem like student

    public Console(StudentService studentService, LabProblemService labProblemService, StudentProblemService studentProblemService) {
        this.studentService = studentService;
        this.labProblemService = labProblemService;
        this.studentProblemService = studentProblemService;
        this.console = new BufferedReader(new InputStreamReader(System.in));
    }

    public void runConsole() {
        System.out.println("Type help for help.");
        String userInput;
        List<String> userOptions = new ArrayList<>();
        while (true) {
            try {

                userInput = this.console.readLine();
                userOptions = Arrays.asList(userInput.split(" "));
                switch (userOptions.get(0)) {
                    case "exit"     :                                           return;
                    case "help"     :   this.help();                            break;
                    case "add"      :   this.add(userOptions);                  break;
                    case "remove"   :   this.remove(userOptions);               break;
                    case "print"    :   this.print(userOptions);                break;
                    case "filter"   :   this.filter(userOptions);               break;
                    default:    System.out.println("Wrong input. Try again.");  break;
                }
            } catch (IOException impossible) {
                System.out.println("There was an input error. Please try again.");
            } catch (NullPointerException | WrongInputException e) {
                System.out.println("Wrong input. Try again.");
            } catch (RepositoryException ex) {
                System.out.println("Problem encountered at REPOSITORY level : \n\t" + ex.getMessage());
            } catch (ValidatorException ex) {
                System.out.println("Problem encountered while VALIDATING data : \n\t" + ex.getMessage());
            } catch (LaboratoryExeption ex) {
                System.out.println("Problem encountered in the application : \n\t" + ex.getMessage());
            }
        }
    }

    private void filter(List<String> userOptions) {

    }

    private void print(List<String> userOptions) {
        try {
            assert (userOptions.size() > 1);
            switch (userOptions.get(1)) {
                case "students"     : this.printStudents(); break;
                case "problems"     : this.printProblems(); break;
                default: throw new WrongInputException();
            }
        } catch (AssertionError ignored) {
            userOptions.add(this.readString("Give entities to print [students | problems]"));
            this.print(userOptions);
        }
    }

    private void printStudents() {
        Set<Student> students = studentService.getAllStudents();
        students.forEach(System.out::println);
    }

    private void printProblems() {
        Set<LabProblem> labProblems = this.labProblemService.getAllLabProblems();
        labProblems.forEach(System.out::println);
    }



    private String readString(String message) {
        try {
            System.out.println(message);
            return this.console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void remove(List<String> userOptions) {

    }

    private void add(List<String> remove) {

    }

    private void filterStudents() {
        System.out.println("filtered students (name containing 's2'):");
        Set<Student> students = studentService.filterByName("s2");
        students.forEach(System.out::println);
    }

    private void printAllStudents() {
    }

    private void addStudents() {
        while (true) {
            Student student = readStudent();
            if (student == null || student.getId() < 0) {
                break;
            }
            try {
                studentService.addStudent(student);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    private Student readStudent() {
        System.out.println("Read student {id,serialNumber, name, group}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());// ...
            String serialNumber = bufferRead.readLine();
            String name = bufferRead.readLine();
            int group = Integer.parseInt(bufferRead.readLine());// ...

            Student student = new Student(serialNumber, name, group);
            student.setId(id);

            return student;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    private void help() {
        System.out.println(
            "exit - Exits the program.\n" +
            "help - Displays this message.\n" +
            "add [student | problem | assignment] [data of entity] - Adds the given entity\n" +
            "remove [student | problem | assignment] [data of entity] - Removes the given entity\n" +
            "print [student | problem] - Prints all entities of chosen type\n" +
            "filter [student | problem | assignment] [type and data of filter] - Prints entities depending on given filter\n" +
            "If you are not familiar with these options, simply do not include them and the program will ask for them."
        );
    }

    private void exit() {
        System.exit(0);
    }
}
