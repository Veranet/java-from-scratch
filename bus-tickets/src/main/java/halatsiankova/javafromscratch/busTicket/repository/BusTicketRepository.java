package halatsiankova.javafromscratch.busTicket.repository;

import com.sun.jdi.connect.Connector;
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
import java.util.stream.Stream;

public class BusTicketRepository {
    private final Map<UUID, BusTicket> tickets = new ConcurrentHashMap<>();

    public void saveAll(Collection<BusTicket> tickets) {
        this.tickets.putAll(tickets.stream()
                .collect(Collectors.toMap(BusTicket::getId, Function.identity())));
    }

    public Optional<BusTicket> findById(UUID ticketId) {
        return Optional.ofNullable(tickets.get(ticketId));
    }

    public void deleteById(UUID ticketId) {
        tickets.remove(ticketId);
    }

    public void deleteAll() {
        tickets.clear();
    }

    /**
     * Returns list of BusTickets by the specified ticket type and price range.
     *
     * @param ticketType the type of the ticket to filter by. Can be "DAY", "WEEK", "MONTH" or "YEAR".
     * @param priceFrom the minimum price of the tickets to filter by
     * @param priceTo the maximum price of the tickets to filter by
     * @return a list of BusTickets that match the specified ticket type and fall within the specified price range
     */
    public List<BusTicket> findAllByTicketTypeAndPriceFromTo(TicketType ticketType, BigDecimal priceFrom, BigDecimal priceTo) {
        return tickets.values().stream()
                .filter(busTicket -> busTicket.getTicketType().equals(ticketType))
                .filter(busTicket -> busTicket.getPrice().compareTo(priceFrom) >= 0)
                .filter(busTicket -> busTicket.getPrice().compareTo(priceTo) <= 0)
                .toList();
    }

    public List<BusTicket> findAll() {
        return tickets.values().stream().toList();
    }

    public int count() {
        return tickets.size();
    }
}
