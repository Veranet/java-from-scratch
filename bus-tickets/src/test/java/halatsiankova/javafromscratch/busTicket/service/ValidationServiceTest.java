package halatsiankova.javafromscratch.busTicket.service;

import halatsiankova.javafromscratch.busTicket.enumerated.ErrorType;
import halatsiankova.javafromscratch.busTicket.enumerated.TicketType;
import halatsiankova.javafromscratch.busTicket.model.BusTicket;
import halatsiankova.javafromscratch.busTicket.model.ErrorEntity;
import halatsiankova.javafromscratch.busTicket.repository.ValidationRepository;
import halatsiankova.javafromscratch.busTicket.validator.BusTicketValidator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {
    @Mock
    private ValidationRepository validationRepository;
    @Mock
    private BusTicketValidator validator;

    @InjectMocks
    private ValidationService validationService;

    @Nested
    public class Validate {
        @Test
        void shouldSave3ErrorEntityToRepositoryWhen3ValidationsHaveFailed() {
            var firstTicket = new BusTicket(UUID.fromString("1a111111-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.DAY,
                    "2023-01-01",
                    BigDecimal.valueOf(1.0));
            var secondTicket = new BusTicket(UUID.fromString("3a333333-6795-4b38-b798-a28abd254eab"),
                    "STD",
                    TicketType.MONTH,
                    "2023-01-01",
                    BigDecimal.valueOf(100.0));
            var tickets = List.of(firstTicket, secondTicket);
            doNothing().when(validationRepository).deleteAll();
            when(validator.validateType(firstTicket))
                    .thenReturn(Optional.of(new ErrorEntity(firstTicket.getId(), ErrorType.TICKET_TYPE)));
            when(validator.validateType(secondTicket)).thenReturn(Optional.empty());

            when(validator.validateStartDate(firstTicket))
                    .thenReturn(Optional.of(new ErrorEntity(firstTicket.getId(), ErrorType.START_DATE)));
            when(validator.validateStartDate(secondTicket)).thenReturn(Optional.empty());

            when(validator.validatePrice(firstTicket)).thenReturn(Optional.empty());
            when(validator.validatePrice(secondTicket))
                    .thenReturn(Optional.of(new ErrorEntity(firstTicket.getId(), ErrorType.PRICE)));

            doNothing().when(validationRepository).saveAll(anyList());
            validationService.validate(tickets);

            verify(validationRepository).saveAll(List.of(
                    new ErrorEntity(UUID.fromString("1a111111-6795-4b38-b798-a28abd254eab"), ErrorType.TICKET_TYPE),
                    new ErrorEntity(UUID.fromString("1a111111-6795-4b38-b798-a28abd254eab"), ErrorType.START_DATE),
                    new ErrorEntity(UUID.fromString("1a111111-6795-4b38-b798-a28abd254eab"), ErrorType.PRICE)));
        }
    }
}
