package halatsiankova.javafromscratch.controller;

import halatsiankova.javafromscratch.dto.TicketDto;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.service.TicketService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private static final Logger LOGGER = Logger.getLogger(TicketController.class.getSimpleName());
    private final ConversionService conversionService;
    private final TicketService ticketService;

    public TicketController(TicketService ticketService,
                            @Qualifier("conversionService") ConversionService conversionService) {
        this.ticketService = ticketService;
        this.conversionService = conversionService;
    }

    @GetMapping("/{id}")
    public TicketDto getTicket(@PathVariable(value = "id") int ticketId) {
        Ticket ticketById = ticketService.getTicketById(ticketId);
        LOGGER.log(Level.INFO, ticketById.toString());
        return conversionService.convert(ticketById, TicketDto.class);
    }
}
