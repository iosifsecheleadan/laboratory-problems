package catalog.ui;

import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.StudentProblem;
import catalog.domain.validators.LaboratoryExeption;
import catalog.domain.validators.Validator;
import catalog.domain.validators.ValidatorException;
import catalog.repository.RepositoryException;
import catalog.service.LabProblemService;
import catalog.service.StudentProblemService;
import catalog.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
                //System.out.println("Give command [add | remove | print | filter | report | help | exit]");
                System.out.println("Give command [add | remove | filter | report | help | exit]");
                userInput = this.console.readLine();
                userOptions = new ArrayList<>(Arrays.asList(userInput.split(" ")));
                switch (userOptions.get(0)) {
                    case "add"      :   this.add(userOptions);                  break;
                    case "remove"   :   this.remove(userOptions);               break;
                    //case "print"    :   this.print(userOptions);                break;
                    case "filter"   :   this.filter(userOptions);               break;
                    case "report"  :   this.report(userOptions);                break;
                    case "help"     :   this.help();                            break;
                    case "exit"     :   return;
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

    private void reportProblems(List<String> userOptions) {
        // todo
        try {
            if (userOptions.size() <= 2) throw new AssertionError();
            switch (userOptions.get(2)) {
                case "name": {
                    System.out.println("There are " +
                            this.labProblemService.filterByName(
                                    userOptions.get(3)).size() +
                            " Lab Problems with given name.");
                    break;
                } case "description": {
                    System.out.println("There are " +
                            this.labProblemService.filterByDescription(
                                    userOptions.get(3)).size() +
                            " Lab Problems with given description.");
                    break;
                } case "student": {
                    System.out.println("There are " +
                            this.studentProblemService.filterByStudent(
                                    userOptions.get(3)).size() +
                            " Lab Problems for given Student serial number.");
                    break;
                } case "all": {
                    System.out.println("There are " +
                            this.labProblemService.getAllLabProblems().size() +
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
                            this.studentProblemService.filterByProblem(Integer.parseInt(
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

    private void filter(List<String> userOptions) {
        // filter students containing given name or group
        // filter Lab Problems containing given name, description or number
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

    private void filterProblems(List<String> userOptions) {
        try {
            if (userOptions.size() <= 2) throw new AssertionError();
            switch (userOptions.get(2)) {
                case "name":{
                    System.out.println("Lab Problems with given name :");
                    this.labProblemService.filterByName(
                            userOptions.get(3))
                            .forEach(System.out::println);
                    break;
                } case "description": {
                    System.out.println("Lab Problems with given description :");
                    this.labProblemService.filterByDescription(
                            userOptions.get(3))
                            .forEach(System.out::println);
                    break;
                } case "student": {
                    System.out.println("Lab Problems assigned to Students with given serial number :");
                    this.studentProblemService.filterByStudent(
                            userOptions.get(3))
                            .forEach(System.out::println);
                    break;
                } case "all": {
                    System.out.println("All Lab Problems :");
                    this.labProblemService.getAllLabProblems()
                            .forEach(System.out::println);
                    break;
                }
                default: throw new WrongInputException();
            }
        } catch (AssertionError ignored) {
            userOptions.add(this.readString("Give filter type [name | description | student | all]"));
            this.filterProblems(userOptions);
        } catch (IndexOutOfBoundsException ignore) {
            userOptions.add(this.readString("Give value to filter by"));
            this.filterProblems(userOptions);
        }
    }

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
                    this.studentProblemService.filterByProblem(
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

/*
    private void print(List<String> userOptions) {
        try {
            if (userOptions.size() <= 1) throw new AssertionError();
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
*/

    private void remove(List<String> userOptions) {
        try {
            if (userOptions.size() <= 1) throw new AssertionError();
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
            if (userOptions.size() <= 1) throw new AssertionError();
            switch (userOptions.get(1)) {
                case "student"      :
                    this.studentService.addStudent(new Student(
                            this.readString("Give student values separated by \",\"\n\t" +
                                    "[ID,serialNumber,name,group]")));
                    break;
                case "problem"      :
                    this.labProblemService.addLabProblem(new LabProblem(
                            this.readString("Give problem values separated by \",\"\n\t" +
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
        } catch (ValidatorException exception) {
            throw new WrongInputException("Wrong input. " + exception.getMessage());
        } catch (RepositoryException exception) {
            throw new WrongInputException("Wrong input. " + exception.getMessage());
        }
    }

    private StudentProblem readAssignment() {
        try {
            return new StudentProblem(this.readString("Give assignment values separated by \",\"\n\t" +
                    "[ID,studentID,problemID]"));
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
            "\n\tWelcome to the Help Menu." +
            "\n\n\tThese are the possible commands:" +
            "\nexit - Exits the program." +
            "\nhelp - Displays this message." +
            "\nadd [student | problem | assignment] - Adds a given entity" +
            "\nremove [student | problem | assignment] - Removes a given entity" +
//            "\nprint [students | problems] - Prints all entities of chosen type" +
            "\nfilter [students | problems] [attribute] [value] - Prints entities depending on given filter" +
            "\nreport [students | problems] [attribute] [value] - Prints number of entities depending on given filter" +
            "\n\n\tAttributes : (used in filter and report)" +
            "\nattributes for students [name | group | problem | all] followed by value of attribute" +
            "\nattributes for problems [name | description | student | all] followed by value of attribute" +
            "\n\nIf you are not familiar with these options, simply do not include them and the program will ask for them."
        );
    }

    private void exit() {
        System.exit(0);
    }
}
