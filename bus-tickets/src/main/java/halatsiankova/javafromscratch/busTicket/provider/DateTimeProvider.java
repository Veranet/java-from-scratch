package halatsiankova.javafromscratch.busTicket.provider;

import java.time.LocalDate;

/**
 * Class to get current date.
 */
public class DateTimeProvider {
    public LocalDate provideDateTime() {
        return LocalDate.now();
    }
}
