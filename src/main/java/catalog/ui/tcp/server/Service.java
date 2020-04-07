package catalog.ui.tcp.server;

import catalog.domain.Assignment;
import catalog.domain.LabProblem;
import catalog.domain.Student;
import catalog.domain.validators.LaboratoryExeption;
import catalog.domain.validators.ValidatorException;
import catalog.repository.RepositoryException;
import catalog.service.AssignmentService;
import catalog.service.LabProblemService;
import catalog.service.StudentService;
import catalog.ui.console.WrongInputException;
import catalog.ui.tcp.common.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Service {
    private Socket socket;
    private StudentService studentService;
    private LabProblemService labProblemService;
    private AssignmentService assignmentService;

    public Service(Socket client, StudentService studentService, LabProblemService labProblemService, AssignmentService assignmentService) {
        this.socket = client;
        this.studentService = studentService;
        this.labProblemService = labProblemService;
        this.assignmentService = assignmentService;
    }

    /**
     * Read command from socket input
     * Compute result
     * Print result to socket output
     */
    public void start() {
        try (InputStream inputStream = this.socket.getInputStream();
             OutputStream outputStream = this.socket.getOutputStream()){

            Message message = new Message();
            message.readFrom(inputStream);
            System.out.println("Recieved message: " + message.getHead());

            message = this.run(message);

            message.writeTo(outputStream);
            System.out.println("Responded with: " + message.getHead());

        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }

    /**
     * Interpret message
     * Return appropriate response
     * @param message - Message
     * @return
     */
    private Message run(Message message) {
        try {
            String clientOption = message.getHead().split(" ")[0];
            switch (clientOption) {
                case "add"      :   return this.add(message);
                case "remove"   :   return this.remove(message);
                case "update"   :   return this.update(message);
                case "filter"   :   return this.filter(message);
                case "report"   :   return this.report(message);
                case "help"     :   return this.help();
                default:            return this.wrongInput();
            }
        } catch (WrongInputException
                | NullPointerException
                | ArrayIndexOutOfBoundsException e) {
            return this.wrongInput();
        } catch (RepositoryException e) {
            return new Message("Repository Error", e.getMessage());
        } catch (ValidatorException e) {
            return new Message("Validator Error", e.getMessage());
        } catch (LaboratoryExeption e) {
            return new Message("Laboratory Error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Message("Probably Wrong Input", e.getClass().getName() + " : " + e.getMessage());
        }
    }

    private Message add(Message message) {
        String clientOption = message.getHead().split(" ")[1];
        switch (clientOption) {
            case "student": {
                Student student = new Student(message.getBody());
                this.studentService.addStudent(student);
                return new Message("Added student", student.toString());
            } case "problem": {
                LabProblem labProblem = new LabProblem(message.getBody());
                this.labProblemService.addLabProblem(labProblem);
                return new Message("Added Problem", labProblem.toString());
            } case "assignment": {
                Assignment assignment = new Assignment(message.getBody());
                this.assignmentService.addAssignment(assignment);
                return new Message("Added Assignment", assignment.toString());
            } default: return this.wrongInput();
        }
    }

    private Message remove(Message message) {
        String clientOption = message.getHead().split(" ")[1];
        switch (clientOption) {
            case "student": {
                Student student = new Student(message.getBody());
                this.studentService.removeStudent(student);
                this.assignmentService.removeStudent(student);
                return new Message("Removed Student", student.toString());
            } case "problem": {
                LabProblem labProblem = new LabProblem(message.getBody());
                this.labProblemService.removeLabProblem(labProblem);
                this.assignmentService.removeProblem(labProblem);
                return new Message("Removed LabProblem", labProblem.toString());
            } case "assignment": {
                Assignment assignment = new Assignment(message.getBody());
                this.assignmentService.removeAssignment(assignment);
                return new Message("Removed Assignment", assignment.toString());
            } default: return this.wrongInput();
        }
    }

    private Message update(Message message) {
        String clientOption = message.getHead().split(" ")[1];
        switch (clientOption) {
            case "student": {
                Student student = new Student(message.getBody());
                this.studentService.updateStudent(student);
                return new Message("Updated Student with ID " + student.getId().toString(),
                        student.toString());
            } case "problem": {
                LabProblem labProblem = new LabProblem(message.getBody());
                this.labProblemService.updateLabProblem(labProblem);
                return new Message("Updated Problem with ID " + labProblem.getId().toString(),
                        labProblem.toString());
            } case "assignment": {
                Assignment assignment = new Assignment(message.getBody());
                this.assignmentService.updateAssignment(assignment);
                return new Message("Updated Assignment with ID " + assignment.getId().toString(),
                        assignment.toString());
            } default: return this.wrongInput();
        }
    }

    private Message filter(Message message) {
        String clientOption = message.getHead().split(" ")[1];
        switch (clientOption) {
            case "students": return this.filterStudents(message);
            case "problems": return this.filterProblems(message);
            default: return this.wrongInput();
        }
    }

    private Message filterStudents(Message message) {
        String clientAttribute = message.getHead().split(" ")[2];
        switch (clientAttribute) {
            case "name": {
                String clientValue = message.getHead().split(" ")[3];
                return new Message("Reporting Students with given Name",
                        this.studentService.filterByName(clientValue).stream()
                                .map(Student::toString)
                                .reduce("", (current, next) -> current + "; " + next));
            } case "group": {
                String clientValue = message.getHead().split(" ")[3];
                return new Message("Reporting Students in given Group",
                        this.studentService.filterByGroup(Integer.parseInt(clientValue)).stream()
                                .map(Student::toString)
                                .reduce("", (curent, next) -> curent + "; " + next));
            } case "problem": {
                String clientValue = message.getHead().split(" ")[3];
                return new Message("Reporting Students with given Problem Number",
                        this.assignmentService.filterByProblem(Integer.parseInt(clientValue)).stream()
                                .map(Student::toString)
                                .reduce("", (current, next) -> current + "; " + next));
            } case "all": {
                return new Message("Reporting all Students",
                        this.studentService.getAllStudents().stream()
                                .map(Student::toString)
                                .reduce("", (current, next) -> current + "; " + next));
            } default: return this.wrongInput();
        }
    }

    private Message filterProblems(Message message) {
        String clientAttribute = message.getHead().split(" ")[2];
        switch (clientAttribute) {
            case "name": {
                String clientValue = message.getHead().split(" ")[3];
                return new Message("Filtering Problems with given Name",
                        this.labProblemService.filterByName(clientValue).stream()
                                .map(LabProblem::toString)
                                .reduce("" ,(current, next) -> current + "; " + next));
            } case "description": {
                String clientValue = message.getHead().split(" ")[3];
                return new Message("Filtering Problems with given Description",
                        this.labProblemService.filterByDescription(clientValue).stream()
                                .map(LabProblem::toString)
                                .reduce("", (current, next) -> current + "; " + next));
            } case "student": {
                String clientValue = message.getHead().split(" ")[3];
                return new Message("Filtering Problems assigned to Student with given SerialNumber",
                        this.assignmentService.filterByStudent(clientValue).stream()
                                .map(LabProblem::toString)
                                .reduce("", (current, next) -> current + "; " + next));
            } case "all": {
                return new Message("Filtering all Problems",
                        this.labProblemService.getAllLabProblems().stream()
                                .map(LabProblem::toString)
                                .reduce("", (current, next) -> current + "; " + next));
            } default: return this.wrongInput();
        }
    }

    private Message report(Message message) {
        String clientOption = message.getHead().split(" ")[1];
        switch (clientOption) {
            case "students": return this.reportStudents(message);
            case "problems": return this.reportProblems(message);
            default: return this.wrongInput();
        }
    }

    private Message reportStudents(Message message) {
        String clientAttribute = message.getHead().split(" ")[2];
        String clientValue = message.getHead().split(" ")[3];
        switch (clientAttribute) {
            case "name": {
                return new Message("Reporting Students with given Name",
                        String.valueOf(this.studentService.filterByName(clientValue).size()));
            } case "group": {
                return new Message("Reporting Students in given Group",
                        String.valueOf(this.studentService.filterByGroup(Integer.parseInt(clientValue)).size()));
            } case "problem": {
                return new Message("Reporting Students with given Problem Number",
                        String.valueOf(this.assignmentService.filterByProblem(Integer.parseInt(clientValue)).size()));
            } case "all": {
                return new Message("Reporting all Students",
                        String.valueOf(this.studentService.getAllStudents().size()));
            } default: return this.wrongInput();
        }
    }

    private Message reportProblems(Message message) {
        String clientAttribute = message.getHead().split(" ")[2];
        String clientValue = message.getHead().split(" ")[3];
        switch (clientAttribute) {
            case "name": {
                return new Message("Reporting Problems with given Name",
                        String.valueOf(this.labProblemService.filterByName(clientValue).size()));
            } case "description": {
                return new Message("Reporting Problems with given Description",
                        String.valueOf(this.labProblemService.filterByDescription(clientValue).size()));
            } case "student": {
                return new Message("Reporting Problems assigned to Student with given SerialNumber",
                        String.valueOf(this.assignmentService.filterByStudent(clientValue)));
            } case "all": {
                return new Message("Reporting all Problems",
                        String.valueOf(this.labProblemService.getAllLabProblems().size()));
            } default: return this.wrongInput();
        }
    }

    private Message help() {
        return new Message("Help Menu",
                "Description : full command must be in command line and entity must be in arguments" +
                        "; Commands [add [student | problem | assignment] [entity]" +
                        "; | remove [student | problem | assignment] [entity]" +
                        "; | update [student | problem | assignment] [entity]" +
                        "; | filter [students | problems] [attribute] [value]" +
                        "; | report [students | problems] [attribute] [value]" +
                        "; | help]" +
                        "; Entities [student [ID,serialNumber,name,group]" +
                        "; | problem [ID,number,name,description]" +
                        "; | assignment [ID,studentID,problemID] ]");
    }

    private Message wrongInput() {
        return new Message("Wrong Input.", "Please Try Again.");
    }
}
