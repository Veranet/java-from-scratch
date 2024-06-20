package halatsiankova.javafromscratch.busTicket.repository;

import halatsiankova.javafromscratch.busTicket.enumerated.TicketType;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusTicketRepositoryTest {

    @Nested
    public class Save {
        @Test
        void shouldSaveBusTicketToRepository() {
            BusTicketRepository repository = new BusTicketRepository();
            UUID ticketId = UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab");
            BusTicket busTicket =
                    new BusTicket(ticketId,
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(100.0));
            repository.save(busTicket);
            Optional<BusTicket> expected = Optional.of(busTicket);
            assertEquals(expected, repository.findById(ticketId));
        }
    }

    @Nested
    public class SaveAll {
        @Test
        void shouldSaveAllBusTicketsToRepository() {
            BusTicketRepository repository = new BusTicketRepository();
            List<BusTicket> busTickets = createListBusTickets();
            repository.saveAll(busTickets);
            Collection<BusTicket> expected = busTickets;
            assertEquals(expected, repository.findAll());
        }
    }

    @Nested
    public class FindById {
        @Test
        void shouldReturnsBusTicketsByIdWhenIsExist() {
            BusTicketRepository repository = new BusTicketRepository();
            repository.saveAll(createListBusTickets());
            UUID ticketId = UUID.fromString("3a333333-6795-4b38-b798-a28abd254eab");
            Optional<BusTicket> expected = Optional.of(new BusTicket(ticketId,
                    "STD",
                    TicketType.DAY,
                    "2025-01-01",
                    BigDecimal.valueOf(100.0)));
            assertEquals(expected, repository.findById(ticketId));
        }

        @Test
        void shouldOptionalIsEmptyWhenBusTicketDoesNotExist() {
            BusTicketRepository repository = new BusTicketRepository();
            repository.saveAll(createListBusTickets());
            UUID ticketId = UUID.fromString("4a44444-6795-4b38-b798-a28abd254eab");
            assertEquals(Optional.empty(), repository.findById(ticketId));
        }
    }

    @Nested
    public class DeleteById {
        @Test
        void ShouldDeleteBusTicketById() {
            BusTicketRepository repository = new BusTicketRepository();
            repository.saveAll(createListBusTickets());
            List<BusTicket> expected = List.of(
                    new BusTicket(UUID.fromString("1a111111-6795-4b38-b798-a28abd254eab"),
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(1.0)),
                    new BusTicket(UUID.fromString("3a333333-6795-4b38-b798-a28abd254eab"),
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(100.0)),
            new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.YEAR,
                    "2025-01-01",
                    BigDecimal.valueOf(14.0)));
            UUID ticketIdForDelete = UUID.fromString("2a222222-6795-4b38-b798-a28abd254eab");
            repository.deleteById(ticketIdForDelete);
            assertEquals(expected, repository.findAll());
        }
    }

    @Nested
    public class FindAllByTicketTypeAndPriceFromTo {
        @Test
        void shouldReturnsAllBusTicketsByAccordingToTheSpecifiedParametersWhenTicketsExist() {
            BusTicketRepository repository = new BusTicketRepository();
            repository.saveAll(createListBusTickets());
            BigDecimal priceFrom = BigDecimal.valueOf(1.0);
            BigDecimal priceTo = BigDecimal.valueOf(50.0);
            List<BusTicket> actual = repository.findAllByTicketTypeAndPriceFromTo(TicketType.DAY, priceFrom, priceTo);
            List<BusTicket> expected = List.of(
                    new BusTicket(UUID.fromString("1a111111-6795-4b38-b798-a28abd254eab"),
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(1.0)),
                    new BusTicket(UUID.fromString("2a222222-6795-4b38-b798-a28abd254eab"),
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(10.0)));
            assertEquals(expected, actual);
        }
    }

    private List<BusTicket> createListBusTickets() {
        List<BusTicket> busTickets = new ArrayList<>();
        BusTicket firstBusTicket =
                new BusTicket(UUID.fromString("1a111111-6795-4b38-b798-a28abd254eab"),
                        "STD",
                        TicketType.DAY,
                        "2025-01-01",
                        BigDecimal.valueOf(1.0));
        BusTicket secondBusTicket =
                new BusTicket(UUID.fromString("2a222222-6795-4b38-b798-a28abd254eab"),
                        "STD",
                        TicketType.DAY,
                        "2025-01-01",
                        BigDecimal.valueOf(10.0));
        BusTicket thirdBusTicket =
                new BusTicket(UUID.fromString("3a333333-6795-4b38-b798-a28abd254eab"),
                        "STD",
                        TicketType.DAY,
                        "2025-01-01",
                        BigDecimal.valueOf(100.0));
        BusTicket fourthBusTicket = new BusTicket(UUID.fromString("5a555555-6795-4b38-b798-a28abd254eab"),
                "STD",
                TicketType.YEAR,
                "2025-01-01",
                BigDecimal.valueOf(14.0));
        busTickets.add(firstBusTicket);
        busTickets.add(secondBusTicket);
        busTickets.add(thirdBusTicket);
        busTickets.add(fourthBusTicket);
        return busTickets;
    }
}
