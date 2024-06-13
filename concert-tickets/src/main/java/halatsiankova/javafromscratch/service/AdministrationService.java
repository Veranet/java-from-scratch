package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.model.Admin;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.provider.DateTimeProvider;
import halatsiankova.javafromscratch.repository.TicketRepository;

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
            var errorLog =
                    String.format("The ticket has expired date : ticketId=%s, admin Id=%d",
                            ticket.getTicketId(), admin.getId());
            throw new IllegalStateException(errorLog);
        }
        var infoLog =
                String.format("checked by admin : ticketId=%s, admin Id=%d", ticket.getTicketId(), admin.getId());
        LOGGER.log(System.Logger.Level.INFO, infoLog);
    }
}
