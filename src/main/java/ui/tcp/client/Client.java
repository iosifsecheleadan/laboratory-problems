package ui.tcp.client;

/**
 * Run Client in Console at specific host and port.
 * @see TCPClient
 * @author sechelea
 */
public class Client {
    private static final int PORT = 1504;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        TCPClient client = new TCPClient(HOST, PORT);
        client.start();
    }
}
