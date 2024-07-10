package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryImplTest {
    private static UserRepositoryImpl userRepository;
    private static TicketRepositoryImpl ticketRepository;

    @BeforeAll
    static void setup() {
        userRepository = new UserRepositoryImpl();
        userRepository.connection = userRepository.getConnection();
        ticketRepository = new TicketRepositoryImpl();
        var createDate = LocalDateTime.of(2025, 1, 2, 0, 0, 0);
        var name = "Ivan";
        BaseUser user = new BaseUser(null, name, createDate);

        userRepository.save(user);
    }

    @Test
    void shouldSaveUser() throws SQLException {
        var createDate = LocalDateTime.of(2025, 1, 2, 0, 0, 0);
        var name = "Alex";
        var user = new BaseUser(null, name, createDate);

        userRepository.save(user);

        var expected = new BaseUser(2, name, createDate);
        assertEquals(Optional.of(expected), userRepository.findById(2));
    }

    @Test
    void shouldReturnOptionalUserByIdWhenUserExist() throws SQLException {
        var expected = Optional.of(new BaseUser(2, "Alex",
                LocalDateTime.of(2025, 1, 2, 0, 0, 0)));
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
        var ticket = new Ticket(null, 1, TicketType.DAY, LocalDateTime.of(2025, 2, 1, 1, 1, 1));
        var user = new BaseUser(null, "Alex", LocalDateTime.of(2025, 1, 2, 0, 0, 0));

        ticketRepository.save(ticket);
        userRepository.save(user);

        boolean firstDeletionResult = userRepository.deleteById(1);
        assertTrue(firstDeletionResult);
    }
}
