package halatsiankova.javafromscratch.busTicket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;
import halatsiankova.javafromscratch.busTicket.repository.BusTicketRepository;
import halatsiankova.javafromscratch.busTicket.repository.ValidationRepository;
import halatsiankova.javafromscratch.busTicket.service.TicketService;
import halatsiankova.javafromscratch.busTicket.service.ValidationService;
import halatsiankova.javafromscratch.busTicket.util.GeneratorUUID;
import halatsiankova.javafromscratch.busTicket.validator.BusTicketValidator;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {
        BusTicketRepository busTicketRepository = new BusTicketRepository();
        ValidationRepository validationRepository = new ValidationRepository();
        GeneratorUUID generatorUUID = new GeneratorUUID();
        BusTicketValidator validator = new BusTicketValidator();
        TicketService ticketService = new TicketService(busTicketRepository, generatorUUID);

        ValidationService validationService = new ValidationService(validationRepository, busTicketRepository, validator);

        String path = "bus-tickets/src/main/java/resources/tickets.txt";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);

        List<BusTicket> tickets = objectMapper.readValue(Paths.get(path).toFile(), new TypeReference<>(){});
        Collection<BusTicket> busTickets = ticketService.saveAll(tickets);
        validationService.validate(busTickets);
        validationService.getTicketValidationStatistics();
    }
}
