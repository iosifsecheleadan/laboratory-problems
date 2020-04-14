package ui.rmi.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.AssignmentService;
import service.ProblemService;
import service.StudentService;

@Configuration
public class ClientConfiguration {
    private static final String host = "rmi://localhost:1099/";

    @Bean
    public RmiProxyFactoryBean studentRmiProxyFactoryBean() {
        RmiProxyFactoryBean service = new RmiProxyFactoryBean();
        service.setServiceInterface(StudentService.class);
        service.setServiceUrl(host + "StudentService");
        return service;
    }

    @Bean
    public RmiProxyFactoryBean problemRmiProxyFactoryBean() {
        RmiProxyFactoryBean service = new RmiProxyFactoryBean();
        service.setServiceInterface(ProblemService.class);
        service.setServiceUrl(host + "ProblemService");
        return service;
    }

    @Bean
    public RmiProxyFactoryBean assignmentRmiProxyFactoryBean() {
        RmiProxyFactoryBean service = new RmiProxyFactoryBean();
        service.setServiceInterface(AssignmentService.class);
        service.setServiceUrl(host + "AssignmentService");
        return service;
    }
}
