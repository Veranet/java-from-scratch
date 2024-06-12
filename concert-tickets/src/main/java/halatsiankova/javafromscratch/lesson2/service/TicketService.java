package halatsiankova.javafromscratch.lesson2.service;

import halatsiankova.javafromscratch.lesson2.enumerated.StadiumSector;
import halatsiankova.javafromscratch.lesson2.model.Ticket;
import halatsiankova.javafromscratch.lesson2.provider.DateTimeProvider;
import halatsiankova.javafromscratch.lesson2.repository.TicketRepository;
import halatsiankova.javafromscratch.lesson2.repository.TicketRepositoryImpl;
import halatsiankova.javafromscratch.lesson2.validator.TicketValidator;

import java.math.BigDecimal;
import java.util.List;

public class TicketService {
    private static final System.Logger LOGGER = System.getLogger(TicketService.class.getSimpleName());

    private final TicketValidator validator;
    private final TicketRepository repository;
    private final DateTimeProvider timeProvider;

    public TicketService() {
        validator = new TicketValidator();
        repository = new TicketRepositoryImpl();
        timeProvider = new DateTimeProvider();
    }

    public TicketService(TicketRepository repository) {
        this.validator = new TicketValidator();
        this.repository = repository;
        this.timeProvider = new DateTimeProvider();
    }


    public Ticket create() {
        Ticket emptyTicket = new Ticket();
        LOGGER.log(System.Logger.Level.INFO, emptyTicket);
        return emptyTicket;
    }

    public Ticket create(String ticketId, String concertHall, int eventCode, long eventTime, boolean isPromo,
                         StadiumSector stadiumSector, double allowedBackpackWeight, BigDecimal price) {
        validator.validateTicketId(ticketId);
        validator.validateConcertHall(concertHall);
        validator.validateEventCode(eventCode);
        validator.validateEventTime(eventTime);
        validator.validateAllowedBackpackWeight(allowedBackpackWeight);
        validator.validatePrice(price);
        var createdDateTime = timeProvider.provideDateTime();
        Ticket fullTicket = new Ticket(ticketId, concertHall, eventCode, eventTime, isPromo,
                stadiumSector, allowedBackpackWeight, price, createdDateTime);
        LOGGER.log(System.Logger.Level.INFO, fullTicket);
        return fullTicket;
    }

    public Ticket create(String concertHall, int eventCode, long time) {
        validator.validateConcertHall(concertHall);
        validator.validateEventCode(eventCode);
        validator.validateEventTime(time);
        Ticket limitedTicket = new Ticket(concertHall, eventCode, time);
        LOGGER.log(System.Logger.Level.INFO, limitedTicket);
        return limitedTicket;
    }

    public void saveAll(List<Ticket> ticketsForSave) {
        repository.saveAll(ticketsForSave);
    }

    public Ticket getById(String ticketId) {
        return repository.findById(ticketId)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("Cannot find ticket by ID = %s", ticketId)));
    }

    public List<Ticket> getTicketsByStadiumSector(StadiumSector stadiumSector) {
        return repository.findTicketByStadiumSector(stadiumSector);
    }
}
