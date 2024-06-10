package halatsiankova.javafromscratch.lesson2.repository;

import halatsiankova.javafromscratch.lesson2.enumerated.StadiumSector;
import halatsiankova.javafromscratch.lesson2.model.Ticket;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TicketRepositoryImpl implements TicketRepository {
    private final ConcurrentHashMap<String, Ticket> tickets;

    public TicketRepositoryImpl() {
        this.tickets = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Ticket ticket) {
        tickets.put(ticket.getTicketId(), ticket);
    }

    @Override
    public void saveAll(Collection<Ticket> tickets) {
        this.tickets.putAll(
                tickets.stream()
                        .collect(Collectors.toMap(Ticket::getTicketId, Function.identity())));
    }

    @Override
    public List<Ticket> findTicketByStadiumSector(StadiumSector stadiumSector) {
        return this.tickets.values().stream()
                .filter(ticket -> ticket.getStadiumSector() == stadiumSector)
                .toList();
    }

    @Override
    public Optional<Ticket> findById(String id) {
        return Optional.ofNullable(tickets.get(id));
    }
}
