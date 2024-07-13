package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.enumerated.Status;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/testDb.sql")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUserAndTickets() {
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
    void shouldReturnOptionalUserByIdWhenUserExist() {
        var ticket = new Ticket(3, 2, TicketType.WEEK,
                LocalDateTime.of(2024, 6, 30, 10, 0, 0));
        var expected = Optional.of(new BaseUser(2, "Alex",
                LocalDateTime.of(2024, 1, 2, 0, 0, 0),
                Status.ACTIVATED, Set.of(ticket)));

        assertEquals(expected, userRepository.findById(2));
    }

    @Test
    void shouldReturnOptionalEmptyWhenUserDoesNotExist() {
        assertEquals(Optional.empty(), userRepository.findById(11));
    }

    @Test
    void shouldUpdateUserAndTicketsWhenUserStatusIsActive() {
        userRepository.updateBaseUserByIdAndTicket(3, TicketType.WEEK.name(),
                LocalDateTime.of(2024, 1, 2, 0, 0, 0));

        var userExpected = Optional.of(new BaseUser(3, "Bob",
                LocalDateTime.of(2023, 1, 2, 0, 0, 0),
                Status.ACTIVATED, Set.of(
                new Ticket(2, 3, TicketType.DAY,
                        LocalDateTime.of(2024, 6, 30, 10, 0, 0)),
                new Ticket(5, 3, TicketType.WEEK,
                        LocalDateTime.of(2024, 1, 2, 0, 0, 0))
        )));
        assertEquals(userExpected, userRepository.findById(3));
    }

    @Test
    void shouldNotUpdateUserAndSaveTicketsWhenUserStatusIsInactive() {
        var userWithStatusInactivated = new BaseUser(null,"Jack",
                LocalDateTime.of(2023, 1, 2, 0, 0, 0),
                Status.INACTIVATED, Set.of(
                new Ticket(null, 4, TicketType.DAY,
                        LocalDateTime.of(2024, 6, 30, 10, 0, 0))
        ));
        userRepository.save(userWithStatusInactivated);
        userRepository.updateBaseUserByIdAndTicket(4, TicketType.WEEK.name(),
                LocalDateTime.of(2024, 1, 2, 0, 0, 0));

        var userExpected = Optional.of(new BaseUser(4, "Jack",
                LocalDateTime.of(2023, 1, 2, 0, 0, 0),
                Status.INACTIVATED, Set.of(
                new Ticket(5, 4, TicketType.DAY,
                        LocalDateTime.of(2024, 6, 30, 10, 0, 0))
        )));
        assertEquals(userExpected, userRepository.findById(4));
    }

    @Test
    void shouldDeleteUserAndTicketsByUserId() {
        var usersCountBefore = userRepository.count();
        userRepository.deleteById(1);
        var usersCountAfter = userRepository.count();
        assertEquals(usersCountBefore, usersCountAfter + 1);
    }
}
