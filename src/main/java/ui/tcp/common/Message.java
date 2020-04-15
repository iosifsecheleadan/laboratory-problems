package ui.tcp.common;

import java.io.*;

/**
 * Class for handling input and output through Streams
 * @author sechelea
 */
public class Message {
    private String head;
    private String body;

    /**
     * Default Constructor
     */
    public Message() {}

    /**
     * Parametrized Constructor
     * @param head String
     * @param body String
     */
    public Message(String head, String body) {
        this.head = head;
        this.body = body;
    }

    /**
     * Get Message Head
     * @return String
     */
    public String getHead() {
        return this.head;
    }

    /**
     * Get Message Body
     * @return String
     */
    public String getBody() {
        return this.body;
    }

    /**
     * Write to given Output Stream
     * @param outputStream OutputStream
     * @throws IOException If cannot write to given OutputStream
     */
    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream output = new DataOutputStream(outputStream);
        output.writeBytes(this.head + System.lineSeparator()
                + this.body + System.lineSeparator());
        output.flush();
    }

    /**
     * Read from given Input Stream
     * Read one line into Message Head
     * Read another line into Message Body
     * @param inputStream InputStream
     * @throws IOException If cannot read from InputStream
     */
    public void readFrom(InputStream inputStream) throws IOException {
        DataInputStream input = new DataInputStream(inputStream);
        this.head = this.readLine(input);
        this.body = this.readLine(input);
    }

    /**
     * Read one line from an Input Stream
     * @param input DataInputStream
     * @return String
     * @throws IOException If cannot read from DataInputStream
     */
    private String readLine(DataInputStream input) throws IOException {
        StringBuilder buffer = new StringBuilder();
        int c;
        while (true) {
            c = input.read();
            if (c < 32 || c > 126) {
                return buffer.toString();
            } else {
                buffer.append((char) c);
            }
        }
    }

    /**
     * Get basic Message String
     * @return String
     */
    @Override
    public String toString() {
        return "\n\tMessage:\n" +
                this.head + "\n" +
                this.body + "\n";
    }
}
