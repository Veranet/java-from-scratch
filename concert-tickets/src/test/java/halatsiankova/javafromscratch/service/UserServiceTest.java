package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.enumerated.Role;
import halatsiankova.javafromscratch.enumerated.Status;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Client;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);

    private UserService userService = new UserService(userRepository, true);

    @Test
    void shouldAddUser() {
        var createDate = LocalDateTime.of(2024, 5, 5, 0, 0);
        var user = new BaseUser(null, "name", createDate, Status.ACTIVATED, Set.of());

        when(userRepository.save(user)).thenReturn(any());

        userService.add(user);

        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.add(null));
    }

    @Test
    void shouldReturnUserById() {
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var user = new Client(2, Role.CLIENT, "Client", localDateTime);

        when(userRepository.findById(2)).thenReturn(Optional.of(user));

        var actual = userService.getUserById(2);

        var expected = new Client(2, Role.CLIENT, "Client", localDateTime);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserWithIdDoesNotExist() throws SQLException {
        when(userRepository.findById(3)).thenReturn(Optional.empty());

        var exception
                = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(3));
        assertEquals("User with ID = 3 does not exist.", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserIdLess0() {
        var exception
                = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(-3));
        assertEquals("User ID must not be negative or equal to 0.", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserIDLess0() {
        var exception =
                assertThrows(IllegalArgumentException.class, () -> userService.deleteUserById(-1));
        assertEquals("User ID must not be negative or equal to 0.", exception.getMessage());
    }

    @Test
    void shouldDeleteUserById() {
        var createDate = LocalDateTime.of(2024, 5, 5, 0, 0);
        var user = new BaseUser(null, "name", createDate, Status.ACTIVATED, Set.of());
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1);

        userService.deleteUserById(1);

        verify(userRepository).deleteById(1);
    }

    @Test
    void shouldUpdateUser() throws SQLException {
        Set<Ticket> ticketList = new HashSet<>();
        var ticket = new Ticket(null, 1, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));
        ticketList.add(ticket);
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var user = new BaseUser(2, "Client", localDateTime, Status.ACTIVATED, ticketList);
        var ticketUpdate = new Ticket(null, 0, TicketType.DAY, localDateTime);
        doNothing().when(userRepository).updateBaseUserByIdAndTicket(user.getId(), ticketUpdate.getType().name(), ticketUpdate.getCreatedDateTime());

        userService.updateUserAndSaveTickets(user, ticketUpdate);

        verify(userRepository).updateBaseUserByIdAndTicket(user.getId(), ticketUpdate.getType().name(), ticketUpdate.getCreatedDateTime());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserIsNull() {
        var ticket = new Ticket(null, 1, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));

        var exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUserAndSaveTickets(null, ticket));
        assertEquals("User must not be null.", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenTicketIsNull()  {
        Set<Ticket> ticketList = new HashSet<>();
        var ticket = new Ticket(null, 1, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));
        ticketList.add(ticket);
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var user = new BaseUser(2, "Client", localDateTime, Status.ACTIVATED, ticketList);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUserAndSaveTickets(user, null));
        assertEquals("Ticket must not be null.", exception.getMessage());
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenUpdateDisabled() {
        userService = new UserService(userRepository, false);

        Set<Ticket> ticketList = new HashSet<>();
        var ticket = new Ticket(null, 1, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));
        ticketList.add(ticket);
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var user = new BaseUser(2, "Client", localDateTime, Status.ACTIVATED, ticketList);

        var exception = assertThrows(UnsupportedOperationException.class,
                () -> userService.updateUserAndSaveTickets(user, ticket));
        assertEquals("Update not supported.", exception.getMessage());
    }
}
