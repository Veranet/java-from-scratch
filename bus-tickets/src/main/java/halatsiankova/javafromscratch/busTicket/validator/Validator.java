package halatsiankova.javafromscratch.busTicket.validator;

import halatsiankova.javafromscratch.busTicket.enumerated.ErrorType;
import halatsiankova.javafromscratch.busTicket.enumerated.TicketType;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;

import halatsiankova.javafromscratch.busTicket.model.ErrorEntity;
import halatsiankova.javafromscratch.busTicket.provider.DateTimeProvider;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class Validator {

    private final DateTimeProvider provider = new DateTimeProvider();

    private static final Logger LOGGER = getLogger(Validator.class.getSimpleName());

    public Optional<ErrorEntity> validateType(BusTicket ticket) {
        if (ticket.getTicketType() == null || ticket.getTicketType() == TicketType.UNDEFINED) {
            String error = String.format(
                    "%s . Ticket type must match one of the following values \"DAY\", \"WEEK\", \"MONTH\", \"YEAR\".",
                            ticket);
            LOGGER.log(Level.WARNING, error);
            return createErrorEntity(ticket, ErrorType.TICKET_TYPE);
        }
        return Optional.empty();
    }

    public Optional<ErrorEntity> validateStartDate(BusTicket ticket) {
        LocalDate currentTime = provider.provideDateTime();
        if (ticket.getStartDate() == null || ticket.getStartDate().isEmpty()
                || LocalDate.parse(ticket.getStartDate()).isAfter(currentTime)) {
            String error = String
                    .format("%s . Ticket must contain the date. The date must not be in the future.", ticket);
            LOGGER.log(Level.WARNING, error);
            return createErrorEntity(ticket, ErrorType.START_DATE);
        }
        if (ticket.getTicketType() == null
                || !Arrays.asList("DAY", "WEEK", "YEAR").contains(ticket.getTicketType().toString())) {
            String error = String
                    .format("%s . Only ticket with types DAY, WEEK and YEAR must have a start day.", ticket);
            LOGGER.log(Level.WARNING, error);
            return createErrorEntity(ticket, ErrorType.START_DATE);
        }
        return Optional.empty();
    }

    public Optional<ErrorEntity> validatePrice(BusTicket ticket) {
        if (ticket.getPrice() == null || ticket.getPrice().intValueExact() == 0
                || (ticket.getPrice().intValueExact() % 2) != 0) {
            String error = String
                    .format("%s . Ticket must contain the price. The price must be even", ticket);
            LOGGER.log(Level.WARNING, error);
            return createErrorEntity(ticket, ErrorType.PRICE);
        }
        return Optional.empty();
    }

    private Optional<ErrorEntity> createErrorEntity(BusTicket ticket, ErrorType error) {
        ErrorEntity errorEntity = new ErrorEntity();
        errorEntity.setTicketId(ticket.getId());
        errorEntity.setError(error);
        return Optional.of(errorEntity);
    }
}
