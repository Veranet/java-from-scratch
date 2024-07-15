package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.enumerated.Role;
import halatsiankova.javafromscratch.enumerated.Status;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Client;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void shouldThrowIllegalArgumentExceptionWhenUserWithIdDoesNotExist() {
        when(userRepository.findById(3)).thenReturn(Optional.empty());

        var exception
                = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(3));
        assertEquals("User with ID = 3 does not exist.", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("failedDataForUsers")
    void shouldThrowIllegalArgumentExceptionWhenUserIdInvalid(int userId, String exceptionMessage) {
        var exception
                = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(userId));
        assertEquals(exceptionMessage, exception.getMessage());
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
    void shouldUpdateUser() {
        Set<Ticket> ticketList = new HashSet<>();
        var ticket = new Ticket(null, 1, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));
        ticketList.add(ticket);
        var localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        var ticketUpdate = new Ticket(null, 0, TicketType.DAY, localDateTime);
        doNothing().when(userRepository)
                .updateBaseUserByIdAndTicket(2, ticketUpdate.getType().name(), ticketUpdate.getCreatedDateTime());

        userService.updateUserAndSaveTickets(2, ticketUpdate);

        verify(userRepository).updateBaseUserByIdAndTicket(2, ticketUpdate.getType().name(),
                ticketUpdate.getCreatedDateTime());
    }

    @ParameterizedTest
    @MethodSource("failedDataForUsers")
    void shouldThrowIllegalArgumentExceptionWhenUserIsInvalid(int userId, String exceptionMessage) {
        var ticket = new Ticket(null, 1, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));

        var exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUserAndSaveTickets(userId, ticket));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenTicketIsNull()  {
        Set<Ticket> ticketList = new HashSet<>();
        var ticket = new Ticket(null, 1, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));
        ticketList.add(ticket);

        var exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUserAndSaveTickets(2, null));
        assertEquals("Ticket must not be null.", exception.getMessage());
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenUpdateDisabled() {
        userService = new UserService(userRepository, false);

        Set<Ticket> ticketList = new HashSet<>();
        var ticket = new Ticket(null, 1, TicketType.DAY,
                LocalDateTime.of(2024, 2, 1, 1, 1, 1));
        ticketList.add(ticket);

        var exception = assertThrows(UnsupportedOperationException.class,
                () -> userService.updateUserAndSaveTickets(2, ticket));
        assertEquals("Update not supported.", exception.getMessage());
    }

    public static Stream<Arguments> failedDataForUsers() {
        return Stream.of(
                arguments(0, "User ID must not be negative or equal to 0."),
                arguments(-2, "User ID must not be negative or equal to 0.")
        );
    }
}
