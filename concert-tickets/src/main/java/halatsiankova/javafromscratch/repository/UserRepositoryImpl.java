package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.enumerated.Role;
import halatsiankova.javafromscratch.model.Admin;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static halatsiankova.javafromscratch.enumerated.Role.ADMIN;

public class UserRepositoryImpl implements UserRepository {
    Connection connection;

    public UserRepositoryImpl() throws SQLException {
        this.connection =
                DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/my_ticket_service_db", "myuser", "mypassword");
    }

    @Override
    public void save(BaseUser user) throws SQLException {
        var INSERT_SQL = "INSERT INTO users (user_name, creation_date) values (?, ?)";
        try (PreparedStatement pr = connection.prepareStatement(INSERT_SQL)) {
            pr.setString(1, user.getName());
            pr.setTimestamp(2, Timestamp.valueOf(user.getCreateDate()));
            pr.executeUpdate();
        }
        int userId = findIdByUserNameAndCreationDate(user.getName(), Timestamp.valueOf(user.getCreateDate()));
        saveRole(userId);
    }

    @Override
    public Optional<BaseUser> findById(Integer id) throws SQLException {
        var FIND_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
        Optional<Role> role = findRoleByUserId(id);
        if(role.isEmpty()) {
            throw new IllegalArgumentException(String.format("User with ID=%d does not exist.", id));
        }
        Role userRole = role.get();
        BaseUser user;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    if (userRole == ADMIN) {
                        user = new Admin();
                    } else {
                        user = new Client();
                    }
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setCreateDate(resultSet.getObject("creation_date", LocalDateTime.class));
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Role> findRoleByUserId(int userId) throws SQLException {
        Role role;
        var FIND_ROLE_BY_USER_ID_SQL = "SELECT role FROM user_role WHERE user_id = ?";
        try (PreparedStatement pr = connection.prepareStatement(FIND_ROLE_BY_USER_ID_SQL)) {
            pr.setInt(1, userId);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                String result = rs.getString("role");
                role = Role.valueOf(result);
                return Optional.of(role);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<BaseUser> findAll() throws SQLException {
        List<BaseUser> users = new ArrayList<>();
        var SELECT_ALL_SQL = "SELECT * FROM users ORDER BY id";
        try (PreparedStatement pr = connection.prepareStatement(SELECT_ALL_SQL)) {
            ResultSet rs = pr.executeQuery();
            while ((rs.next())) {
                int userId = rs.getInt(1);
                var name = rs.getString(2);
                LocalDateTime createDate = rs.getObject(3, LocalDateTime.class);
                Role role = findRoleByUserId(userId).get();
                BaseUser user;
                if (role == ADMIN) {
                    user = new Admin();
                } else {
                    user = new Client();
                }
                user.setId(userId);
                user.setName(name);
                user.setCreateDate(createDate);
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public boolean deleteById(int userId) throws SQLException {
        var DELETE_TICKETS_BY_USER_ID_SQL = "DELETE FROM ticket WHERE user_id = ?";
        var DELETE_ROLE_BY_USER_ID_SQL = "DELETE FROM user_role WHERE user_id = ?";
        var DELETE_USER_BY_ID_SQL = "DELETE FROM users WHERE id = ?";
        boolean rowDeleted;

        try (PreparedStatement deleteTickets = connection.prepareStatement(DELETE_TICKETS_BY_USER_ID_SQL);
             PreparedStatement deleteUser = connection.prepareStatement(DELETE_USER_BY_ID_SQL);
             PreparedStatement deleteRole = connection.prepareStatement(DELETE_ROLE_BY_USER_ID_SQL)
        ) {
            connection.setAutoCommit(false);

            deleteTickets.setInt(1, userId);
            deleteTickets.executeUpdate();

            deleteRole.setInt(1, userId);
            deleteRole.executeUpdate();

            deleteUser.setInt(1, userId);
            rowDeleted = deleteUser.executeUpdate() > 0;

            connection.commit();
        }
        return rowDeleted;
    }

    public int findIdByUserNameAndCreationDate(String name, Timestamp date) throws SQLException {
        var FIND_ID_BY_NAME_AND_DATE_SQL = "SELECT id FROM users WHERE user_name = ? AND creation_date = ?";
        int userId = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ID_BY_NAME_AND_DATE_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setTimestamp(2, date);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
              userId = rs.getInt("id");}
        }
        return userId;
    }

    public void saveRole(int userId) throws SQLException {
        var SAVE_ROLE_SQL = "INSERT INTO user_role (user_id, role) values (?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SAVE_ROLE_SQL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setObject(2, ADMIN, java.sql.Types.OTHER);
            preparedStatement.executeUpdate();
        }
    }
}
