package halatsiankova.javafromscratch.busticket;

import halatsiankova.javafromscratch.busticket.model.BusTicket;
import halatsiankova.javafromscratch.busticket.repository.BusTicketRepository;
import halatsiankova.javafromscratch.busticket.repository.ValidationRepository;
import halatsiankova.javafromscratch.busticket.service.TicketService;
import halatsiankova.javafromscratch.busticket.service.ValidationService;
import halatsiankova.javafromscratch.busticket.util.GeneratorUUID;
import halatsiankova.javafromscratch.busticket.util.TicketsLoader;
import halatsiankova.javafromscratch.busticket.validator.BusTicketValidator;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class App {

    private static final String TICKETS_PATH = "bus-tickets/src/main/java/resources/tickets.txt";

    public static void main(String[] args) throws IOException {
        BusTicketRepository busTicketRepository = new BusTicketRepository();
        ValidationRepository validationRepository = new ValidationRepository();
        GeneratorUUID generatorUUID = new GeneratorUUID();
        BusTicketValidator validator = new BusTicketValidator();
        TicketService ticketService = new TicketService(busTicketRepository, generatorUUID);

        ValidationService validationService = new ValidationService(validationRepository, busTicketRepository, validator);

        TicketsLoader<BusTicket> loader = new TicketsLoader<>();
        List<BusTicket> tickets = loader.load(TICKETS_PATH, BusTicket.class);

        Collection<BusTicket> busTickets = ticketService.saveAll(tickets);
        validationService.validate(busTickets);
        validationService.getTicketValidationStatistics();
    }


}
