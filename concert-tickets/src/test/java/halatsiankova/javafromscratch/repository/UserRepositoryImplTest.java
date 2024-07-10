package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.BaseRepositoryTest;
import halatsiankova.javafromscratch.enumerated.Status;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryImplTest extends BaseRepositoryTest {
    private static final UserRepositoryImpl userRepository = new UserRepositoryImpl();

    @BeforeAll
    static void setup() {
        var createDate = LocalDateTime.of(2025, 1, 2, 0, 0, 0);
        var ticketWithUserId1 = new Ticket(null, 1, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));
        BaseUser userID1 = new BaseUser(null, "Ivan", createDate, Status.ACTIVATED, Set.of(ticketWithUserId1));

        var ticketWithUserId2 = new Ticket(null, 2, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));
        BaseUser userID2 = new BaseUser(null, "Bob", createDate, Status.ACTIVATED, Set.of(ticketWithUserId2));

        userRepository.save(userID1);
        userRepository.save(userID2);
    }

    @Test
    void shouldSaveUserAndTickets() throws SQLException {
        var createDate = LocalDateTime.of(2025, 1, 2, 0, 0, 0);
        var name = "Petr";

        var ticket = new Ticket(null, 4, TicketType.YEAR,
                LocalDateTime.of(2025, 2, 1, 1, 1, 1));
        var user = new BaseUser(null, name, createDate, Status.ACTIVATED, Set.of(ticket));

        userRepository.save(user);

        var expected = new BaseUser(4, name, createDate, Status.ACTIVATED, Set.of(ticket));
        assertEquals(Optional.of(expected), userRepository.findById(4));
    }

    @Test
    void shouldReturnOptionalUserByIdWhenUserExist() throws SQLException {
        var ticket = new Ticket(9, 2, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));
        var expected = Optional.of(new BaseUser(2, "Bob",
                LocalDateTime.of(2025, 1, 2, 0, 0, 0),
                Status.ACTIVATED, Set.of(ticket)));

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
        var ticket =
                new Ticket(null, 1, TicketType.DAY,
                        LocalDateTime.of(2025, 2, 1, 1, 1, 1));
        var user =
                new BaseUser(null, "Alex",
                        LocalDateTime.of(2025, 1, 2, 0, 0, 0),
                        Status.ACTIVATED, Set.of(ticket));

        userRepository.save(user);
        boolean firstDeletionResult = userRepository.deleteById(1);

        assertTrue(firstDeletionResult);
    }

    @Test
    void shouldUpdateUserAndSaveTicketWhenUserIsActivated() throws SQLException {
        Set<Ticket> ticketList = new HashSet<>();
        Ticket ticket = new Ticket(null, 1, TicketType.DAY, LocalDateTime.of(2024, 2, 1, 1, 1, 1));
        ticketList.add(ticket);

        BaseUser baseUser = new BaseUser(2, "Ivan",
                LocalDateTime.of(2025, 1, 2, 0, 0, 0), Status.ACTIVATED, ticketList);


        userRepository.updateUserAndSaveTicket(baseUser, new Ticket(null, 0,TicketType.DAY, LocalDateTime.of(2024, 2, 1, 1, 1, 1)));

        Set<Ticket> expectedTicketList = Set.of(
                new Ticket(12, 2, TicketType.DAY, LocalDateTime.of(2024, 2, 1, 1, 1, 1)),
                new Ticket(9, 2, TicketType.DAY, LocalDateTime.of(2024, 2, 1, 1, 1, 1))
        );
        BaseUser expected = new BaseUser(2, "Ivan",
                LocalDateTime.of(2025, 1, 2, 0, 0, 0), Status.ACTIVATED, expectedTicketList);
        assertEquals(Optional.of(expected), userRepository.findById(2));
    }
}
