package ui.rmi.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.AssignmentService;
import service.ProblemService;
import service.StudentService;

/**
 * Client Configuration Class
 * @author sechelea
 */
@Configuration
public class ClientConfiguration {
    private static final String host = "rmi://localhost:1099/";

    /**
     * Student Service Bean
     * @return RmiProxyFactoryBean
     */
    @Bean
    public RmiProxyFactoryBean studentRmiProxyFactoryBean() {
        RmiProxyFactoryBean service = new RmiProxyFactoryBean();
        service.setServiceInterface(StudentService.class);
        service.setServiceUrl(host + "StudentService");
        return service;
    }

    /**
     * Problem Service Bean
     * @return RmiProxyFactoryBean
     */
    @Bean
    public RmiProxyFactoryBean problemRmiProxyFactoryBean() {
        RmiProxyFactoryBean service = new RmiProxyFactoryBean();
        service.setServiceInterface(ProblemService.class);
        service.setServiceUrl(host + "ProblemService");
        return service;
    }

    /**
     * Assignment Service Bean
     * @return RmiProxyFactoryBean
     */
    @Bean
    public RmiProxyFactoryBean assignmentRmiProxyFactoryBean() {
        RmiProxyFactoryBean service = new RmiProxyFactoryBean();
        service.setServiceInterface(AssignmentService.class);
        service.setServiceUrl(host + "AssignmentService");
        return service;
    }
}
