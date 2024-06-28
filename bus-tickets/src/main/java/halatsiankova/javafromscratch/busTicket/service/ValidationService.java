package halatsiankova.javafromscratch.busticket.service;

import halatsiankova.javafromscratch.busticket.enumerated.ErrorType;
import halatsiankova.javafromscratch.busticket.model.BusTicket;
import halatsiankova.javafromscratch.busticket.model.ErrorEntity;
import halatsiankova.javafromscratch.busticket.repository.BusTicketRepository;
import halatsiankova.javafromscratch.busticket.repository.ValidationRepository;
import halatsiankova.javafromscratch.busticket.validator.BusTicketValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class ValidationService {
    public static Logger LOGGER = getLogger(ValidationService.class.getSimpleName());
    private final ValidationRepository validationRepository;
    private final BusTicketRepository busTicketRepository;
    private final BusTicketValidator validator;

    public ValidationService(ValidationRepository validationRepository, BusTicketRepository busTicketRepository, BusTicketValidator validator) {
        this.validationRepository = validationRepository;
        this.busTicketRepository = busTicketRepository;
        this.validator = validator;
    }

    /**
     * Validates a collection of BusTicket objects. This method clears all existing validation errors,
     * validates each ticket for type, start date, and price errors, and then saves any validation errors
     * found into the validation repository.
     * Validation criteria:<p>
     * - ticket type must be equal to DAY, WEEK, MONTH or YEAR,<p>
     * - price must be even,<p>
     * - date must not be in the future and must only be for tickets with the type DAY, WEEK, or YEAR.
     * @param tickets the collection of BusTicket objects to validate
     */
    public void validate(Collection<BusTicket> tickets) {
        validationRepository.deleteAll();
        List<ErrorEntity> errorEntities = new ArrayList<>();
        tickets.forEach(ticket -> {
            Optional<ErrorEntity> typeValidationError = validator.validateType(ticket);
            typeValidationError.ifPresent(errorEntities::add);
            
            Optional<ErrorEntity> dateValidationError = validator.validateStartDate(ticket);
            dateValidationError.ifPresent(errorEntities::add);

            Optional<ErrorEntity> priceValidationError = validator.validatePrice(ticket);
            priceValidationError.ifPresent(errorEntities::add);
        });
        validationRepository.saveAll(errorEntities);
    }

    /**
     * Returns information about the number of tickets,
     * how many of them are valid and what is the most common error.
     * <p>
     * Example:
     * <pre>
     * INFO:
     *     Total = 17
     *     Valid = 3
     *     Most popular violation = START_DATE
     * </pre>
     */
    public void getTicketValidationStatistics() {
        ErrorType mostCommonErrorType = findMostCommonErrorType();

        int total = busTicketRepository.count();
        int valid = total - validationRepository.countByTicketId();

        var error = String.format("\n\tTotal = %d%n\tValid = %d%n\tMost popular violation = %s",
                total, valid, mostCommonErrorType.name());

        LOGGER.log(Level.INFO, error);
    }

    private ErrorType findMostCommonErrorType() {
        List<ErrorEntity> errorEntities = validationRepository.findAllErrors();
        int dateValidationErrors = Math.toIntExact(errorEntities.stream()
                .filter(errorEntity -> errorEntity.getError().equals(ErrorType.START_DATE)).count());
        int priceValidationErrors = Math.toIntExact(errorEntities.stream()
                .filter(errorEntity -> errorEntity.getError().equals(ErrorType.PRICE)).count());
        int ticketTypeValidationErrors = Math.toIntExact(errorEntities.stream()
                .filter(errorEntity -> errorEntity.getError().equals(ErrorType.TICKET_TYPE)).count());
        int max = Math.max(dateValidationErrors, Math.max(priceValidationErrors, ticketTypeValidationErrors));

        if (max == dateValidationErrors) {
            return ErrorType.START_DATE;
        } else if (max == priceValidationErrors) {
            return ErrorType.PRICE;
        }
        return ErrorType.TICKET_TYPE;
    }
}
