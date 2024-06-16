package halatsiankova.javafromscratch.busTicket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;
import halatsiankova.javafromscratch.busTicket.repository.BusTicketRepository;
import halatsiankova.javafromscratch.busTicket.repository.ValidationRepository;
import halatsiankova.javafromscratch.busTicket.service.TicketService;
import halatsiankova.javafromscratch.busTicket.service.ValidationService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class App {

    public static void main(String[] args) throws IOException {
        BusTicketRepository busTicketRepository = new BusTicketRepository();
        ValidationRepository validationRepository = new ValidationRepository();
        TicketService ticketService = new TicketService(busTicketRepository);

        ValidationService validationService = new ValidationService(validationRepository);

        String path = "bus-tickets/src/main/java/resources/tickets.txt";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);

        List<BusTicket> tickets = objectMapper.readValue(Paths.get(path).toFile(), new TypeReference<>(){});
        Collection<BusTicket> busTickets = ticketService.saveAll(tickets);
        validationService.validate(busTickets);
        validationService.getTicketValidationStatistics(tickets);
    }
}