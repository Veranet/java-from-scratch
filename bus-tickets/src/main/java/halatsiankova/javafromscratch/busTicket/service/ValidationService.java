package halatsiankova.javafromscratch.busTicket.service;

import halatsiankova.javafromscratch.busTicket.App;
import halatsiankova.javafromscratch.busTicket.enumerated.ErrorType;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;
import halatsiankova.javafromscratch.busTicket.model.ErrorEntity;
import halatsiankova.javafromscratch.busTicket.repository.ValidationRepository;
import halatsiankova.javafromscratch.busTicket.validator.Validator;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class ValidationService {
    public static Logger LOGGER = getLogger(App.class.getSimpleName());
    private final ValidationRepository validationRepository;
    private final Validator validator;

    public ValidationService(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
        this.validator = new Validator();
    }

    public void validate(Collection<BusTicket> tickets) {
        tickets.forEach(ticket -> {
            Optional<ErrorEntity> typeValidationError = validator.validateType(ticket);
            typeValidationError.ifPresent(this::saveErrorEntity);
            
            Optional<ErrorEntity> dateValidationError = validator.validateStartDate(ticket);
            dateValidationError.ifPresent(this::saveErrorEntity);

            Optional<ErrorEntity> priceValidationError = validator.validatePrice(ticket);
            priceValidationError.ifPresent(this::saveErrorEntity);
        });
    }

    public void getTicketValidationStatistics(Collection<BusTicket> tickets) {
        ErrorType mostCommonErrorType = findMostCommonErrorType();
        int total = tickets.size();
        int valid = total - validationRepository.countByTicketId();

        String error = String.format("\n\tTotal = %d%n\tValid = %d%n\tMost popular violation = %s",
                total, valid, mostCommonErrorType.name());

        LOGGER.log(Level.INFO, error);
    }

    private void saveErrorEntity(ErrorEntity errorEntity) {
        validationRepository.save(errorEntity);

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
