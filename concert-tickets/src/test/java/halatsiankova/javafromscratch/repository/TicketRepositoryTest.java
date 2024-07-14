package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/testDb.sql")
class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;


    @Test
    void shouldSaveTicket() {
        var createDate = LocalDateTime.of(2024, 7, 1, 0, 0 ,0);
        var ticket = new Ticket(null, 1, TicketType.DAY, createDate);

        ticketRepository.save(ticket);

        var expected = Optional.of(new Ticket(5, 1, TicketType.DAY, createDate));
        assertEquals(expected, ticketRepository.findById(5));
    }

    @Test
    void shouldReturnOptionalTicketByIdWhenTicketExist() {
        var localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);

        var expected = Optional.of(new Ticket(1, 1, TicketType.DAY, localDateTime));

        assertEquals(expected, ticketRepository.findById(1));
    }

    @Test
    void shouldReturnOptionalEmptyWhenTicketDoesNotExist() {
        assertEquals(Optional.empty(), ticketRepository.findById(100));
    }

    @Test
    void shouldReturnListTicketsByUserId() {
        var localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);

        var expected = List.of(
                new Ticket(1, 1, TicketType.DAY, localDateTime),
                new Ticket(4, 1, TicketType.MONTH, localDateTime));

        assertEquals(expected, ticketRepository.findAllByUserId(1));
    }

    @Test
    void shouldReturnEmptyListWhenTicketsByUserIdDidNotExist() {
        assertEquals(List.of(), ticketRepository.findAllByUserId(5));
    }

    @Test
    void shouldUpdateTicket() {
        ticketRepository.updateTicketTypeById(1, TicketType.YEAR.name());

        var actual = ticketRepository.findById(1);

        var localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);
        var expected = Optional.of(new Ticket(1, 1, TicketType.YEAR, localDateTime));
        assertEquals(expected, actual);
    }
}
