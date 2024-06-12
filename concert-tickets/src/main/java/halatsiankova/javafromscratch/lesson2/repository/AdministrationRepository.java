package halatsiankova.javafromscratch.lesson2.repository;

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
        if (associatedTickets.get(userId) == null) {
            List<String> ticketIds = new ArrayList<>();
            ticketIds.add(ticketId);
            associatedTickets.put(userId, ticketIds);
        } else {
            associatedTickets.get(userId).add(ticketId);
        }
    }

    public List<String> findAllByUserId(Integer userId) {
        return associatedTickets.get(userId);
    }
}
