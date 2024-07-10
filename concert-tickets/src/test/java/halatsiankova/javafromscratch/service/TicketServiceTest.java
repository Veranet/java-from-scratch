package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.repository.TicketRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketServiceTest {

    private final TicketRepositoryImpl ticketRepository = mock(TicketRepositoryImpl.class);
    private final TicketService ticketService = new TicketService(ticketRepository);

    @Test
    void shouldAadTicket() throws SQLException {
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var ticket = new Ticket(null, 1, TicketType.DAY, StadiumSector.A, localDateTime);
        doNothing().when(ticketRepository).save(ticket);

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
    void shouldReturnTicketById() throws SQLException {
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var ticket = new Ticket(1, 1, TicketType.DAY, StadiumSector.A, localDateTime);
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        var actual = ticketService.getTicketById(1);

        var expected = new Ticket(1, 1, TicketType.DAY, StadiumSector.A, localDateTime);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenIdLess0() {
        var exception =
                assertThrows(IllegalArgumentException.class, () -> ticketService.getTicketById(-2));

        assertEquals("Ticket ID must not be negative or equal to 0.", exception.getMessage());
    }

    @Test
    void shouldReturnListTicketsByStadiumSector() throws SQLException {
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var tickets = List.of(
                new Ticket(1, 2,TicketType.DAY, StadiumSector.A, localDateTime),
                new Ticket(2, 2,TicketType.DAY, StadiumSector.A, localDateTime));
        when(ticketRepository.findTicketByStadiumSector(StadiumSector.A)).thenReturn(tickets);

        var actual = ticketService.getTicketsByStadiumSector(StadiumSector.A);

        var expected = List.of(
                new Ticket(1, 2,TicketType.DAY, StadiumSector.A, localDateTime),
                new Ticket(2, 2,TicketType.DAY, StadiumSector.A, localDateTime));
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyListTicketsByStadiumSectorWhenTicketsDidNotExist() throws SQLException {
        when(ticketRepository.findTicketByStadiumSector(StadiumSector.A)).thenReturn(List.of());

        assertEquals(List.of(), ticketService.getTicketsByStadiumSector(StadiumSector.A));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenStadiumSectorNull() {
        var exception =
                assertThrows(IllegalArgumentException.class, () -> ticketService.getTicketsByStadiumSector(null));

        assertEquals("Stadium sector must not be null.", exception.getMessage());
    }

    @Test
    void shouldReturnListTicketsByUserId() throws SQLException {
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var tickets = List.of(
                new Ticket(1, 2,TicketType.DAY, StadiumSector.A, localDateTime),
                new Ticket(2, 2,TicketType.DAY, StadiumSector.A, localDateTime));
        when(ticketRepository.findAllByUserId(2)).thenReturn(tickets);

        var actual = ticketService.getAllTicketsByUserId(2);

        var expected = List.of(
                new Ticket(1, 2,TicketType.DAY, StadiumSector.A, localDateTime),
                new Ticket(2, 2,TicketType.DAY, StadiumSector.A, localDateTime));
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyListTicketsByUserIdWhenTicketsDidNotExist() throws SQLException {
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
    void shouldUpdateTicket() throws SQLException {
        doNothing().when(ticketRepository).update(TicketType.YEAR, 2);

        ticketService.update(TicketType.YEAR, 2);

        verify(ticketRepository).update(TicketType.YEAR, 2);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenTicketTypeNull() {
        var exception =
                assertThrows(IllegalArgumentException.class, () -> ticketService.update(null, 2));

        assertEquals("Ticket type must not be null.", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenTicketIDLess0() {
        var exception =
                assertThrows(IllegalArgumentException.class, () -> ticketService.update(TicketType.YEAR, -2));

        assertEquals("Ticket ID must not be negative or equal to 0.", exception.getMessage());
    }
}
