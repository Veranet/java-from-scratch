package halatsiankova.javafromscratch.busTicket.service;

import halatsiankova.javafromscratch.busTicket.enumerated.TicketType;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;
import halatsiankova.javafromscratch.busTicket.repository.BusTicketRepository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
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

    public BusTicket getById(UUID ticketId) {
        return repository.findById(ticketId).
                orElseThrow(() -> new IllegalArgumentException(String.format("BusTicket with id=%s doesn't exist.",
                        ticketId)));
    }

    public void deleteByTicketId(UUID ticketId) {
        repository.deleteById(ticketId);
    }

    public List<BusTicket> getAllByTicketIdAndPriceFromTo(TicketType ticketType, BigDecimal priceFrom, BigDecimal priceTo) {
        return repository.findAllByTicketTypeAndPriceFromTo(ticketType, priceFrom, priceTo);
    }

    private UUID generateId() {
        return UUID.randomUUID();
    }
}
