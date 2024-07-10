package halatsiankova.javafromscratch.config;

import halatsiankova.javafromscratch.connection.ConnectionDataBasePSQL;
import halatsiankova.javafromscratch.repository.TicketRepositoryImpl;
import halatsiankova.javafromscratch.repository.UserRepositoryImpl;
import halatsiankova.javafromscratch.service.TicketService;
import halatsiankova.javafromscratch.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ConnectionDataBasePSQL connectionDataBasePSQL() {
        return new ConnectionDataBasePSQL();
    };

    @Bean
    public UserRepositoryImpl userRepositoryImpl() {
        return new UserRepositoryImpl();
    }

    @Bean
    public TicketRepositoryImpl ticketRepositoryImpl() {
        return new TicketRepositoryImpl();
    }

    @Bean
    public TicketService ticketService() {
        return new TicketService(ticketRepositoryImpl());
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepositoryImpl());
    }
}
