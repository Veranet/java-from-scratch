package halatsiankova.javafromscratch.busticket.validator;

import halatsiankova.javafromscratch.busticket.enumerated.ErrorType;
import halatsiankova.javafromscratch.busticket.enumerated.TicketType;
import halatsiankova.javafromscratch.busticket.model.BusTicket;
import halatsiankova.javafromscratch.busticket.model.ErrorEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    private final BusTicketValidator validator = new BusTicketValidator();

    @Nested
    public class ValidateType {
        @Test
        void shouldReturnsOptionalErrorEntityWithTicketTypeErrorWhenTicketTypeIsNotValid() {
            var busTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.UNDEFINED,
                    "2025-01-01",
                    BigDecimal.valueOf(14.0));

            Optional<ErrorEntity> actual = validator.validateType(busTicket);

            var expected = Optional.of(new ErrorEntity(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    ErrorType.TICKET_TYPE));

            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnsOptionalErrorEntityWithTicketTypeErrorWhenTicketTypeIsNull() {
            var busTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    null,
                    "2025-01-01",
                    BigDecimal.valueOf(14.0));

            Optional<ErrorEntity> actual = validator.validateType(busTicket);

            var expected = Optional.of(new ErrorEntity(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    ErrorType.TICKET_TYPE));

            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnsOptionalEmptyWhenTicketTypeIsValid() {
            var busTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.DAY,
                    "2025-01-01",
                    BigDecimal.valueOf(14.0));

            Optional<ErrorEntity> actual = validator.validateType(busTicket);

            assertEquals(Optional.empty(), actual);
        }
    }

    @Nested
    public class ValidateStartDate {
        @Test
        void shouldReturnsOptionalEmptyWhenStartDateIsValid() {
            var busTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.DAY,
                    "2023-01-01",
                    BigDecimal.valueOf(14.0));

            Optional<ErrorEntity> actual = validator.validateStartDate(busTicket);

            assertEquals(Optional.empty(), actual);
        }

        @Test
        void shouldReturnsOptionalErrorEntityWithStartDateErrorWhenTicketStartDateIsNull() {
            var busTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.DAY,
                    null,
                    BigDecimal.valueOf(14.0));

            Optional<ErrorEntity> actual = validator.validateStartDate(busTicket);

            var expected = Optional.of(new ErrorEntity(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    ErrorType.START_DATE));

            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnsOptionalErrorEntityWithStartDateErrorWhenTicketStartDateInTheFuture() {
            var busTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.DAY,
                    "2030-01-01",
                    BigDecimal.valueOf(14.0));

            Optional<ErrorEntity> actual = validator.validateStartDate(busTicket);

            var expected = Optional.of(new ErrorEntity(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    ErrorType.START_DATE));

            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnsOptionalErrorEntityWithStartDateErrorWhenTicketStartDateIsValidButTicketTypeIsNotDayOrWeekOrYear() {
            var busTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.MONTH,
                    "2023-01-01",
                    BigDecimal.valueOf(14.0));

            Optional<ErrorEntity> actual = validator.validateStartDate(busTicket);

            var expected = Optional.of(new ErrorEntity(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    ErrorType.START_DATE));

            assertEquals(expected, actual);
        }
    }

    @Nested
    public class ValidatePrice {
        @Test
        void shouldReturnsOptionalEmptyWhenPriceIsValid() {
            var busTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.DAY,
                    "2023-01-01",
                    BigDecimal.valueOf(14.0));

            Optional<ErrorEntity> actual = validator.validatePrice(busTicket);

            assertEquals(Optional.empty(), actual);
        }

        @Test
        void shouldReturnsOptionalErrorEntityWithPriceErrorWhenPriceIsNull() {
            var busTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.DAY,
                    "2023-01-01",
                    null);

            Optional<ErrorEntity> actual = validator.validatePrice(busTicket);

            var expected = Optional.of(new ErrorEntity(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    ErrorType.PRICE));

            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnsOptionalErrorEntityWithPriceErrorWhenPriceIsNotEven() {
            var busTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.DAY,
                    "2023-01-01",
                    BigDecimal.valueOf(15.0));

            Optional<ErrorEntity> actual = validator.validatePrice(busTicket);

            var expected = Optional.of(new ErrorEntity(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    ErrorType.PRICE));

            assertEquals(expected, actual);
        }
    }
}
