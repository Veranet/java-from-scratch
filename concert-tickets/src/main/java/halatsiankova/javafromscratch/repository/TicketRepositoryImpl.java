package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.Ticket;

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

public class TicketRepositoryImpl implements TicketRepository {
    Connection connection;

    public TicketRepositoryImpl() throws SQLException {
        this.connection =
                DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/my_ticket_service_db", "myuser", "mypassword"
                );
    }

    @Override
    public void save(Ticket ticket) throws SQLException {
        var INSERT_TICKET_SQL =
                "INSERT INTO ticket (user_id, ticket_type, stadium_sector, creation_date) values (?, ?, ?, ?)";
        try (PreparedStatement pr = connection.prepareStatement(INSERT_TICKET_SQL)) {
            pr.setInt(1, ticket.getUserId());
            pr.setObject(2, ticket.getType(), java.sql.Types.OTHER);
            pr.setObject(3, ticket.getStadiumSector(), java.sql.Types.OTHER);
            pr.setTimestamp(4, Timestamp.valueOf(ticket.getCreatedDateTime()));
            pr.executeUpdate();
        }
    }

    @Override
    public Optional<Ticket> findById(Integer id) throws SQLException {
        var FIND_BY_ID_SQL =
                "SELECT * FROM ticket WHERE id = ?";
        var ticket = new Ticket();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ticket.setId(resultSet.getInt("id"));
                    ticket.setUserId(resultSet.getInt("user_id"));
                    ticket.setType(TicketType.valueOf(resultSet.getString("ticket_type")));
                    ticket.setStadiumSector(StadiumSector.valueOf(resultSet.getString("stadium_sector")));
                    ticket.setCreatedDateTime(resultSet.getObject("creation_date", LocalDateTime.class));
                    return Optional.of(ticket);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Ticket> findTicketByStadiumSector(StadiumSector stadiumSector) throws SQLException {
        var FIND_BY_ID_SQL =
                "SELECT * FROM ticket WHERE stadium_sector = ? ORDER BY id";
        List<Ticket> tickets = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, stadiumSector, java.sql.Types.OTHER);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setId(rs.getInt("id"));
                    ticket.setUserId(rs.getInt("user_id"));
                    ticket.setType(TicketType.valueOf(rs.getString("ticket_type")));
                    ticket.setStadiumSector(StadiumSector.valueOf(rs.getString("stadium_sector")));
                    ticket.setCreatedDateTime(rs.getObject("creation_date", LocalDateTime.class));
                    tickets.add(ticket);
                }
            }
        }
        return tickets;
    }

    public List<Ticket> findAllByUserId(int userId) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        var SELECT_ALL_TICKETS_SQL = "SELECT * FROM ticket WHERE user_id = ? ORDER BY id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TICKETS_SQL)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user_id = resultSet.getInt("user_id");
                TicketType ticketType = TicketType.valueOf(resultSet.getString("ticket_type"));
                StadiumSector stadiumSector = StadiumSector.valueOf(resultSet.getString("stadium_sector"));
                LocalDateTime creationDate = resultSet.getObject("creation_date", LocalDateTime.class);
                Ticket ticket = new Ticket(id, user_id, ticketType, stadiumSector, creationDate);
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    @Override
    public void update(TicketType ticketType, int ticketId) throws SQLException {
        var UPDATE_TICKET_SQL = "UPDATE ticket SET ticket_type = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TICKET_SQL)) {
            preparedStatement.setObject(1, ticketType, java.sql.Types.OTHER);
            preparedStatement.setInt(2, ticketId);
            preparedStatement.executeUpdate();
        }
    }
}
