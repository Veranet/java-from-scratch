package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.enumerated.Role;
import halatsiankova.javafromscratch.model.Admin;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Client;
import halatsiankova.javafromscratch.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private UserRepositoryImpl userRepository = mock(UserRepositoryImpl.class);

    private UserService userService = new UserService(userRepository);

    @Test
    void shouldAddUser() throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        BaseUser user = new Client(null, Role.CLIENT, "Client", localDateTime);
        doNothing().when(userRepository).save(user);

        userService.add(user);

        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.add(null));
    }

    @Test
    void shouldReturnUserById() throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 5, 5, 0, 0);
        BaseUser user = new Client(2, Role.CLIENT, "Client", localDateTime);

        when(userRepository.findById(2)).thenReturn(Optional.of(user));

        BaseUser actual = userService.getUserById(2);

        BaseUser expected = new Client(2, Role.CLIENT, "Client", localDateTime);
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
    void shouldReturnListUsers() throws SQLException {
        var dateForClient = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        var dateForAdmin = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        List<BaseUser> listUsers = List.of(
                new Client(1, Role.CLIENT, "AB", dateForClient),
                new Admin(2, Role.ADMIN, "BD", dateForAdmin)
        );
        when(userRepository.findAll()).thenReturn(listUsers);

        List<BaseUser> actual = userService.getAllUsers();

        List<BaseUser> expected = List.of(
                new Client(1, Role.CLIENT, "AB", dateForClient),
                new Admin(2, Role.ADMIN, "BD", dateForAdmin)
        );
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserIDLess0() throws SQLException {
        var exception =
                assertThrows(IllegalArgumentException.class, () -> userService.deleteUserById(-1));
        assertEquals("User ID must not be negative or equal to 0.", exception.getMessage());
    }

    @Test
    void shouldDeleteUserById() throws SQLException {
        when(userRepository.deleteById(1)).thenReturn(true);

        userService.deleteUserById(1);

        verify(userRepository).deleteById(1);
    }
}
