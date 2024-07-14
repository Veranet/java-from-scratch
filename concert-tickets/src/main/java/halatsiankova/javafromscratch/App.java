package halatsiankova.javafromscratch;

import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Admin;
import halatsiankova.javafromscratch.model.Client;
import halatsiankova.javafromscratch.model.Ticket;
//import halatsiankova.javafromscratch.service.TicketService;
//import halatsiankova.javafromscratch.service.UserService;
import halatsiankova.javafromscratch.repository.TicketRepository;
import halatsiankova.javafromscratch.repository.UserRepository;
import halatsiankova.javafromscratch.util.HexIdGeneratorUtil;

import halatsiankova.javafromscratch.util.DataLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static halatsiankova.javafromscratch.enumerated.StadiumSector.A;
import static java.util.logging.Logger.getLogger;

@SpringBootApplication
public class App {

    private static final Logger LOGGER = getLogger(App.class.getSimpleName());

    private final static String PATH_TICKETS = "classpath:tickets.json";
    private final static String PATH_USERS = "classpath:users.json";

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(App.class, args);
        // Lesson - 12

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        // Lesson - 11
        DataLoader dataLoader = applicationContext.getBean(DataLoader.class);

        var initialUsers = dataLoader.load(PATH_USERS, BaseUser.class);
        initialUsers.forEach(user -> LOGGER.log(Level.INFO, user.toString()));
        var userRepository = applicationContext.getBean(UserRepository.class);
        var savedUsers = userRepository.saveAll(initialUsers);

        var initialTickets = dataLoader.load(PATH_TICKETS, Ticket.class);
        initialTickets.forEach(ticket -> {
            ticket.setUserId(savedUsers.getFirst().getId());
            LOGGER.log(Level.INFO, ticket.toString());
        });
        var ticketRepository = applicationContext.getBean(TicketRepository.class);
        ticketRepository.saveAll(initialTickets);

        // Lesson - 1
        Ticket ticket = new Ticket();
        Ticket ticketWithAllFields = new Ticket("12ae", "MAIN", 222, 1717499006,
                true, A, 15.86, BigDecimal.valueOf(100.58), 1);
        Ticket ticketWithLimitedFields = new Ticket("SMALL", 135, 1717499006);

        // Lesson - 2
        List<Ticket> ticketsToLecture3 = createTenTickets();

        // Lesson - 4
        // task 4.1
        Ticket ticket1 = ticketsToLecture3.get(1);
        ticket.setId(15);
        LOGGER.log(Level.INFO, ticket.getId().toString());
        // task 4.2
        LOGGER.log(Level.INFO, ticket.print());
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
                                StadiumSector.values()[i % 3], allowedBackpackWeight, price, 1))
                .toList();
    }
}
