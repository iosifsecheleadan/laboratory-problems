package catalog.ui.tcp.client;

import catalog.ui.tcp.common.Message;
import catalog.ui.tcp.common.MessageException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Command implements Runnable{
    private Thread thread;
    private Message result;
    //private boolean finished;
    private Message message;
    private Socket socket;

    public Command(Socket server, Message command) {
        this.socket = server;
        this.thread = new Thread(this);
        this.message = command;
        this.result = null;
        //this.finished = false;
    }

    /**
     * Start thread
     */
    public void start() {
        if (this.thread != null) {
            System.out.println("Started working.");
            this.thread.start();
        }
    }

    /**
     * Write command in socket output
     * Read result from socket input
     * Save result
     */
    @Override
    public void run() {
        try (InputStream inputStream = this.socket.getInputStream();
             OutputStream outputStream = this.socket.getOutputStream()) {

            this.message.writeTo(outputStream);
            this.message.readFrom(inputStream);
            this.result = new Message(this.message.getHead(), this.message.getBody());
            //this.finished = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if result found
     * @return boolean
     */
    public boolean isFinished() {
        return this.result != null;
        //return this.finished;
    }

    /**
     * Stop thread
     */
    public void close() {
        // this.thread.interrupt();
    }

    /**
     * Return result message separated by given separator
     * @param separator - String
     * @return String
     */
    public String toString(String separator) {
        if (this.isFinished()) {
            return this.message.toString().replaceAll(";", separator);
        } else {
            throw new MessageException("Result not yet obtained");
        }
    }

}
