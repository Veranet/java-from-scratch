package halatsiankova.javafromscratch.busticket.repository;

import halatsiankova.javafromscratch.busticket.enumerated.TicketType;
import halatsiankova.javafromscratch.busticket.model.BusTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusTicketRepositoryTest {
    private BusTicketRepository repository;
    private List<BusTicket> busTickets;

    @BeforeEach
    public void init() {
        repository = new BusTicketRepository();
        busTickets = createListBusTickets();
        repository.saveAll(busTickets);
    }

    @Nested
    public class SaveAll {
        @Test
        void shouldSaveAllBusTicketsToRepository() {
            repository.saveAll(busTickets);

            Collection<BusTicket> expected = busTickets;

            assertEquals(expected, repository.findAll());
        }
    }

    @Nested
    public class FindById {
        @Test
        void shouldReturnsBusTicketByIdWhenIsExist() {
            var ticketId = UUID.fromString("3a333333-6795-4b38-b798-a28abd254eab");
            Optional<BusTicket> expected = Optional.of(new BusTicket(ticketId,
                    "STD",
                    TicketType.DAY,
                    "2025-01-01",
                    BigDecimal.valueOf(100.0)));

            assertEquals(expected, repository.findById(ticketId));
        }

        @Test
        void shouldReturnsOptionalIsEmptyWhenBusTicketDoesNotExist() {
            var ticketId = UUID.fromString("4a44444-6795-4b38-b798-a28abd254eab");

            assertEquals(Optional.empty(), repository.findById(ticketId));
        }
    }

    @Nested
    public class DeleteById {
        @Test
        void ShouldDeleteBusTicketById() {
            var ticketIdForDelete = UUID.fromString("2a222222-6795-4b38-b798-a28abd254eab");

            repository.deleteById(ticketIdForDelete);

            var expected = List.of(
                    new BusTicket(UUID.fromString("1a111111-6795-4b38-b798-a28abd254eab"),
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(1.0)),
                    new BusTicket(UUID.fromString("3a333333-6795-4b38-b798-a28abd254eab"),
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(100.0)),
                    new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                            "STD",
                            TicketType.YEAR,
                            "2025-01-01",
                            BigDecimal.valueOf(14.0)));

            assertEquals(expected, repository.findAll());
        }
    }

    @Nested
    public class FindAllByTicketTypeAndPriceFromTo {
        @ParameterizedTest
        @MethodSource("successData")
        void shouldGetByIdSuccessfully(TicketType type, BigDecimal priceFrom, BigDecimal priceTo,
                                       List<BusTicket> expected) {
            assertEquals(expected, repository.findAllByTicketTypeAndPriceFromTo(type, priceFrom, priceTo));
        }

        private static Stream<Arguments> successData() {
            return Stream.of(
                    Arguments.arguments(TicketType.DAY, BigDecimal.valueOf(10.0), BigDecimal.valueOf(40.0), List.of(
                            new BusTicket(UUID.fromString("2a222222-6795-4b38-b798-a28abd254eab"),
                                    "STD",
                                    TicketType.DAY,
                                    "2025-01-01",
                                    BigDecimal.valueOf(10.0)))),
                    Arguments.arguments(TicketType.YEAR, BigDecimal.valueOf(10.0), BigDecimal.valueOf(100.0), List.of(
                            new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                                    "STD",
                                    TicketType.YEAR,
                                    "2025-01-01",
                                    BigDecimal.valueOf(14.0)))),
                    Arguments.arguments(TicketType.DAY, BigDecimal.valueOf(10.0), BigDecimal.valueOf(10.0), List.of(
                            new BusTicket(UUID.fromString("2a222222-6795-4b38-b798-a28abd254eab"),
                                    "STD",
                                    TicketType.DAY,
                                    "2025-01-01",
                                    BigDecimal.valueOf(10.0)))),
                    Arguments.arguments(TicketType.WEEK, BigDecimal.valueOf(1000.0), BigDecimal.valueOf(5000.0), List.of())
            );
        }
    }

    @Nested
    public class DeleteAll {
        @Test
        void shouldDeleteAllWhenMethodWASCalled() {
            repository.deleteAll();
            assertEquals(List.of(), repository.findAll());
        }
    }

    @Nested
    public class Count {
        @Test
        void shouldReturns4WhenMapConsistOfFourBusTickets() {
            assertEquals(4, repository.count());
        }

        @Test
        void shouldReturnsZeroWhenMapIsEmpty() {
            var busTicketRepository = new BusTicketRepository();

            assertEquals(0, busTicketRepository.count());
        }
    }

    private List<BusTicket> createListBusTickets() {
        return List.of(
                new BusTicket(UUID.fromString("1a111111-6795-4b38-b798-a28abd254eab"),
                        "STD",
                        TicketType.DAY,
                        "2025-01-01",
                        BigDecimal.valueOf(1.0)),
                new BusTicket(UUID.fromString("2a222222-6795-4b38-b798-a28abd254eab"),
                        "STD",
                        TicketType.DAY,
                        "2025-01-01",
                        BigDecimal.valueOf(10.0)),
                new BusTicket(UUID.fromString("3a333333-6795-4b38-b798-a28abd254eab"),
                        "STD",
                        TicketType.DAY,
                        "2025-01-01",
                        BigDecimal.valueOf(100.0)),
                new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                        "STD",
                        TicketType.YEAR,
                        "2025-01-01",
                        BigDecimal.valueOf(14.0)));
    }
}
