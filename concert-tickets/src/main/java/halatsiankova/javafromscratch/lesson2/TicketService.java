package halatsiankova.javafromscratch.lesson2;

import halatsiankova.javafromscratch.lesson2.model.Ticket;
import halatsiankova.javafromscratch.lesson2.repository.Repository;
import halatsiankova.javafromscratch.lesson2.repository.TicketRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.IntStream;

public class TicketService {
    private final Repository<Ticket, String> repository;

    public TicketService() {
        repository = new TicketRepository();
    }

    private List<Ticket> createTenTickets() {
        var concertHall = "MainHall";
        var eventCode = 111;
        var eventTime = Instant.parse("2024-10-10T10:00:00.100000Z")
                .getEpochSecond();
        var allowedBackpackWeight = 15.00;
        var price = BigDecimal.valueOf(159.45);
        return IntStream.range(0, 10)
                .mapToObj(i ->
                        create(generateTicketId(i), concertHall, eventCode, eventTime, false,
                                Ticket.StadiumSector.values()[i % 3], allowedBackpackWeight, price))
                .toList();
    }

    public List<Ticket> getByStadiumSector(Ticket.StadiumSector sector) {
        return repository.findByStadiumSector(sector);
    }

    public Ticket create(String ticketId, String concertHall, int eventCode, long eventTime, boolean isPromo,
                         Ticket.StadiumSector stadiumSector, double allowedBackpackWeight, BigDecimal price) {
        var createdDateTime = createTimeStamp();
        return new Ticket(ticketId, concertHall, eventCode, eventTime, isPromo,
                stadiumSector, allowedBackpackWeight, price, createdDateTime);
    }

    public OffsetDateTime createTimeStamp() {
        return OffsetDateTime.now();
    }

    public void saveAll(List<Ticket> ticketsForSave) {
        repository.saveAll(ticketsForSave);
    }

    private String generateTicketId(int order) {
        return Integer.toHexString(order);
    }

    public static void main(String[] args) {
        TicketService ticketService = new TicketService();
        List<Ticket> ticketsToLecture3 = ticketService.createTenTickets();
        ticketService.saveAll(ticketsToLecture3);

        // Retrieve tickets by StadiumSector and print only sector A tickets
        List<Ticket> sectorATickets = ticketService.getByStadiumSector(Ticket.StadiumSector.A);
        System.out.println("Tickets in sector A:");
        sectorATickets.forEach(System.out::println);
    }
}
