package halatsiankova.javafromscratch.lesson2.service;

import halatsiankova.javafromscratch.lesson2.model.Ticket;
import halatsiankova.javafromscratch.lesson2.model.User;
import halatsiankova.javafromscratch.lesson2.repository.AdministrationRepository;
import halatsiankova.javafromscratch.lesson2.repository.TicketRepository;

import java.util.List;

public class UserService {

    private final TicketRepository ticketRepository;

    private final AdministrationRepository administrationRepository;

    public UserService(TicketRepository ticketRepository, AdministrationRepository administrationRepository,
                       TicketService ticketService) {
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
