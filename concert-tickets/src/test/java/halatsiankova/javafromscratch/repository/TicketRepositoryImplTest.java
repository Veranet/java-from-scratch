package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.BaseRepositoryTest;
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
    void init() {
        ticketRepository = new TicketRepositoryImpl();
        var createDate = LocalDateTime.of(2024, 7, 1, 0, 0 ,0);
        var ticketWithUserId1 = new Ticket(null, 1, TicketType.DAY, createDate);
        ticketRepository.save(ticketWithUserId1);
    }


    @Test
    void shouldSaveTicket() throws SQLException {
        var createDate = LocalDateTime.of(2024, 7, 1, 0, 0 ,0);
        var ticket = new Ticket(null, 1, TicketType.DAY, createDate);

        ticketRepository.save(ticket);

        var expected = Optional.of(new Ticket(3, 1, TicketType.DAY, createDate));
        assertEquals(expected, ticketRepository.findById(3));
    }

    @Test
    void shouldReturnOptionalTicketByIdWhenTicketExist() throws SQLException {
        var localDateTime = LocalDateTime.of(2024, 7, 1, 0, 0, 0);

        var expected = Optional.of(new Ticket(1, 1, TicketType.DAY, localDateTime));

        assertEquals(expected, ticketRepository.findById(1));
    }

    @Test
    void shouldReturnOptionalEmptyWhenTicketDoesNotExist() throws SQLException {
        assertEquals(Optional.empty(), ticketRepository.findById(100));
    }

    @Test
    void shouldReturnListTicketsByUserId() throws SQLException {
        var localDateTime = LocalDateTime.of(2024, 7, 1, 0, 0, 0);

        var expected = List.of(
                new Ticket(1, 1, TicketType.DAY, localDateTime),
                new Ticket(2, 1, TicketType.DAY, localDateTime));

        assertEquals(expected, ticketRepository.findAllByUserId(1));
    }

    @Test
    void shouldReturnEmptyListWhenTicketsByUserIdDidNotExist() throws SQLException {
        assertEquals(List.of(), ticketRepository.findAllByUserId(5));
    }

    @Test
    void shouldUpdateTicket() throws SQLException {
        ticketRepository.update(TicketType.YEAR, 1);

        var actual = ticketRepository.findById(1);

        var localDateTime = LocalDateTime.of(2024, 7, 1, 0, 0, 0);
        var expected = Optional.of(new Ticket(1, 1, TicketType.YEAR, localDateTime));
        assertEquals(expected, actual);
    }
}
