package halatsiankova.javafromscratch.busTicket.repository;

import halatsiankova.javafromscratch.busTicket.enumerated.ErrorType;
import halatsiankova.javafromscratch.busTicket.model.ErrorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ValidationRepositoryTest {

    private ValidationRepository repository;

    @BeforeEach
    void init() {
        repository = new ValidationRepository();
        ErrorEntity errorEntity =
                new ErrorEntity(UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.TICKET_TYPE);
        ErrorEntity secondErrorEntity =
                new ErrorEntity(UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.START_DATE);
        repository.save(errorEntity);
        repository.save(secondErrorEntity);
    }

    @Nested
    public class Save {
        @Test
        void shouldSaveErrorEntityInStore() {
            List<ErrorEntity> actual = repository.findAllErrors();
            List<ErrorEntity> expected = List.of(new ErrorEntity(
                    UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.TICKET_TYPE),
                    new ErrorEntity(
                            UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.START_DATE));
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class CountByTicketId {
        @Test
        void shouldReturns1WhenMapConsistOfTwoErrorEntriesBySameTicketIds() {
           assertEquals(1, repository.countByTicketId());
        }

        @Test
        void shouldReturnsZeroWhenMapIsEmpty() {
            ValidationRepository validationRepository = new ValidationRepository();
            assertEquals(0, validationRepository.countByTicketId());
        }
    }

    @Nested
    public class FindAllErrors {
        @Test
        void shouldReturnsListAllErrorsEntries() {
            List<ErrorEntity> expected = new ArrayList<>();
            ErrorEntity first = new ErrorEntity(UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.TICKET_TYPE);
            ErrorEntity second = new ErrorEntity(UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.START_DATE);
            expected.add(first);
            expected.add(second);
            assertEquals(expected, repository.findAllErrors());
        }

        @Test
        void shouldReturnsEmptyListWhenMapIsEmpty() {
            ValidationRepository validationRepository = new ValidationRepository();
            assertEquals(List.of(), validationRepository.findAllErrors());
        }
    }
}