package halatsiankova.javafromscratch.lesson2.service;

import halatsiankova.javafromscratch.lesson2.model.Admin;
import halatsiankova.javafromscratch.lesson2.model.Ticket;
import halatsiankova.javafromscratch.lesson2.provider.DateTimeProvider;
import halatsiankova.javafromscratch.lesson2.repository.TicketRepository;

public class AdministrationService {
    private static final System.Logger LOGGER = System.getLogger(AdministrationService.class.getSimpleName());
    private final TicketRepository ticketRepository;

    private final DateTimeProvider dateTimeProvider;

    public AdministrationService(TicketRepository ticketRepository, DateTimeProvider dateTimeProvider) {
        this.ticketRepository = ticketRepository;
        this.dateTimeProvider = dateTimeProvider;
    }

    public void checkTicket(Ticket ticket, Admin admin) {
        long currentDate = dateTimeProvider.provideDateTime().toInstant().toEpochMilli();
        long eventTime = ticketRepository.findById(ticket.getTicketId()).get().getEventTime();
        if (currentDate < eventTime) {
            var errorMessage =
                    String.format("The ticket has expired date : ticketId=%s, admin Id=%d", ticket.getTicketId(), admin.getId());
            throw new IllegalStateException(errorMessage);
        }
        var successMessage =
                String.format("checked by admin : ticketId=%s, admin Id=%d", ticket.getTicketId(), admin.getId());
        LOGGER.log(System.Logger.Level.INFO, successMessage);
    }
}
