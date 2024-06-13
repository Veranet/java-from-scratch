package halatsiankova.javafromscratch;

import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.provider.DateTimeProvider;
import halatsiankova.javafromscratch.repository.AdministrationRepository;
import halatsiankova.javafromscratch.repository.TicketRepository;
import halatsiankova.javafromscratch.repository.TicketRepositoryImpl;
import halatsiankova.javafromscratch.repository.UserRepository;
import halatsiankova.javafromscratch.service.AdministrationService;
import halatsiankova.javafromscratch.service.TicketService;
import halatsiankova.javafromscratch.service.UserService;
import halatsiankova.javafromscratch.validator.NullValidator;
import halatsiankova.javafromscratch.model.Admin;
import halatsiankova.javafromscratch.model.Client;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.model.User;
import halatsiankova.javafromscratch.util.HexIdGeneratorUtil;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static halatsiankova.javafromscratch.enumerated.StadiumSector.A;

public class App {

    private static final System.Logger LOGGER = System.getLogger(App.class.getSimpleName());

    public static void main(String[] args) {
        // Lesson - 1
        TicketService service = new TicketService();
        service.create();
        service.create("12ae", "MAIN", 222, 1717499006,
                true, A, 15.86, BigDecimal.valueOf(100.58));
        service.create("SMALL", 135, 1717499006);

        // Lesson - 2
        List<Ticket> ticketsToLecture3 = createTenTickets(service);
        service.saveAll(ticketsToLecture3);

        // Lesson - 3
        Arrays.stream(StadiumSector.values())
                .map(service::getTicketsByStadiumSector)
                .flatMap(List::stream)
                .forEach(System.out::println);

        // Lesson - 4
        // task 4.1
        Ticket ticket = ticketsToLecture3.get(1);
        ticket.setId(15);
        LOGGER.log(System.Logger.Level.INFO, ticket.getId());
        // task 4.2
        ticket.print();
        // task 4.4
        service.shareByPhone("+12 123-456-7890", ticket);
        service.shareByPhoneAndEmail("+12 123-456-7890", "email@dom.com", ticket);
        // task 4.5
        AdministrationRepository administrationRepository = new AdministrationRepository();
        UserRepository userRepository = new UserRepository();
        TicketRepository ticketRepository = new TicketRepositoryImpl();
        TicketService ticketService = new TicketService(ticketRepository);
        UserService userService = new UserService(ticketRepository, administrationRepository);
        AdministrationService administrationService = new AdministrationService(ticketRepository, new DateTimeProvider());
        User user = new Client();
        user.setId(1);
        Admin admin = new Admin();
        admin.setId(2);
        user.printRole();
        admin.printRole();
        ticketService.saveAll(ticketsToLecture3);
        userRepository.save(user);
        userService.buyTicket(user, ticketsToLecture3.getFirst());
        Ticket usersTicket = userService.getAllTickets(user).getFirst();
        LOGGER.log(System.Logger.Level.INFO, usersTicket);
        administrationService.checkTicket(usersTicket, admin);

        Ticket checkField = new Ticket();
        NullValidator.checkNullFields(checkField);
    }

    private static List<Ticket> createTenTickets(TicketService service) {
        var concertHall = "MainHall";
        var eventCode = 111;
        var eventTime = Instant.parse("2024-10-10T10:00:00.100000Z")
                .getEpochSecond();
        var allowedBackpackWeight = 15.00;
        var price = BigDecimal.valueOf(159.45);
        return IntStream.range(0, 10)
                .mapToObj(i ->
                        service.create(HexIdGeneratorUtil.generateTicketId(i), concertHall, eventCode, eventTime, false,
                                StadiumSector.values()[i % 3], allowedBackpackWeight, price))
                .toList();
    }
}
