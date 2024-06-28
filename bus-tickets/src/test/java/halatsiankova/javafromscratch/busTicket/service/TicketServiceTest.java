package halatsiankova.javafromscratch.busTicket.service;

import halatsiankova.javafromscratch.busTicket.enumerated.TicketType;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;
import halatsiankova.javafromscratch.busTicket.repository.BusTicketRepository;
import halatsiankova.javafromscratch.busTicket.util.GeneratorUUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private BusTicketRepository repository;
    @Mock
    private GeneratorUUID generatorUUID;

    @InjectMocks
    private TicketService ticketService;

    @Nested
    public class SaveAll {
        @Test
        void shouldSaveAllBusTickets() {
            var tickets = List.of(
                    new BusTicket("STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(100.0)),
                    new BusTicket("STD",
                            TicketType.WEEK,
                            "2028-01-01",
                            BigDecimal.valueOf(10.0)));

            var firstTicketId = UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab");
            var secondTicketId = UUID.fromString("00b00000-0000-4b38-b798-a28abd254eab");
            when(generatorUUID.generateId()).thenReturn(firstTicketId, secondTicketId);
            var expected = List.of(
                    new BusTicket(firstTicketId,
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(100.0)),
                    new BusTicket(secondTicketId,
                            "STD",
                            TicketType.WEEK,
                            "2028-01-01",
                            BigDecimal.valueOf(10.0)));

            var actual = ticketService.saveAll(tickets);

            assertEquals(expected, actual);
        }

        @Test
        void shouldThrowsIllegalArgumentExceptionWhenNull() {
            var exception = assertThrows(IllegalArgumentException.class, () -> ticketService.saveAll(null));
            assertEquals("Collection of tickets must not be null.", exception.getMessage());
        }
    }

    @Nested
    public class GetById {
        @Test
        void shouldReturnsBusTicketByIdWhenBusTicketExist() {
            var ticketId = UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab");
            var ticket =
                    new BusTicket(ticketId,
                            "STD",
                            TicketType.WEEK,
                            "2028-01-01",
                            BigDecimal.valueOf(10.0));
            when(repository.findById(ticketId)).thenReturn(Optional.of(ticket));

            var expected = new BusTicket(ticketId,
                    "STD",
                    TicketType.WEEK,
                    "2028-01-01",
                    BigDecimal.valueOf(10.0));

            assertEquals(expected, ticketService.getById(ticketId));
        }

        @Test
        void shouldThrowsIllegalArgumentExceptionWhenBusTicketDoesNotExist() {
            var ticketId = UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab");
            when(repository.findById(ticketId)).thenReturn(Optional.empty());

            var exception =
                    assertThrows(IllegalArgumentException.class, () -> ticketService.getById(ticketId));

            assertEquals("BusTicket with id=11b11111-1111-4b38-b798-a28abd254eab doesn't exist.",
                    exception.getMessage());
        }

        @Test
        void shouldThrowsIllegalArgumentExceptionWhenNull() {
            var exception =
                    assertThrows(IllegalArgumentException.class, () -> ticketService.getById(null));

            assertEquals("Ticket ID must not be null.",
                    exception.getMessage());
        }
    }

    @Nested
    public class DeleteByTicketId {
        @Test
        void shouldDeleteByTicketId() {
            var ticketId = UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab");

            ticketService.deleteByTicketId(ticketId);

            verify(repository).deleteById(ticketId);
        }

        @Test
        void shouldThrowsIllegalArgumentExceptionWhenNull() {
            var exception = assertThrows(IllegalArgumentException.class, () -> ticketService.deleteByTicketId(null));
            assertEquals("Ticket ID must not be null.", exception.getMessage());
        }
    }

    @Nested
    public class GetAllByTicketIdAndPriceFromTo {
        @ParameterizedTest
        @MethodSource("successData")
        void should(List<BusTicket> tickets, TicketType type, BigDecimal priceFrom, BigDecimal priceTo,
                    List<BusTicket> expected, String description) {
            when(repository.findAllByTicketTypeAndPriceFromTo(type, priceFrom, priceTo)).thenReturn(tickets);

            var actual = ticketService.getAllByTicketIdAndPriceFromTo(type, priceFrom, priceTo);

            assertEquals(expected, actual, description);
        }

        @ParameterizedTest
        @MethodSource("failedData")
        void shouldThrowsWhenPriceFromGreaterThanPriceTo(TicketType ticketType, BigDecimal priceFrom, BigDecimal priceTo,
                                                         String exceptionMessage) {
            var exception = assertThrows(IllegalArgumentException.class,
                    () -> ticketService.getAllByTicketIdAndPriceFromTo(ticketType, priceFrom, priceTo));

            assertEquals(exceptionMessage, exception.getMessage());
            verify(repository, never()).findAllByTicketTypeAndPriceFromTo(ticketType, priceFrom, priceTo);
        }

        private static Stream<Arguments> successData() {
            return Stream.of(
                    arguments(List.of(
                                    new BusTicket(UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab"),
                                            "STD",
                                            TicketType.DAY,
                                            "2025-01-01",
                                            BigDecimal.valueOf(20.0)),
                                    new BusTicket(UUID.fromString("00b00000-0000-4b38-b798-a28abd254eab"),
                                            "STD",
                                            TicketType.WEEK,
                                            "2028-01-01",
                                            BigDecimal.valueOf(15.0))
                            ),
                            TicketType.WEEK,
                            BigDecimal.valueOf(15.0),
                            BigDecimal.valueOf(20.0),
                            List.of(
                                    new BusTicket(UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab"),
                                            "STD",
                                            TicketType.DAY,
                                            "2025-01-01",
                                            BigDecimal.valueOf(20.0)),
                                    new BusTicket(UUID.fromString("00b00000-0000-4b38-b798-a28abd254eab"),
                                            "STD",
                                            TicketType.WEEK,
                                            "2028-01-01",
                                            BigDecimal.valueOf(15.0))
                            ),
                            "when price from less than price to and supported type."
                    ),
                    arguments(List.of(
                                    new BusTicket(UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab"),
                                            "STD",
                                            TicketType.DAY,
                                            "2025-01-01",
                                            BigDecimal.valueOf(20.0))
                            ),
                            TicketType.WEEK,
                            BigDecimal.valueOf(0.0),
                            BigDecimal.valueOf(20.0),
                            List.of(
                                    new BusTicket(UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab"),
                                            "STD",
                                            TicketType.DAY,
                                            "2025-01-01",
                                            BigDecimal.valueOf(20.0))
                            ),
                            "when price from = 0"
                    ),
                    arguments(List.of(
                                    new BusTicket(UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab"),
                                            "STD",
                                            TicketType.DAY,
                                            "2025-01-01",
                                            BigDecimal.valueOf(15.0)),
                                    new BusTicket(UUID.fromString("00b00000-0000-4b38-b798-a28abd254eab"),
                                            "STD",
                                            TicketType.WEEK,
                                            "2028-01-01",
                                            BigDecimal.valueOf(15.0))
                            ),
                            TicketType.WEEK,
                            BigDecimal.valueOf(15.0),
                            BigDecimal.valueOf(15.0),
                            List.of(
                                    new BusTicket(UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab"),
                                            "STD",
                                            TicketType.DAY,
                                            "2025-01-01",
                                            BigDecimal.valueOf(15.0)),
                                    new BusTicket(UUID.fromString("00b00000-0000-4b38-b798-a28abd254eab"),
                                            "STD",
                                            TicketType.WEEK,
                                            "2028-01-01",
                                            BigDecimal.valueOf(15.0))
                            ),
                            "when price from = price to."
                    ),
                    arguments(List.of(), TicketType.WEEK, BigDecimal.valueOf(15.0), BigDecimal.valueOf(20.0), List.of(),
                            "should return an empty list when no such tickets exist."
                    )
            );
        }

        private static Stream<Arguments> failedData() {
            return Stream.of(
                    arguments(null, BigDecimal.valueOf(15.0), BigDecimal.valueOf(20.0),
                            "Ticket type, price from, price to must not be null."
                    ),
                    arguments(TicketType.DAY, null, BigDecimal.valueOf(20.0),
                            "Ticket type, price from, price to must not be null."
                    ),
                    arguments(TicketType.DAY, BigDecimal.valueOf(20.0), null,
                            "Ticket type, price from, price to must not be null."
                    ),
                    arguments(TicketType.DAY, BigDecimal.valueOf(150.0), BigDecimal.valueOf(20.0),
                            "The price from must be less than the price to."
                    )
            );
        }
    }
}
