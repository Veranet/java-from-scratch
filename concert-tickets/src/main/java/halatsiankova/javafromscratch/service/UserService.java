package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.model.User;
import halatsiankova.javafromscratch.repository.AdministrationRepository;
import halatsiankova.javafromscratch.repository.TicketRepository;

import java.util.List;

public class UserService {

    private final TicketRepository ticketRepository;

    private final AdministrationRepository administrationRepository;

    public UserService(TicketRepository ticketRepository, AdministrationRepository administrationRepository) {
        this.ticketRepository = ticketRepository;
        this.administrationRepository = administrationRepository;
    }

    public List<Ticket> getAllTickets(User user) {
        return administrationRepository.findAllByUserId(user.getId()).stream()
                .map(ticketId -> ticketRepository.findById(ticketId).get())
                .toList();
    }

    public void buyTicket(User user, Ticket ticket) {
        administrationRepository.save(user.getId(), ticket.getTicketId());
    }
}
