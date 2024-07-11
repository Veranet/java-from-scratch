package halatsiankova.javafromscratch.config;

import halatsiankova.javafromscratch.connection.ConnectionDataBasePSQL;
import halatsiankova.javafromscratch.repository.TicketRepositoryImpl;
import halatsiankova.javafromscratch.repository.UserRepositoryImpl;
import halatsiankova.javafromscratch.service.TicketService;
import halatsiankova.javafromscratch.service.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
public class ApplicationConfig {

    @Bean
    public ConnectionDataBasePSQL connectionDataBasePSQL(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String user,
            @Value("${spring.datasource.password}") String password) {
        return new ConnectionDataBasePSQL(url, user, password);
    }

    @Bean
    public UserRepositoryImpl userRepositoryImpl(ConnectionDataBasePSQL connectionDataBasePSQL) {
        return new UserRepositoryImpl(connectionDataBasePSQL);
    }

    @Bean
    public TicketRepositoryImpl ticketRepositoryImpl(ConnectionDataBasePSQL connectionDataBasePSQL) {
        return new TicketRepositoryImpl(connectionDataBasePSQL);
    }

    @Bean
    public TicketService ticketService(TicketRepositoryImpl ticketRepositoryImpl) {
        return new TicketService(ticketRepositoryImpl);
    }

    @Bean
    public UserService userService(UserRepositoryImpl userRepositoryImpl,
                                   @Value("${service.update-enabled}") boolean updateEnabled) {
        return new UserService(userRepositoryImpl, updateEnabled);
    }
}
