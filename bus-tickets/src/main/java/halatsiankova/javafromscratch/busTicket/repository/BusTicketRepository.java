package halatsiankova.javafromscratch.busTicket.repository;

import halatsiankova.javafromscratch.busTicket.enumerated.TicketType;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Optional<BusTicket> findById(UUID ticketId) {
        return Optional.of(tickets.get(ticketId));
    }

    public void deleteById(UUID ticketId) {
        tickets.remove(ticketId);
    }

    public List<BusTicket> findAllByTicketTypeAndPriceFromTo(TicketType ticketType, BigDecimal priceFrom, BigDecimal priceTo) {
        return tickets.values().stream()
                .filter(busTicket -> busTicket.getTicketType().equals(ticketType))
                .filter(busTicket -> busTicket.getPrice().compareTo(priceFrom) >= 0)
                .filter(busTicket -> busTicket.getPrice().compareTo(priceTo) <= 0)
                .toList();
    }
}
