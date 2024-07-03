package halatsiankova.javafromscratch.busticket.repository;

import halatsiankova.javafromscratch.busticket.enumerated.ErrorType;
import halatsiankova.javafromscratch.busticket.model.ErrorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationRepositoryTest {
    private ValidationRepository repository;

    @BeforeEach
    void init() {
        repository = new ValidationRepository();
        repository.saveAll(List.of(
                new ErrorEntity(UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.TICKET_TYPE),
                new ErrorEntity(UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.START_DATE)));
    }

    @Nested
    public class SaveAll {
        @Test
        void shouldSaveAllErrorEntityInStore() {
            ValidationRepository validationRepository = new ValidationRepository();
            validationRepository.saveAll(List.of(
                    new ErrorEntity(UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.TICKET_TYPE),
                    new ErrorEntity(UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.START_DATE)));

            var expected = List.of(
                    new ErrorEntity(
                            UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.TICKET_TYPE),
                    new ErrorEntity(
                            UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"), ErrorType.START_DATE));

            assertEquals(expected, repository.findAllErrors());
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
            var validationRepository = new ValidationRepository();

            assertEquals(0, validationRepository.countByTicketId());
        }
    }

    @Nested
    public class FindAllErrors {
        @Test
        void shouldReturnsListAllErrorsEntries() {
            var expected = List.of(
                    new ErrorEntity(UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"),
                            ErrorType.TICKET_TYPE),
                    new ErrorEntity(UUID.fromString("20b90321-6795-4b38-b798-a28abd254eab"),
                            ErrorType.START_DATE));
            assertEquals(expected, repository.findAllErrors());
        }

        @Test
        void shouldReturnsEmptyListWhenMapIsEmpty() {
            var validationRepository = new ValidationRepository();

            assertEquals(List.of(), validationRepository.findAllErrors());
        }
    }

    @Nested
    public class DeleteAll {
        @Test
        void shouldClearMapWhenMethodWasCalled() {
            repository.deleteAll();
            assertEquals(List.of(), repository.findAllErrors());
        }
    }
}
