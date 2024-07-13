package halatsiankova.javafromscratch.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import halatsiankova.javafromscratch.util.TicketsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;

@Configuration
@PropertySource("classpath:application.yml")
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public TicketsLoader ticketsLoader(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        return new TicketsLoader(resourceLoader, objectMapper);
    }
}
