package catalog.ui;

import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.StudentProblem;
import catalog.domain.validators.LaboratoryExeption;
import catalog.domain.validators.ValidatorException;
import catalog.repository.InMemoryRepository;
import catalog.repository.RepositoryException;
import catalog.service.LabProblemService;
import catalog.service.StudentProblemService;
import catalog.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author radu.
 */
public class Console {
    private StudentService studentService;
    private LabProblemService labProblemService;
    private StudentProblemService studentProblemService;

    private BufferedReader console;

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
                System.out.println("Give command [add | remove | print | filter | help | exit]");
                userInput = this.console.readLine();
                userOptions = new ArrayList<>(Arrays.asList(userInput.split(" ")));
                switch (userOptions.get(0)) {
                    case "exit"     :   return;
                    case "help"     :   this.help();                            break;
                    case "add"      :   this.add(userOptions);                  break;
                    case "remove"   :   this.remove(userOptions);               break;
                    case "print"    :   this.print(userOptions);                break;
                    case "filter"   :   this.filter(userOptions);               break;
                    default:    System.out.println("Wrong input. Try again.");  break;
                }
            } catch (IOException impossible) {
                System.out.println("There was an input error. Please try again.");
            } catch (WrongInputException e) {
                System.out.println("Wrong input. Try again.");
            } catch (NullPointerException e) {
                System.out.println("Something not initialized correctly.");
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
        // filter students containing given name or group
        // filter Lab Problems containing given name, description or number
        try {
            assert (userOptions.size() > 1);
            switch (userOptions.get(1)) {
                case "students"     : this.filterStudents(userOptions); break;
                case "problems"  : this.filterProblems(userOptions); break;
                default: throw new WrongInputException();
            }
        } catch (AssertionError | IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give entities to filter [students | problems]"));
            this.filter(userOptions);
        }
    }

    private void filterProblems(List<String> userOptions) {
        try {
            assert (userOptions.size() > 2);
            this.filterProblems(userOptions.get(2));
        } catch (AssertionError | IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give filter type [name | description | students]"));
            this.filterProblems(userOptions);
        }
    }

    private void filterProblems(String type) {
        switch (type) {
            case "name":{
                this.labProblemService.filterByName(
                        this.readString("Give problem name"))
                        .forEach(System.out::println);
                break;
            } case "description": {
                this.labProblemService.filterByDescription(
                        this.readString("Give problem description"))
                        .forEach(System.out::println);
                break;
            } case "students": {
                this.studentProblemService.filterByStudent(
                        this.readString("Give student serial number"))
                        .forEach(System.out::println);
                break;
            } default: throw new WrongInputException();
        }
    }

    private void filterStudents(List<String> userOptions) {
        try {
            assert (userOptions.size() > 2);
            this.filterStudents(userOptions.get(2));
        } catch (AssertionError | IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give filter type [name | group | problems]"));
            this.filterStudents(userOptions);
        }
    }

    private void filterStudents(String type) {
        switch (type) {
            case "name": {
                this.studentService.filterByName(
                        this.readString("Give student name"))
                        .forEach(System.out::println);
                break;
            } case "group": {
                this.studentService.filterByGroup(
                        this.readInt("Give student group"))
                        .forEach(System.out::println);
                break;
            } case "problem": {
                this.studentProblemService.filterByProblem(
                        this.readInt("Give problem number"))
                        .forEach(System.out::println);
                break;
            } default: throw new WrongInputException();
        }
    }


    private void print(List<String> userOptions) {
        try {
            assert (userOptions.size() > 1);
            switch (userOptions.get(1)) {
                case "students"     : this.printStudents(); break;
                case "problems"     : this.printProblems(); break;
                default: throw new WrongInputException();
            }
        } catch (AssertionError | IndexOutOfBoundsException ignored) {
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


    private void remove(List<String> userOptions) {
        try {
            assert (userOptions.size() > 1);
            switch (userOptions.get(1)) {
                case "student"      :
                    Student student = this.studentService.getBySerialNumber(
                            this.readString("Give student serial number")).get();
                    this.studentService.removeStudent(student);
                    this.studentProblemService.removeStudent(student);
                    break;
                case "problem"      :
                    LabProblem problem = this.labProblemService.getByProblemNumber(
                            this.readInt("Give problem number")).get();
                    this.labProblemService.removeLabProblem(problem);
                    this.studentProblemService.removeProblem(problem);
                    break;
                case "assignment"   :
                    this.studentProblemService.removeStudentProblem(
                            this.readAssignment());
                    break;
                default: throw new WrongInputException();
            }
        } catch (AssertionError | IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give entities to add [student | problem | assignment]"));
            this.remove(userOptions);
        } catch (NoSuchElementException ignored) {
            throw new WrongInputException();
        }
    }

    private void add(List<String> userOptions) {
        try {
            assert (userOptions.size() > 1);
            switch (userOptions.get(1)) {
                case "student"      :
                    this.studentService.addStudent(
                            new Student(this.readString("Give student values separated by \",\"\n" +
                                    "[ID,serialNumber,name,group]")));
                    break;
                case "problem"      :
                    this.labProblemService.addLabProblem(
                            new LabProblem(this.readString("Give problem values separated by \",\"\n" +
                                    "[ID,number,name,description]")));
                    break;
                case "assignment"   :
                    this.studentProblemService.addStudentProblem(
                            this.readAssignment());
                    break;
                default: throw new WrongInputException();
            }
        } catch (AssertionError | IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give entities to add [student | problem | assignment]"));
            this.add(userOptions);
        }
    }

    private StudentProblem readAssignment() {
        try {
            return new StudentProblem(this.readString("Give assignment values separated by \",\"" +
                    "[ID,studentID,problemID]\n"));
        } catch (NoSuchElementException ignored) {
            throw new WrongInputException();
        }
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


    private Integer readInt(String message) {
        try {
            System.out.println(message);
            return Integer.parseInt(this.console.readLine());
        } catch (IOException | NullPointerException e){
            e.printStackTrace();
            return null;
        } catch (NumberFormatException ignored) {
            throw new WrongInputException();
        }
    }

    private void help() {
        System.out.println(
            "exit - Exits the program.\n" +
            "help - Displays this message.\n" +
            "add [student | problem | assignment] [data of entity] - Adds the given entity\n" +
            "remove [student | problem | assignment] [data of entity] - Removes the given entity\n" +
            "print [student | problem] - Prints all entities of chosen type\n" +
            "filter [student | problem | assignment] [attribute of filter] - Prints entities depending on given filter\n" +
            "If you are not familiar with these options, simply do not include them and the program will ask for them."
        );
    }

    private void exit() {
        System.exit(0);
    }
}
