package halatsiankova.javafromscratch.lesson2;

import halatsiankova.javafromscratch.lesson2.model.Ticket;
import halatsiankova.javafromscratch.lesson2.service.TicketService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static halatsiankova.javafromscratch.lesson2.model.Ticket.StadiumSector.A;
import static halatsiankova.javafromscratch.lesson2.util.TicketsGeneratorUtil.generateTicketId;

public class App {

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
        Arrays.stream(Ticket.StadiumSector.values())
                .map(service::getTicketsByStadiumSector)
                .flatMap(List::stream)
                .forEach (System.out::println);
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
                         service.create(generateTicketId(i),concertHall, eventCode, eventTime, false,
                                Ticket.StadiumSector.values()[i % 3], allowedBackpackWeight, price))
                .toList();
    }
}
