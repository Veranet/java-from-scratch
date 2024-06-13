package halatsiankova.javafromscratch.lesson2.repository;

import halatsiankova.javafromscratch.lesson2.model.Ticket;

import java.util.List;

public interface TicketRepository extends Repository<Ticket, String> {
  
    List<Ticket> findTicketByStadiumSector(Ticket.StadiumSector stadiumSector);

    public TicketRepository() {
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
    public Optional<Ticket> findById(String id) {
        return Optional.ofNullable(tickets.get(id));
    }
}
