package halatsiankova.javafromscratch.lesson2.repository;

import halatsiankova.javafromscratch.lesson2.model.Ticket;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TicketRepository implements Repository<Ticket, String> {
    private final ConcurrentHashMap<String, Ticket> tickets;

    public TicketRepository() {
        this.tickets = new ConcurrentHashMap<>();
    }



    @Override
    public void saveAll(Collection<Ticket> tickets) {
        this.tickets.putAll(
                tickets.stream()
                        .collect(Collectors.toMap(Ticket::getTicketId, Function.identity())));
    }

    @Override
    public List<Ticket> findByStadiumSector(Ticket.StadiumSector sector) {
        return tickets.values().stream()
                .filter(ticket -> ticket.getStadiumSector() == sector)
                .collect(Collectors.toList());
    }
}
