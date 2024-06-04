package halatsiankova.javafromscratch.lesson2;

import halatsiankova.javafromscratch.lesson2.model.Ticket;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.regex.Pattern;

import static halatsiankova.javafromscratch.lesson2.model.Ticket.StadiumSector.A;

public class TicketService {
    private static final System.Logger LOGGER = System.getLogger(TicketService.class.getSimpleName());

    private final Validator validator;

    public TicketService() {
        validator = new Validator();
    }

    public static void main(String[] args) {

        TicketService ticketService = new TicketService();
        ticketService.create();
        ticketService.create("12ae", "MAIN", 222, 1717499006,
                true, A, 15.86, BigDecimal.valueOf(100.58));
        ticketService.create("SMALL", 135, 1717499006);
    }

    public Ticket create() {
        Ticket emptyTicket = new Ticket();
        LOGGER.log(System.Logger.Level.INFO, emptyTicket);
        return emptyTicket;
    }

    public Ticket create(String ticketId, String concertHall, int eventCode, long eventTime, boolean isPromo,
                         Ticket.StadiumSector stadiumSector, double allowedBackpackWeight, BigDecimal price) {
        validator.validateTicketId(ticketId);
        validator.validateConcertHall(concertHall);
        validator.validateEventCode(eventCode);
        validator.validateEventTime(eventTime);
        validator.validateAllowedBackpackWeight(allowedBackpackWeight);
        validator.validatePrice(price);
        var createdDateTime = createTimeStamp();
        Ticket fullTicket = new Ticket(ticketId, concertHall, eventCode, eventTime, isPromo,
                stadiumSector, allowedBackpackWeight, price, createdDateTime);
        LOGGER.log(System.Logger.Level.INFO, fullTicket);
        return fullTicket;
    }

    public Ticket create(String concertHall, int eventCode, long time) {
        validator.validateConcertHall(concertHall);
        validator.validateEventCode(eventCode);
        validator.validateEventTime(time);
        Ticket limitedTicket = new Ticket(concertHall, eventCode, time);
        LOGGER.log(System.Logger.Level.INFO, limitedTicket);
        return limitedTicket;
    }

    public OffsetDateTime createTimeStamp() {
        return OffsetDateTime.now();
    }

    public class Validator {
        private void validateTicketId (String ticketId) {
            var ticketIdLength = ticketId.length();
            if (ticketIdLength > 4) {
                throw new IllegalArgumentException("ticketId must contains max 4 digits and/or chars");
            }
            String pattern = "[a-zA-Z0-9]{%d}".formatted(ticketIdLength);
            Pattern compiledPattern = Pattern.compile(pattern);
            if (!compiledPattern.matcher(ticketId).find()) {
                throw new IllegalArgumentException("ticketId must consist of numbers and/or symbols only.");
            }
        }

        private void validateConcertHall(String concertHall) {
            if (concertHall != null && concertHall.length() >= 10) {
                throw new IllegalArgumentException("concertHall must contains max 10 chars");
            }
        }

        private void validateEventCode(int eventCode) {
            if (String.valueOf(eventCode).length() != 3) {
                throw new IllegalArgumentException("eventCode must contains 3 digits");
            }
        }

        private void validateEventTime(long eventTime) {
            if (eventTime < 0) {
                throw new IllegalArgumentException("eventTime must be above zero");
            }
        }

        private void validateAllowedBackpackWeight(double allowedBackpackWeight) {
            if (allowedBackpackWeight < 0) {
                throw new IllegalArgumentException("allowedBackpackWeight must be above zero");
            }
        }

        private void validatePrice(final BigDecimal price) {
            if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("price must be above zero");
            }
        }
    }
}
