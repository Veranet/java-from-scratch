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

    /**
     * Saves a collection of BusTicket objects by first clearing the existing tickets in the repository,
     * then assigning a unique UUID to each new ticket, and finally saving all the tickets in the repository.
     * @param tickets the collection of BusTicket objects to save
     * @return the collection of BusTicket objects after they have been saved, with their IDs set
     */
    public Collection<BusTicket> saveAll(Collection<BusTicket> tickets) {
        if(tickets == null) {
            throw new IllegalArgumentException("Collection of tickets must not be null.");
        }
        repository.deleteAll();
        tickets.forEach(ticket -> ticket.setId(generateUUID.generateId()));
        repository.saveAll(tickets);
        return tickets;
    }

    public BusTicket getById(UUID ticketId) {
        if(ticketId == null) {
            throw new IllegalArgumentException("Ticket ID must not be null.");
        }
        return repository.findById(ticketId).
                orElseThrow(() -> new IllegalArgumentException(String.format("BusTicket with id=%s doesn't exist.",
                        ticketId)));
    }

    public void deleteByTicketId(UUID ticketId) {
        if(ticketId == null) {
            throw new IllegalArgumentException("Ticket ID must not be null.");
        }
        repository.deleteById(ticketId);
    }

    public List<BusTicket> getAllByTicketIdAndPriceFromTo(TicketType ticketType, BigDecimal priceFrom, BigDecimal priceTo) {
        if(ticketType == null || priceFrom == null || priceTo == null) {
            throw new IllegalArgumentException("Ticket type, price from, price to must not be null.");
        }
        if(priceFrom.compareTo(priceTo) >0) {
            throw new IllegalArgumentException("The price from must be less than the price to.");
        }
        return repository.findAllByTicketTypeAndPriceFromTo(ticketType, priceFrom, priceTo);
    }
}
