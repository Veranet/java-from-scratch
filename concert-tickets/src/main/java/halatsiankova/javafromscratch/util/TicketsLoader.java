package halatsiankova.javafromscratch.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import halatsiankova.javafromscratch.model.Ticket;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;

public class TicketsLoader {
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private final static String PATH = "classpath:tickets.json";

    public TicketsLoader(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public List<Ticket> load() {
        Resource resource = resourceLoader.getResource(PATH);
        try {
            return objectMapper.readerForListOf(Ticket.class).readValue(resource.getFile());
        } catch (IOException ioException) {
            throw new IllegalStateException("Cannot find resource", ioException);
        }
    }
}
