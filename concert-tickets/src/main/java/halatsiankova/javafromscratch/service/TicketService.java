package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository repository;

    public TicketService(TicketRepository ticketRepository) {
        this.repository = ticketRepository;
    }

    public void add(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket must not be null.");
        }
        repository.save(ticket);
    }

    public Ticket getTicketById(int ticketId) {
        checkTicketId(ticketId);
        Optional<Ticket> ticket;
        ticket = repository.findById(ticketId);
        return ticket.orElseThrow(
                () -> new IllegalArgumentException(String.format("Ticket with ID = %d does not exist.", ticketId)));
    }

    public List<Ticket> getAllTicketsByUserId(int userId) {
        UserService.checkUserId(userId);
        return repository.findAllByUserId(userId);
    }

    public void update(TicketType ticketType, int ticketId) {
        if (ticketType == null) {
            throw new IllegalArgumentException("Ticket type must not be null.");
        }
        checkTicketId(ticketId);
        repository.updateTicketTypeById(ticketId, ticketType.name());
    }

    private void checkTicketId(int ticketId) {
        if (ticketId <= 0) {
            throw new IllegalArgumentException("Ticket ID must not be negative or equal to 0.");
        }
    }
}
