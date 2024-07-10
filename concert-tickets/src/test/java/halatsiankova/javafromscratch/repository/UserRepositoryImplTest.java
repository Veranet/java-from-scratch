package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.BaseRepositoryTest;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryImplTest extends BaseRepositoryTest {
    private static UserRepositoryImpl userRepository;

    @BeforeAll
    static void setup() {
        userRepository = new UserRepositoryImpl();
        userRepository.connection = userRepository.getConnection();
        var createDate = LocalDateTime.of(2025, 1, 2, 0, 0, 0);
        var ticketWithUserId1 = new Ticket(null, 1, TicketType.DAY,
                LocalDateTime.of(2025, 2, 1, 1, 1, 1));
        BaseUser userID1 = new BaseUser(null, "Ivan", createDate, List.of(ticketWithUserId1));

        var ticketWithUserId2 = new Ticket(null, 2, TicketType.DAY,
                LocalDateTime.of(2025, 2, 1, 1, 1, 1));
        BaseUser userID2 = new BaseUser(null, "Bob", createDate, List.of(ticketWithUserId2));

        userRepository.save(userID1);
        userRepository.save(userID2);
    }

    @Test
    void shouldSaveUserAndTickets() throws SQLException {
        var createDate = LocalDateTime.of(2025, 1, 2, 0, 0, 0);
        var name = "Petr";

        var ticket = new Ticket(null, 3, TicketType.YEAR,
                LocalDateTime.of(2025, 2, 1, 1, 1, 1));
        var user = new BaseUser(null, name, createDate, List.of(ticket));

        userRepository.save(user);

        var expected = new BaseUser(3, name, createDate, List.of(ticket));
        assertEquals(Optional.of(expected), userRepository.findById(3));
    }

    @Test
    void shouldReturnOptionalUserByIdWhenUserExist() throws SQLException {
        var ticket = new Ticket(2, 2, TicketType.DAY,
                LocalDateTime.of(2025, 2, 1, 1, 1, 1));
        var expected = Optional.of(new BaseUser(2, "Bob",
                LocalDateTime.of(2025, 1, 2, 0, 0, 0), List.of(ticket)));

        assertEquals(expected, userRepository.findById(2));
    }

    @Test
    void shouldReturnOptionalEmptyWhenUserDoesNotExist() throws SQLException {
        assertEquals(Optional.empty(), userRepository.findById(11));
    }

    @Test
    void shouldReturnFalseWhenUserWasNotDeleteById() {
        assertFalse(userRepository.deleteById(200));
    }

    @Test
    void shouldReturnTrueWhenUserWasDeletedById() {
        boolean firstDeletionResult = userRepository.deleteById(1);

        assertTrue(firstDeletionResult);
    }
}
