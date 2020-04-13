package ui.tcp.common;

import java.io.*;

public class Message {
    private String head;
    private String body;

    public Message() {}

    public Message(String head, String body) {
        this.head = head;
        this.body = body;
    }

    public String getHead() {
        return this.head;
    }

    public String getBody() {
        return this.body;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream output = new DataOutputStream(outputStream);
        output.writeBytes(this.head + System.lineSeparator()
                + this.body + System.lineSeparator());
        output.flush();
    }

    public void readFrom(InputStream inputStream) throws IOException {
        DataInputStream input = new DataInputStream(inputStream);
        this.head = this.readLine(input);
        this.body = this.readLine(input);
    }

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

    @Override
    public String toString() {
        return "\n\tMessage:\n" +
                this.head + "\n" +
                this.body + "\n";
    }
}
