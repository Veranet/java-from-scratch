package halatsiankova.javafromscratch;

import halatsiankova.javafromscratch.connection.ConnectionDataBasePSQL;
import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Admin;
import halatsiankova.javafromscratch.model.Client;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.repository.TicketRepositoryImpl;
import halatsiankova.javafromscratch.repository.UserRepositoryImpl;
import halatsiankova.javafromscratch.service.TicketService;
import halatsiankova.javafromscratch.service.UserService;
import halatsiankova.javafromscratch.util.HexIdGeneratorUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static halatsiankova.javafromscratch.enumerated.StadiumSector.A;
import static java.util.logging.Logger.getLogger;

public class App {

    private static final Logger LOGGER = getLogger(App.class.getSimpleName());

    public static void main(String[] args) throws SQLException {
        // Lesson - 1
        Ticket ticket = new Ticket();
        Ticket ticketWithAllFields = new Ticket("12ae", "MAIN", 222, 1717499006,
                true, A, 15.86, BigDecimal.valueOf(100.58));
        Ticket ticketWithLimitedFields = new Ticket("SMALL", 135, 1717499006);

        // Lesson - 2
        List<Ticket> ticketsToLecture3 = createTenTickets();

        // Lesson - 4
        // task 4.1
        Ticket ticket1 = ticketsToLecture3.get(1);
        ticket.setId(15);
        LOGGER.log(Level.INFO, ticket.getId().toString());
        // task 4.2
        LOGGER.log(Level.INFO,ticket.print());
        // task 4.4
        LOGGER.log(Level.INFO, ticket1.share("+12 123-456-7890"));
        LOGGER.log(Level.INFO, ticket1.share("+12 123-456-7890", "email@dom.com"));
        // task 4.5
        BaseUser user = new Client();
        user.setId(1);
        Admin admin = new Admin();
        admin.setId(2);
        user.printRole();
        admin.printRole();

        // ApplicationConfig
        ConnectionDataBasePSQL connectionDataBasePSQL = new ConnectionDataBasePSQL();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(connectionDataBasePSQL);
        TicketRepositoryImpl ticketRepository = new TicketRepositoryImpl(connectionDataBasePSQL);
        UserService userService = new UserService(userRepository);
        TicketService ticketService = new TicketService(ticketRepository);
    }

    private static List<Ticket> createTenTickets() {
        var concertHall = "MainHall";
        var eventCode = 111;
        var eventTime = Instant.parse("2024-10-10T10:00:00.100000Z")
                .getEpochSecond();
        var allowedBackpackWeight = 15.00;
        var price = BigDecimal.valueOf(159.45);
        return IntStream.range(0, 10)
                .mapToObj(i ->
                        new Ticket(HexIdGeneratorUtil.generateTicketId(i), concertHall, eventCode, eventTime, false,
                                StadiumSector.values()[i % 3], allowedBackpackWeight, price))
                .toList();
    }
}
