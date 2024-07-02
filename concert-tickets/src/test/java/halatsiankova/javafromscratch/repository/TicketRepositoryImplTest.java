package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.Ticket;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketRepositoryImplTest {

    private static TicketRepositoryImpl ticketRepository;
    private static Connection connection;

    @BeforeAll
    public static void setup() throws SQLException {
        ticketRepository = new TicketRepositoryImpl();
        connection =
                DriverManager.
                        getConnection("jdbc:postgresql://localhost:5432/my_ticket_service_db", "myuser", "mypassword");
    }

    @BeforeEach
    public void init() throws IOException, InterruptedException {
        String sqlQuery = getResource();
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Thread.sleep(3000);
    }

    @AfterEach
    public void clear() throws InterruptedException {

        String sqlQuery;
        try {
            sqlQuery = new String(Files.readAllBytes(Paths.get("src/main/resources/clear.sql")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Thread.sleep(5000);
    }
    @Test
    void shouldSaveTicket() throws SQLException {
        LocalDateTime createDate = LocalDateTime.of(2024, 7, 1, 0, 0 ,0);
        Ticket ticket = new Ticket(null, 1, TicketType.DAY, StadiumSector.C, createDate);

        ticketRepository.save(ticket);

        Optional<Ticket> expected = Optional.of(new Ticket(5, 3, TicketType.DAY, StadiumSector.C, createDate));

        assertEquals(expected, ticketRepository.findById(5));
    }

    @Test
    void shouldReturnOptionalTicketByIdWhenTicketExist() throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);

        Optional<Ticket> expected =
                Optional.of(new Ticket(1, 1, TicketType.DAY, StadiumSector.A, localDateTime));

        assertEquals(expected, ticketRepository.findById(1));
    }

    @Test
    void shouldReturnOptionalEmptyWhenTicketDoesNotExist() throws SQLException {
        assertEquals(Optional.empty(), ticketRepository.findById(100));
    }

    @Test
    void shouldReturnListTicketsByStadiumSector() throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);

        List<Ticket> expected = List.of(
                new Ticket(1, 1, TicketType.DAY, StadiumSector.A, localDateTime),
                new Ticket(2, 3, TicketType.DAY, StadiumSector.A, localDateTime),
                new Ticket(3, 2, TicketType.WEEK, StadiumSector.A, localDateTime));

        assertEquals(expected, ticketRepository.findTicketByStadiumSector(StadiumSector.A));
    }

    @Test
    void shouldReturnEmptyListTicketsByStadiumSectorWhenTicketsDidNotExist() throws SQLException {
        assertEquals(List.of(), ticketRepository.findTicketByStadiumSector(StadiumSector.B));
    }

    @Test
    void shouldReturnListTicketsByUserId() throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);

        List<Ticket> expected = List.of(
                new Ticket(1, 1, TicketType.DAY, StadiumSector.A, localDateTime),
                new Ticket(4, 1, TicketType.MONTH, StadiumSector.C, localDateTime));

        assertEquals(expected, ticketRepository.findAllByUserId(1));
    }

    @Test
    void shouldReturnEmptyListWhenTicketsByUserIdDidNotExist() throws SQLException {
        assertEquals(List.of(), ticketRepository.findAllByUserId(5));
    }

    @Test
    void shouldUpdateTicket() throws SQLException {
        ticketRepository.update(TicketType.YEAR, 1);

        Optional<Ticket> actual = ticketRepository.findById(1);

        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 30, 10, 0, 0);
        Optional<Ticket> expected = Optional.of(new Ticket(1, 1, TicketType.YEAR, StadiumSector.A,localDateTime));
        assertEquals(expected, actual);
    }

    private static String getResource() throws IOException {
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/resources/init.sql")));
        } catch (IOException exception) {
            System.out.println("\nCannot read file: " + exception.getMessage());
            throw exception;
        }
    }
}
