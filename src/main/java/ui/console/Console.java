package ui.console;

import domain.entities.Problem;
import domain.entities.Student;
import domain.entities.Assignment;

import domain.validators.LaboratoryException;
import domain.validators.ValidatorException;

import repository.RepositoryException;

import service.AssignmentService;
import service.ProblemService;
import service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Class for handling and mapping User Input to specific Service calls
 * @author sechelea
 */
public class Console implements Runnable{
    private final StudentService studentService;
    private final ProblemService problemService;
    private final AssignmentService assignmentService;

    private final BufferedReader console;

    /**
     * Parametrized Constructor
     * @param studentService StudentService
     * @param problemService ProblemService
     * @param assignmentService AssignmentService
     */
    public Console(StudentService studentService, ProblemService problemService, AssignmentService assignmentService) {
        this.studentService = studentService;
        this.problemService = problemService;
        this.assignmentService = assignmentService;
        this.console = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Take and handle User Input indefinitely
     */
    @Override
    public void run() {
        System.out.println("Type help for help.");
        String userInput;
        List<String> userOptions = new ArrayList<>();
        while (true) {
            try {
                System.out.println("Give command [add | remove | update | filter | report | help | exit]");
                userInput = this.console.readLine();
                userOptions = new ArrayList<>(Arrays.asList(userInput.split(" ")));
                switch (userOptions.get(0)) {
                    case "add"      :   this.add(userOptions);                  break;
                    case "remove"   :   this.remove(userOptions);               break;
                    case "update"   :   this.update(userOptions);               break;
                    case "filter"   :   this.filter(userOptions);               break;
                    case "report"   :   this.report(userOptions);               break;
                    case "help"     :   this.help();                            break;
                    case "exit"     :   this.exit();                            return;
                    default:    System.out.println("Wrong input. Try again.");  break;
                }
            } catch (IOException impossible) {
                System.out.println("There was an input error. Please try again.");
            } catch (WrongInputException e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Something not initialized correctly.");
            } catch (RepositoryException ex) {
                System.out.println("Problem encountered at REPOSITORY level : \n\t" + ex.getMessage());
            } catch (ValidatorException ex) {
                System.out.println("Problem encountered while VALIDATING data : \n\t" + ex.getMessage());
            } catch (LaboratoryException ex) {
                System.out.println("Problem encountered in the application : \n\t" + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Probably wrong input. Here's the error :\n\t" + ex.getClass() + " - " + ex.getMessage());
            }
        }
    }

    /**
     * Handle "report" call with given options
     * @param userOptions List
     */
    private void report(List<String> userOptions) {
        try {
            if (userOptions.size() <= 1) throw new AssertionError();
            switch (userOptions.get(1)) {
                case "students" : this.reportStudents(userOptions); break;
                case "problems" :this.reportProblems(userOptions); break;
                default: throw new WrongInputException();
            }
        } catch (AssertionError | IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give entities to report on [students | problems]"));
            this.report(userOptions);
        }
    }

    /**
     * Handle "report problems" call with given options
     * @param userOptions List
     */
    private void reportProblems(List<String> userOptions) {
        try {
            if (userOptions.size() <= 2) throw new AssertionError();
            switch (userOptions.get(2)) {
                case "name": {
                    System.out.println("There are " +
                            this.problemService.filterByName(
                                    userOptions.get(3)).size() +
                            " Lab Problems with given name.");
                    break;
                } case "description": {
                    System.out.println("There are " +
                            this.problemService.filterByDescription(
                                    userOptions.get(3)).size() +
                            " Lab Problems with given description.");
                    break;
                } case "student": {
                    System.out.println("There are " +
                            this.assignmentService.filterByStudent(
                                    userOptions.get(3)).size() +
                            " Lab Problems for given Student serial number.");
                    break;
                } case "all": {
                    System.out.println("There are " +
                            this.problemService.getAllProblems().size() +
                            " Lab Problems.");
                    break;
                } default: throw new WrongInputException();
            }
        } catch (AssertionError ignored) {
            userOptions.add(this.readString("Give filter type [name | description | student | all]"));
            this.reportProblems(userOptions);
        } catch (IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give value to report by"));
            this.reportProblems(userOptions);
        }
    }

    /**
     * Handle "report students" call with given options
     * @param userOptions List
     */
    private void reportStudents(List<String> userOptions) {
        try {
            if (userOptions.size() <= 2) throw new AssertionError();
            switch (userOptions.get(2)) {
                case "name": {
                    System.out.println("There are " +
                            this.studentService.filterByName(
                                    userOptions.get(3)).size() +
                            " Students with given name.");
                    break;
                } case "group": {
                    System.out.println("There are " +
                            this.studentService.filterByGroup(Integer.parseInt(
                                    userOptions.get(3))).size() +
                            " Students in given group.");
                    break;
                } case "problem": {
                    System.out.println("There are " +
                            this.assignmentService.filterByProblem(Integer.parseInt(
                                    userOptions.get(3))).size() +
                            " Students with given problem number.");
                    break;
                } case "all": {
                    System.out.println("There are " +
                            this.studentService.getAllStudents().size() +
                            " Students.");
                    break;
                }
                default: throw new WrongInputException();
            }
        } catch (AssertionError ignored) {
            userOptions.add(this.readString("Give report type [name | group | problem | all]"));
            this.reportStudents(userOptions);
        } catch (IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give value to report by"));
            this.reportStudents(userOptions);
        }
    }

    /**
     * Handle "filter" call with given options
     * @param userOptions List
     */
    private void filter(List<String> userOptions) {
        try {
            if (userOptions.size() <= 1) throw new AssertionError();
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

    /**
     * Handle "filter problems" call with given options
     * @param userOptions List
     */
    private void filterProblems(List<String> userOptions) {
        try {
            if (userOptions.size() <= 2) throw new AssertionError();
            switch (userOptions.get(2)) {
                case "name":{
                    System.out.println("Lab Problems with given name :");
                    this.problemService.filterByName(
                            userOptions.get(3))
                            .forEach(System.out::println);
                    break;
                } case "description": {
                    System.out.println("Lab Problems with given description :");
                    this.problemService.filterByDescription(
                            userOptions.get(3))
                            .forEach(System.out::println);
                    break;
                } case "student": {
                    System.out.println("Lab Problems assigned to Students with given serial number :");
                    this.assignmentService.filterByStudent(
                            userOptions.get(3))
                            .forEach(System.out::println);
                    break;
                } case "all": {
                    System.out.println("All Lab Problems :");
                    this.problemService.getAllProblems()
                            .forEach(System.out::println);
                    break;
                }
                default: throw new WrongInputException();
            }
        } catch (AssertionError ignored) {
            userOptions.add(this.readString("Give filter type [name | description | student | all]"));
            this.filterProblems(userOptions);
        } catch (IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give value to filter by"));
            this.filterProblems(userOptions);
        }
    }

    /**
     * Handle "report students" call with given options
     * @param userOptions List
     */
    private void filterStudents(List<String> userOptions) {
        try {
            if (userOptions.size() <= 2) throw new AssertionError();
            switch (userOptions.get(2)) {
                case "name": {
                    System.out.println("Students with given name :");
                    this.studentService.filterByName(
                            userOptions.get(3))
                            .forEach(System.out::println);
                    break;
                } case "group": {
                    System.out.println("Students with given group number :");
                    this.studentService.filterByGroup(
                            Integer.parseInt(userOptions.get(3)))
                            .forEach(System.out::println);
                    break;
                } case "problem": {
                    System.out.println("Students assigned with given problem number :");
                    this.assignmentService.filterByProblem(
                            Integer.parseInt(userOptions.get(3)))
                            .forEach(System.out::println);
                    break;
                } case "all": {
                    System.out.println("All Students :");
                    this.studentService.getAllStudents()
                            .forEach(System.out::println);
                    break;
                }
                default: throw new WrongInputException();
            }
        } catch (AssertionError ignored) {
            userOptions.add(this.readString("Give filter type [name | group | problem | all]"));
            this.filterStudents(userOptions);
        } catch (IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give value to filter by"));
            this.filterStudents(userOptions);
        }
    }

    /**
     * Handle "remove" call with given options
     * @param userOptions List
     */
    private void remove(List<String> userOptions) {
        try {
            if (userOptions.size() <= 1) throw new AssertionError();
            switch (userOptions.get(1)) {
                case "student"      :
                    Student student = this.studentService.getBySerialNumber(
                            this.readString("Give student serial number")).get();
                    this.studentService.removeStudent(student);
                    this.assignmentService.removeStudent(student);
                    break;
                case "problem"      :
                    Problem problem = this.problemService.getByProblemNumber(
                            this.readInt("Give problem number")).get();
                    this.problemService.removeLabProblem(problem);
                    this.assignmentService.removeProblem(problem);
                    break;
                case "assignment"   :
                    this.assignmentService.removeAssignment(
                            this.readAssignment());
                    break;
                default: throw new WrongInputException();
            }
        } catch (AssertionError | IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give entities to remove [student | problem | assignment]"));
            this.remove(userOptions);
        } catch (NoSuchElementException ignored) {
            throw new WrongInputException();
        }
    }

    /**
     * Handle "add" call with given options
     * @param userOptions List
     */
    private void add(List<String> userOptions) {
        try {
            if (userOptions.size() <= 1) throw new AssertionError();
            switch (userOptions.get(1)) {
                case "student"      :
                    this.studentService.addStudent(new Student(
                            this.readString("Give student values separated by \",\"\n\t" +
                                    "[ID,serialNumber,name,group]")));
                    break;
                case "problem"      :
                    this.problemService.addLabProblem(new Problem(
                            this.readString("Give problem values separated by \",\"\n\t" +
                                    "[ID,number,name,description]")));
                    break;
                case "assignment"   :
                    this.assignmentService.addAssignment(
                            this.readAssignment());
                    break;
                default: throw new WrongInputException();
            }
        } catch (AssertionError | IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give entities to add [student | problem | assignment]"));
            this.add(userOptions);
        } catch (ValidatorException | RepositoryException exception) {
            throw new WrongInputException("Wrong input. " + exception.getMessage());
        }
    }

    /**
     * Handle "update" call with given options
     * @param userOptions List
     */
    private void update(List<String> userOptions) {
        try {
            if (userOptions.size() <= 1) throw new AssertionError();
            switch (userOptions.get(1)) {
                case "student" :
                    this.studentService.updateStudent(new Student(
                            this.readString("Give student values separated by \",\"\n\t" +
                                    "[ID,serialNumber,name,group]")));
                    break;
                case "problem" :
                    this.problemService.updateLabProblem(new Problem(
                            this.readString("Give problem values separated by \",\"\n\t" +
                                    "[ID,number,name,description]")));
                    break;
                case "assignment" :
                    this.assignmentService.updateAssignment(
                            this.readAssignment());
                    break;
                default: throw new WrongInputException();
            }
        }  catch (AssertionError | IndexOutOfBoundsException ignored) {
            userOptions.add(this.readString("Give entities to update [student | problem | assignment]"));
            this.add(userOptions);
        } catch (ValidatorException | RepositoryException exception) {
            throw new WrongInputException("Wrong input. " + exception.getMessage());
        }
    }

    /**
     * Read an Assignment from Console
     * @return Assignment
     */
    private Assignment readAssignment() {
        try {
            return new Assignment(this.readString("Give assignment values separated by \",\"\n\t" +
                    "[ID,studentID,problemID]"));
        } catch (NoSuchElementException ignored) {
            throw new WrongInputException();
        }
    }

    /**
     * Give a message to Console
     * <br> Read a string from Console
     * @param message String
     * @return String
     */
    private String readString(String message) {
        try {
            System.out.println(message);
            return this.console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Give a message to Console
     * <br> Read an Integer from Console
     * @param message String
     * @return String
     */
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

    /**
     * Print Help Message to Console
     */
    private void help() {
        System.out.println(
            "\n\tWelcome to the Help Menu." +
            "\n\n\tThese are the possible commands:" +
            "\nexit - Exits the program." +
            "\nhelp - Displays this message." +
            "\nadd [student | problem | assignment] - Adds a given entity" +
            "\nupdate [student | problem | assignment] - Updates a given entity" +
            "\nremove [student | problem | assignment] - Removes a given entity" +
            "\nfilter [students | problems] [attribute] [value] - Prints entities depending on given filter" +
            "\nreport [students | problems] [attribute] [value] - Prints number of entities depending on given filter" +
            "\n\n\tAttributes : (used in filter and report)" +
            "\nattributes for students [name | group | problem | all] followed by value of attribute" +
            "\nattributes for problems [name | description | student | all] followed by value of attribute" +
            "\n\nIf you are not familiar with these options, simply do not include them and the program will ask for them."
        );
    }

    /**
     * Close all Services
     */
    private void exit() {
        this.problemService.close();
        this.studentService.close();
        this.assignmentService.close();
    }
}
