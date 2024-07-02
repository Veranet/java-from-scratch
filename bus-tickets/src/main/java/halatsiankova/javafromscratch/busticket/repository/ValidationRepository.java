package halatsiankova.javafromscratch.busticket.repository;

import halatsiankova.javafromscratch.busticket.model.ErrorEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ValidationRepository {
    private final Map<UUID, List<ErrorEntity>> validationErrors = new ConcurrentHashMap<>();

    public int countByTicketId() {
       return validationErrors.size();
    }

    public List<ErrorEntity> findAllErrors() {
        return validationErrors.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }

    public void deleteAll() {
        validationErrors.clear();
    }

    public void saveAll(List<ErrorEntity> errorEntities) {
        errorEntities.stream()
                .forEach(errorEntity -> validationErrors.computeIfAbsent(errorEntity.getTicketId(), k -> new ArrayList<>())
                        .add(errorEntity));
    }
}
