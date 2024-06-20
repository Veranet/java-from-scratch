package halatsiankova.javafromscratch.busTicket.service;

import halatsiankova.javafromscratch.busTicket.enumerated.TicketType;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;
import halatsiankova.javafromscratch.busTicket.repository.BusTicketRepository;
import halatsiankova.javafromscratch.busTicket.util.GeneratorUUID;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class TicketService {
    private final BusTicketRepository repository;
    private final GeneratorUUID generateUUID;

    public TicketService(BusTicketRepository busTicketRepository, GeneratorUUID generateId) {
        this.repository = busTicketRepository;
        this.generateUUID = generateId;
    }

    public void save(BusTicket ticket) {
        ticket.setId(generateUUID.generateId());
        repository.save(ticket);
    }

    public Collection<BusTicket> saveAll(Collection<BusTicket> collection) {
        collection.forEach(ticket -> ticket.setId(generateUUID.generateId()));
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
        if(priceFrom.compareTo(priceTo) >0) {
            throw new IllegalArgumentException("The price from must be less than the price to.");
        }
        return repository.findAllByTicketTypeAndPriceFromTo(ticketType, priceFrom, priceTo);
    }
}
