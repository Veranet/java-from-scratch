package halatsiankova.javafromscratch.validator;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class TicketValidator {
    public void validateTicketId(String ticketId) {
        var ticketIdLength = ticketId.length();
        if (ticketIdLength > 4) {
            var errorLog = String.format("ticketId=%s must contains max 4 digits and/or chars.", ticketId);
            throw new IllegalArgumentException(errorLog);
        }
        String pattern = "[a-zA-Z0-9]{%d}".formatted(ticketIdLength);
        Pattern compiledPattern = Pattern.compile(pattern);
        if (!compiledPattern.matcher(ticketId).find()) {
            var errorLog = String.format("ticketId=%s must consist of numbers and/or symbols only.", ticketId);
            throw new IllegalArgumentException(errorLog);
        }
    }

    public void validateConcertHall(String concertHall) {
        if (concertHall != null && concertHall.length() >= 10) {
            var errorLog = String.format("concertHall=%s must contains max 10 chars.", concertHall);
            throw new IllegalArgumentException(errorLog);
        }
    }

    public void validateEventCode(int eventCode) {
        if (String.valueOf(eventCode).length() != 3) {
            var errorLog = String.format("eventCode=%d must contains 3 digits.", eventCode);
            throw new IllegalArgumentException(errorLog);
        }
    }

    public void validateEventTime(long eventTime) {
        if (eventTime < 0) {
            var errorLog = String.format("eventTime=$d must be above zero.", eventTime);
            throw new IllegalArgumentException(errorLog);
        }
    }

    public void validateAllowedBackpackWeight(double allowedBackpackWeight) {
        if (allowedBackpackWeight < 0) {
            var errorLog = String.format("allowedBackpackWeight=%d must be above zero.", allowedBackpackWeight);
            throw new IllegalArgumentException(errorLog);
        }
    }

    public void validatePrice(final BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            var errorLog = String.format("price=%s must be above zero.", price);
            throw new IllegalArgumentException(errorLog);
        }
    }
}
