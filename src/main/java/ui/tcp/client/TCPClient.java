package ui.tcp.client;

import ui.tcp.common.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Class for handling and mapping User Input to Service results
 * @see Command
 * @author sechelea
 */
public class TCPClient {
    private final String HOST;
    private final int PORT;
    private Map<String, Command> commands;

    /**
     * Parametrized Constructor
     * @param host String
     * @param port int
     */
    public TCPClient(String host, int port) {
        this.HOST = host;
        this.PORT = port;
        this.commands = new HashMap<>();
    }

    /**
     * Allow user to:
     *      create and run multiple commands one after the other,
     *      and receive their results as they finish.
     */
    public void start() {
        Scanner console = new Scanner(System.in);
        while (true) {

            System.out.println("Give command:");
            String head = console.nextLine();
            if (head.equals("exit")) this.exit();
            System.out.println("Give arguments:");
            String body = console.nextLine();

            this.startNewCommand(new Message(head, body));
            this.printFoundSolutions();
        }
    }

    /**
     * Create, run and save command with given message
     * @param message - Message
     */
    private void startNewCommand(Message message) {
        try {
            Command command = new Command(new Socket(this.HOST, this.PORT), message);
            this.commands.put(message.getHead(), command);
            command.start();
        } catch (IOException e) {
            System.out.println("Connection to server lost.");
            System.exit(1);
        }
    }

    /**
     * Close, remove and print saved results to console
     */
    private void printFoundSolutions() {
        Iterator<Map.Entry<String, Command>> iterator = this.commands.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Command> entry = iterator.next();
            String name = entry.getKey();
            Command command = entry.getValue();

            if (command.isFinished()) {
                System.out.println("Result of command \"" + name + "\" found:"
                        + command.toString("\n"));
                iterator.remove();
            }
        }
    }

    /**
     * Stop all commands and close Client
     */
    private void exit() {
        for (Command command : this.commands.values()) {
            command.close();
        }
        System.exit(0);
    }
}
