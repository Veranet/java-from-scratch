package halatsiankova.javafromscratch.busTicket.repository;

import halatsiankova.javafromscratch.busTicket.model.BusTicket;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BusTicketRepository {
    private final Map<UUID, BusTicket> tickets = new ConcurrentHashMap<>();

    public void save(BusTicket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    public void saveAll(Collection<BusTicket> tickets) {
        this.tickets.putAll(tickets.stream()
                .collect(Collectors.toMap(BusTicket::getId, Function.identity())));
    }
}
