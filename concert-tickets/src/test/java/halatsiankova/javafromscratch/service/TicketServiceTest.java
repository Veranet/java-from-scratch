package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketServiceTest {

    private final TicketRepository ticketRepository = mock(TicketRepository.class);
    private final TicketService ticketService = new TicketService(ticketRepository);

    @Test
    void shouldAadTicket() {
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var ticket = new Ticket(null, 1, TicketType.DAY, localDateTime);
        when(ticketRepository.save(ticket)).thenReturn(any());

        ticketService.add(ticket);

        verify(ticketRepository).save(ticket);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNull() {
        var exception =
                assertThrows(IllegalArgumentException.class, () -> ticketService.add(null));

        assertEquals("Ticket must not be null.", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenIdLess0() {
        var exception =
                assertThrows(IllegalArgumentException.class, () -> ticketService.getTicketById(-2));

        assertEquals("Ticket ID must not be negative or equal to 0.", exception.getMessage());
    }

    @Test
    void shouldReturnListTicketsByUserId() {
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var tickets = List.of(
                new Ticket(1, 2,TicketType.DAY, localDateTime),
                new Ticket(2, 2,TicketType.DAY, localDateTime));
        when(ticketRepository.findAllByUserId(2)).thenReturn(tickets);

        var actual = ticketService.getAllTicketsByUserId(2);

        var expected = List.of(
                new Ticket(1, 2,TicketType.DAY, localDateTime),
                new Ticket(2, 2,TicketType.DAY, localDateTime));
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyListTicketsByUserIdWhenTicketsDidNotExist() {
        when(ticketRepository.findAllByUserId(1)).thenReturn(List.of());

        assertEquals(List.of(), ticketService.getAllTicketsByUserId(1));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserIdLess0() {
        var exception =
                assertThrows(IllegalArgumentException.class, () -> ticketService.getAllTicketsByUserId(-3));

        assertEquals("User ID must not be negative or equal to 0.", exception.getMessage());
    }

    @Test
    void shouldUpdateTicket() {
        doNothing().when(ticketRepository).updateTicketTypeById(2, TicketType.YEAR.name());

        ticketService.update(TicketType.YEAR, 2);

        verify(ticketRepository).updateTicketTypeById(2, TicketType.YEAR.name());
    }

    @ParameterizedTest
    @MethodSource("failedDataForTickets")
    void shouldThrowIllegalArgumentExceptionWhenInvalidTicketParams(int ticketId,
                                                                    TicketType ticketType,
                                                                    String exceptionMessage) {
        var exception = assertThrows(IllegalArgumentException.class, () -> ticketService.update(ticketType, ticketId));

        assertEquals(exceptionMessage, exception.getMessage());
    }

    private static Stream<Arguments> failedDataForTickets() {
        return Stream.of(
                arguments(0, TicketType.DAY, "Ticket ID must not be negative or equal to 0."),
                arguments(-2, TicketType.DAY, "Ticket ID must not be negative or equal to 0."),
                arguments(1, null, "Ticket type must not be null.")
        );
    }
}
