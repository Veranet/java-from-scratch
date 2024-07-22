package halatsiankova.javafromscratch.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;

public class DataLoader {
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public DataLoader(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public <T> List<T> load(String path, Class<T> type) {
        Resource resource = resourceLoader.getResource(path);
        try {
            return objectMapper.readerForListOf(type).readValue(resource.getFile());
        } catch (IOException ioException) {
            throw new IllegalStateException("Cannot find resource", ioException);
        }
    }
}
