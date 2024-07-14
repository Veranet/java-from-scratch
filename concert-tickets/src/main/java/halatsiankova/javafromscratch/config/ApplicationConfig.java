package halatsiankova.javafromscratch.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import halatsiankova.javafromscratch.util.DataLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

@Configuration
@PropertySource("classpath:application.yml")
public class ApplicationConfig {


    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder().addModule(new JavaTimeModule()).build();
    }

    @Bean
    public DataLoader ticketsLoader(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        return new DataLoader(resourceLoader, objectMapper);
    }

    @Bean
    @ConditionalOnProperty(name = "conditional.string-enabled", havingValue = "true")
    public ThisIsMyFirstConditionalBean thisIsMyFirstConditional(
            @Value("${conditional.string}") String conditionalString) {
        return new ThisIsMyFirstConditionalBean(conditionalString);
    }

    @Bean
    public ConversionService conversionService(List<Converter<?, ?>> converters) {
        ApplicationConversionService conversionService = new ApplicationConversionService();
        converters.forEach(conversionService::addConverter);
        return conversionService;
    }
}
