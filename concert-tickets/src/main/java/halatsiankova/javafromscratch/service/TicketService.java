package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.repository.TicketRepositoryImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class TicketService {
    private final TicketRepositoryImpl repository;

    public TicketService() {
        try {
            repository = new TicketRepositoryImpl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TicketService(TicketRepositoryImpl repository) {
        this.repository = repository;
    }

    public void add(Ticket ticket) {
        if(ticket == null) {
            throw new IllegalArgumentException("Ticket must not be null.");
        }
        try {
            repository.save(ticket);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ticket getTicketById(int ticketId) {
        checkTicketId(ticketId);
        Optional<Ticket> ticket;
        try {
            ticket = repository.findById(ticketId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticket.orElseThrow(
                () -> new IllegalArgumentException(String.format("Ticket with ID = %d does not exist.", ticketId)));
    }

    public List<Ticket> getTicketsByStadiumSector(StadiumSector stadiumSector) {
        if(stadiumSector == null) {
            throw new IllegalArgumentException("Stadium sector must not be null.");
        }
        try {
            return repository.findTicketByStadiumSector(stadiumSector);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ticket> getAllTicketsByUserId(int userId) {
        UserService.checkUserId(userId);
        try {
            return repository.findAllByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(TicketType ticketType, int ticketId) {
        if(ticketType == null) {
            throw new IllegalArgumentException("Ticket type must not be null.");
        }
        checkTicketId(ticketId);
        try {
            repository.update(ticketType, ticketId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkTicketId(int ticketId) {
        if(ticketId <= 0) {
            throw new IllegalArgumentException("Ticket ID must not be negative or equal to 0.");
        }
    }
}
