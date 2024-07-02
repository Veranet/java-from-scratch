package halatsiankova.javafromscratch.busticket.validator;

import halatsiankova.javafromscratch.busticket.enumerated.ErrorType;
import halatsiankova.javafromscratch.busticket.enumerated.TicketType;
import halatsiankova.javafromscratch.busticket.model.BusTicket;

import halatsiankova.javafromscratch.busticket.model.ErrorEntity;
import halatsiankova.javafromscratch.busticket.provider.DateTimeProvider;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * Validates BusTickets.
 *
 * Example
 * ```java
 * BusTicket ticket = new BusTicket;
 * ```
 */
public class BusTicketValidator {

    private final DateTimeProvider provider = new DateTimeProvider();

    private static final Logger LOGGER = getLogger(BusTicketValidator.class.getSimpleName());

    /**
     * Returns an ErrorEntity containing the ticket ID and error type TICKET_TYPE if the ticket fails validation.
     * Ticket type must match one of the following values "DAY", "WEEK", "MONTH", "YEAR".
     * @param ticket ticket for validation
     * @return errorEntity that contains the ticket ID and error type TICKET_TYPE
     */
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

    /**
     * Returns an ErrorEntity containing the ticket ID and error type START_DATE if the ticket fails validation.
     * Ticket start date must not be in the future.
     * Only ticket with types DAY, WEEK and YEAR must have a start day.
     * @param ticket ticket for validation
     * @return errorEntity that contains the ticket ID and error type START_DATE
     */
    public Optional<ErrorEntity> validateStartDate(BusTicket ticket) {
        LocalDate currentTime = provider.provideDateTime();
        if (ticket.getStartDate() == null || ticket.getStartDate().isEmpty()
                || LocalDate.parse(ticket.getStartDate()).isAfter(currentTime)) {
            var error = String
                    .format("%s . Ticket must contain the date. The date must not be in the future.", ticket);
            LOGGER.log(Level.WARNING, error);
            return createErrorEntity(ticket, ErrorType.START_DATE);
        }
        if (ticket.getTicketType() == null
                || !Arrays.asList("DAY", "WEEK", "YEAR").contains(ticket.getTicketType().toString())) {
            var error = String
                    .format("%s . Only ticket with types DAY, WEEK and YEAR must have a start day.", ticket);
            LOGGER.log(Level.WARNING, error);
            return createErrorEntity(ticket, ErrorType.START_DATE);
        }
        return Optional.empty();
    }

    /**
     * Returns an ErrorEntity containing the ticket ID and error type PRICE if the ticket fails validation.
     * Ticket price must be even.
     * Only ticket with types DAY, WEEK and YEAR must have a start day.
     * @param ticket ticket for validation
     * @return errorEntity that contains the ticket ID and error type PRICE
     */
    public Optional<ErrorEntity> validatePrice(BusTicket ticket) {
        if (ticket.getPrice() == null || ticket.getPrice().intValueExact() == 0
                || (ticket.getPrice().intValueExact() % 2) != 0) {
            var error = String.format("%s . Ticket must contain the price. The price must be even.", ticket);
            LOGGER.log(Level.WARNING, error);
            return createErrorEntity(ticket, ErrorType.PRICE);
        }
        return Optional.empty();
    }

    private Optional<ErrorEntity> createErrorEntity(BusTicket ticket, ErrorType error) {
        var errorEntity = new ErrorEntity(ticket.getId(), error);
        return Optional.of(errorEntity);
    }
}
