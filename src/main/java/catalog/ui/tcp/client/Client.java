package catalog.ui.tcp.client;

public class Client {
    private static final int PORT = 1504;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        TCPClient client = new TCPClient(HOST, PORT);
        client.start();
    }
}
