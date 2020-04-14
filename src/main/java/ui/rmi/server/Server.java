package ui.rmi.server;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Server {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ServerConfiguration.class);
        System.out.println("Server running");
    }
}
