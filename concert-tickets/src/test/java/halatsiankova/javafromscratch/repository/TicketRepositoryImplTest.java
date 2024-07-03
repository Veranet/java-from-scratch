package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.BaseRepositoryTest;
import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketRepositoryImplTest extends BaseRepositoryTest {

    private TicketRepositoryImpl ticketRepository;

    @BeforeEach
    void init() throws SQLException {
        ticketRepository = new TicketRepositoryImpl(con);
    }

    @Test
    void shouldSaveTicket() throws SQLException {
        LocalDateTime createDate = LocalDateTime.of(2024, 7, 1, 0, 0 ,0);
        Ticket ticket = new Ticket(null, 1, TicketType.DAY, StadiumSector.C, createDate);

        ticketRepository.save(ticket);

        Optional<Ticket> expected = Optional.of(new Ticket(5, 3, TicketType.DAY, StadiumSector.C, createDate));

        assertEquals(expected, ticketRepository.findById(5));
    }

    @Test
    void shouldReturnOptionalTicketByIdWhenTicketExist() throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);

        Optional<Ticket> expected =
                Optional.of(new Ticket(1, 1, TicketType.DAY, StadiumSector.A, localDateTime));

        assertEquals(expected, ticketRepository.findById(1));
    }

    @Test
    void shouldReturnOptionalEmptyWhenTicketDoesNotExist() throws SQLException {
        assertEquals(Optional.empty(), ticketRepository.findById(100));
    }

    @Test
    void shouldReturnListTicketsByStadiumSector() throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);

        List<Ticket> expected = List.of(
                new Ticket(1, 1, TicketType.DAY, StadiumSector.A, localDateTime),
                new Ticket(2, 3, TicketType.DAY, StadiumSector.A, localDateTime),
                new Ticket(3, 2, TicketType.WEEK, StadiumSector.A, localDateTime));

        assertEquals(expected, ticketRepository.findTicketByStadiumSector(StadiumSector.A));
    }

    @Test
    void shouldReturnEmptyListTicketsByStadiumSectorWhenTicketsDidNotExist() throws SQLException {
        assertEquals(List.of(), ticketRepository.findTicketByStadiumSector(StadiumSector.B));
    }

    @Test
    void shouldReturnListTicketsByUserId() throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);

        List<Ticket> expected = List.of(
                new Ticket(1, 1, TicketType.DAY, StadiumSector.A, localDateTime),
                new Ticket(4, 1, TicketType.MONTH, StadiumSector.C, localDateTime));

        assertEquals(expected, ticketRepository.findAllByUserId(1));
    }

    @Test
    void shouldReturnEmptyListWhenTicketsByUserIdDidNotExist() throws SQLException {
        assertEquals(List.of(), ticketRepository.findAllByUserId(5));
    }

    @Test
    void shouldUpdateTicket() throws SQLException {
        ticketRepository.update(TicketType.YEAR, 1);

        Optional<Ticket> actual = ticketRepository.findById(1);

        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);
        Optional<Ticket> expected = Optional.of(new Ticket(1, 1, TicketType.YEAR, StadiumSector.A,localDateTime));
        assertEquals(expected, actual);
    }
}
