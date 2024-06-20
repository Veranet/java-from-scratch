package halatsiankova.javafromscratch.busTicket.service;

import halatsiankova.javafromscratch.busTicket.enumerated.TicketType;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;
import halatsiankova.javafromscratch.busTicket.repository.BusTicketRepository;
import halatsiankova.javafromscratch.busTicket.util.GeneratorUUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private BusTicketRepository repository;
    @Mock
    private GeneratorUUID generatorUUID;

    @InjectMocks
    private TicketService ticketService;
    @Nested
    public class Save {
        @Test
        void shouldSaveBusTicket() {
            UUID ticketId = UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab");
            BusTicket busTicket =
                    new BusTicket("STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(100.0));
            BusTicket ticketForSave = new BusTicket(ticketId,
                    "STD",
                    TicketType.DAY,
                    "2025-01-01",
                    BigDecimal.valueOf(100.0));
            when(generatorUUID.generateId()).thenReturn(ticketId);
            ticketService.save(busTicket);
            verify(repository).save(ticketForSave);
        }
    }

    @Nested
    public class SaveAll {
        @Test
        void shouldSaveAllBusTickets() {
            Collection<BusTicket> tickets = new ArrayList<>();
            BusTicket firstBusTicket =
                    new BusTicket("STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(100.0));
            BusTicket secondBusTicket =
                    new BusTicket("STD",
                            TicketType.WEEK,
                            "2028-01-01",
                            BigDecimal.valueOf(10.0));
            UUID firstTicketId = UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab");
            UUID secondTicketId = UUID.fromString("00b00000-0000-4b38-b798-a28abd254eab");
            tickets.add(firstBusTicket);
            tickets.add(secondBusTicket);
            when(generatorUUID.generateId()).thenReturn(firstTicketId);
            when(generatorUUID.generateId()).thenReturn(secondTicketId);
            Collection<BusTicket> ticketsForSave = new ArrayList<>();
            BusTicket firstBusTicketForSave =
                    new BusTicket(firstTicketId,
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(100.0));
            BusTicket secondBusTicketForSave =
                    new BusTicket(secondTicketId,
                            "STD",
                            TicketType.WEEK,
                            "2028-01-01",
                            BigDecimal.valueOf(10.0));
            ticketsForSave.add(firstBusTicketForSave);
            ticketsForSave.add(secondBusTicketForSave);
            Collection<BusTicket> expected = new ArrayList<>();
            expected.add(firstBusTicket);
            expected.add(secondBusTicket);
            Collection<BusTicket> actual = ticketService.saveAll(tickets);
            assertEquals(expected, actual);

        }
    }

    @Nested
    public class GetById {
        @Test
        void shouldReturnsBusTicketByIdWhenBusTicketExist() {
            UUID ticketId = UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab");
            BusTicket ticket =
                    new BusTicket(ticketId,
                            "STD",
                            TicketType.WEEK,
                            "2028-01-01",
                            BigDecimal.valueOf(10.0));
            when(repository.findById(ticketId)).thenReturn(Optional.of(ticket));
            BusTicket expected = new BusTicket(ticketId,
                    "STD",
                    TicketType.WEEK,
                    "2028-01-01",
                    BigDecimal.valueOf(10.0));
            assertEquals(expected, ticketService.getById(ticketId));
        }

        @Test
        void shouldThrowsIllegalArgumentExceptionWhenBusTicketDoesNotExist() {
            UUID ticketId = UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab");
            when(repository.findById(ticketId)).thenReturn(Optional.empty());
            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> ticketService.getById(ticketId));
            assertEquals("BusTicket with id=11b11111-1111-4b38-b798-a28abd254eab doesn't exist.",
                    exception.getMessage());
        }
    }

    @Nested
    public class DeleteByTicketId {
        @Test
        void shouldDeleteByTicketId() {
            UUID ticketId = UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab");
            ticketService.deleteByTicketId(ticketId);
            verify(repository).deleteById(ticketId);
        }
    }

    @Nested
    public class GetAllByTicketIdAndPriceFromTo {
        @Test
        void shouldReturnsListBusTicketsByAccordingToTheSpecifiedParametersWhenBusTicketsExist() {
            UUID firstTicketId = UUID.fromString("11b11111-1111-4b38-b798-a28abd254eab");
            UUID secondTicketId = UUID.fromString("00b00000-0000-4b38-b798-a28abd254eab");
            List<BusTicket> expected = List.of(
                    new BusTicket(firstTicketId,
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(100.0)),
                    new BusTicket(secondTicketId,
                            "STD",
                            TicketType.WEEK,
                            "2028-01-01",
                            BigDecimal.valueOf(10.0))
            );
            List<BusTicket> tickets = List.of(
                    new BusTicket(firstTicketId,
                            "STD",
                            TicketType.DAY,
                            "2025-01-01",
                            BigDecimal.valueOf(100.0)),
                    new BusTicket(secondTicketId,
                            "STD",
                            TicketType.WEEK,
                            "2028-01-01",
                            BigDecimal.valueOf(10.0))
            );
            BigDecimal priceFrom = BigDecimal.valueOf(10.0);
            BigDecimal priceTo = BigDecimal.valueOf(100.0);
            when(repository.findAllByTicketTypeAndPriceFromTo(TicketType.WEEK, priceFrom, priceTo)).thenReturn(tickets);
            List<BusTicket> actual = ticketService.getAllByTicketIdAndPriceFromTo(TicketType.WEEK, priceFrom, priceTo);
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnsEmptyListBusTicketsByAccordingToTheSpecifiedParametersWhenBusTicketsDoNotExist() {
            BigDecimal priceFrom = BigDecimal.valueOf(10.0);
            BigDecimal priceTo = BigDecimal.valueOf(100.0);
            when(repository.findAllByTicketTypeAndPriceFromTo(TicketType.YEAR, priceFrom, priceTo)).thenReturn(List.of());
            List<BusTicket> actual = ticketService.getAllByTicketIdAndPriceFromTo(TicketType.YEAR, priceFrom, priceTo);
            assertEquals(List.of(), actual);
        }

        @Test
        void shouldThrowsWhenPriceFromGreaterThanPriceTo() {
            BigDecimal priceFrom = BigDecimal.valueOf(10000.0);
            BigDecimal priceTo = BigDecimal.valueOf(10.0);

           var exception = assertThrows(IllegalArgumentException.class,
                    () -> ticketService.getAllByTicketIdAndPriceFromTo(TicketType.YEAR, priceFrom, priceTo));
            assertEquals("The price from must be less than the price to.", exception.getMessage());
            verify(repository, never()).findAllByTicketTypeAndPriceFromTo(TicketType.YEAR, priceFrom, priceTo);
        }
    }
}
