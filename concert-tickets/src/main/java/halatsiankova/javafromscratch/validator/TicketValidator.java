package halatsiankova.javafromscratch.validator;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class TicketValidator {
    public void validateTicketId(String ticketId) {
        var ticketIdLength = ticketId.length();
        if (ticketIdLength > 4) {
            throw new IllegalArgumentException("ticketId must contains max 4 digits and/or chars.");
        }
        String pattern = "[a-zA-Z0-9]{%d}".formatted(ticketIdLength);
        Pattern compiledPattern = Pattern.compile(pattern);
        if (!compiledPattern.matcher(ticketId).find()) {
            throw new IllegalArgumentException("ticketId must consist of numbers and/or symbols only.");
        }
    }

    public void validateConcertHall(String concertHall) {
        if (concertHall != null && concertHall.length() >= 10) {
            throw new IllegalArgumentException("concertHall must contains max 10 chars.");
        }
    }

    public void validateEventCode(int eventCode) {
        if (String.valueOf(eventCode).length() != 3) {
            throw new IllegalArgumentException("eventCode must contains 3 digits.");
        }
    }

    public void validateEventTime(long eventTime) {
        if (eventTime < 0) {
            throw new IllegalArgumentException("eventTime must be above zero.");
        }
    }

    public void validateAllowedBackpackWeight(double allowedBackpackWeight) {
        if (allowedBackpackWeight < 0) {
            throw new IllegalArgumentException("allowedBackpackWeight must be above zero.");
        }
    }

    public void validatePrice(final BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price must be above zero.");
        }
    }
}