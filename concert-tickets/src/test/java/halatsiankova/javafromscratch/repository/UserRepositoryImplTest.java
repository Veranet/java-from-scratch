package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.BaseRepositoryTest;
import halatsiankova.javafromscratch.connection.ConnectionDataBasePSQL;
import halatsiankova.javafromscratch.enumerated.Role;
import halatsiankova.javafromscratch.model.Admin;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryImplTest extends BaseRepositoryTest {
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void init() {
        userRepository = new UserRepositoryImpl(con);
    }

    @Test
    void shouldSaveUser() throws SQLException {
        LocalDateTime createDate = LocalDateTime.of(2024, 7, 1, 0, 0 ,0);
        String name = "Mark";
        BaseUser user = new Admin(null, Role.ADMIN, name, createDate);

        userRepository.save(user);

        int id = userRepository.findIdByUserNameAndCreationDate(name, Timestamp.valueOf(createDate));

        BaseUser expected = new Admin(id, Role.ADMIN, name, createDate);
        assertEquals(Optional.of(expected), userRepository.findById(id));
    }

    @Test
    void shouldReturnOptionalUserByIdWhenUserExist() throws SQLException {
        Optional<BaseUser> expected = Optional.of(new Admin(1, Role.ADMIN, "Ivan",
                LocalDateTime.of(2025, 1, 2, 0, 0, 0)));
        assertEquals(expected, userRepository.findById(1));
    }

    @Test
    void shouldReturnIllegalArgumentExceptionWhenUserDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.findById(11));
    }

    @Test
    void shouldReturnRoleByUserId() throws SQLException {
        assertEquals(Optional.of(Role.CLIENT), userRepository.findRoleByUserId(2));
    }

    @Test
    void shouldReturnOptionalEmptyWhenRoleWithUserIdDoesNotExist() throws SQLException {
        assertEquals(Optional.empty(), userRepository.findRoleByUserId(20));
    }

    @Test
    void shouldReturnListUsersWhenUsersAreExist() throws SQLException {
        List<BaseUser> expected = List.of(
        new Admin(1, Role.ADMIN, "Ivan", LocalDateTime.of(2025, 1, 2, 0, 0)),
        new Client(2, Role.CLIENT, "Alex", LocalDateTime.of(2024, 1, 2, 0, 0)),
        new Client(3, Role.CLIENT, "Bob", LocalDateTime.of(2023, 1, 2, 0, 0)));
        assertEquals(expected, userRepository.findAll());
    }

    @Test
    void shouldReturnTrueWhenUserWasDeleteById() throws SQLException {
        assertTrue(userRepository.deleteById(1));
    }

    @Test
    void shouldReturnFalseWhenUserWasNotDeleteById() throws SQLException {
        assertFalse(userRepository.deleteById(200));
    }

    @Test
    void shouldReturnIdByUserNameAndCreationDate() throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 2, 0, 0);
        assertEquals(3, userRepository.findIdByUserNameAndCreationDate("Bob", Timestamp.valueOf(localDateTime)));
    }

    @Test
    void shouldReturnOptionalRoleWhenUserIdExist() throws SQLException {
        assertEquals(Optional.of(Role.CLIENT), userRepository.findRoleByUserId(3));
    }

    @Test
    void shouldReturnOptionalEmptyWhenUserIdDoesNotExist() throws SQLException {
        assertEquals(Optional.empty(), userRepository.findRoleByUserId(30));
    }
}
