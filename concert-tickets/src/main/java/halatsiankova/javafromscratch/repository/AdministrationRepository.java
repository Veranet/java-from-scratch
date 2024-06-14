package halatsiankova.javafromscratch.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdministrationRepository {
    private final Map<Integer, List<String>> associatedTickets;

    public AdministrationRepository() {
        this.associatedTickets = new ConcurrentHashMap<>();
    }

    public void save(Integer userId, String ticketId) {
        associatedTickets.computeIfAbsent(userId, k -> new ArrayList<>()).add(ticketId);
    }

    public List<String> findAllByUserId(Integer userId) {
        return associatedTickets.get(userId);
    }
}
