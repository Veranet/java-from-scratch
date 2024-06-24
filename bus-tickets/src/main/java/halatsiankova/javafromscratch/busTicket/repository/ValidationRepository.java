package halatsiankova.javafromscratch.busTicket.repository;

import halatsiankova.javafromscratch.busTicket.model.ErrorEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ValidationRepository {
    private final Map<UUID, List<ErrorEntity>> validationErrors = new ConcurrentHashMap<>();

    public void save(ErrorEntity errorEntity) {
        validationErrors.computeIfAbsent(errorEntity.getTicketId(), k -> new ArrayList<>())
                .add(errorEntity);
    }

    public int countByTicketId() {
       return validationErrors.size();
    }

    public List<ErrorEntity> findAllErrors() {
        return validationErrors.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }
}
