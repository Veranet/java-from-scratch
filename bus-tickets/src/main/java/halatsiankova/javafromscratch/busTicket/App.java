package halatsiankova.javafromscratch.busticket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import halatsiankova.javafromscratch.busticket.model.BusTicket;
import halatsiankova.javafromscratch.busticket.repository.BusTicketRepository;
import halatsiankova.javafromscratch.busticket.repository.ValidationRepository;
import halatsiankova.javafromscratch.busticket.service.TicketService;
import halatsiankova.javafromscratch.busticket.service.ValidationService;
import halatsiankova.javafromscratch.busticket.util.GeneratorUUID;
import halatsiankova.javafromscratch.busticket.validator.BusTicketValidator;

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
