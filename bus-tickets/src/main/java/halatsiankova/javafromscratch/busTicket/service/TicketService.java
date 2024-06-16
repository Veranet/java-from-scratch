package halatsiankova.javafromscratch.busTicket.service;

import halatsiankova.javafromscratch.busTicket.model.BusTicket;
import halatsiankova.javafromscratch.busTicket.repository.BusTicketRepository;

import java.util.Collection;
import java.util.UUID;

public class TicketService {
    private final BusTicketRepository repository;

    public TicketService(BusTicketRepository busTicketRepository) {
        this.repository = busTicketRepository;
    }

    public void save(BusTicket ticket) {
        ticket.setId(generateId());
        repository.save(ticket);
    }

    public Collection<BusTicket> saveAll(Collection<BusTicket> collection) {
        collection.forEach(ticket -> ticket.setId(generateId()));
        repository.saveAll(collection);
        return collection;
    }

    private UUID generateId() {
        return UUID.randomUUID();
    }
}
