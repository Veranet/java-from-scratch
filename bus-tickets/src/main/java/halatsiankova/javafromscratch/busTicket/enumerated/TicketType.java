package halatsiankova.javafromscratch.busTicket.enumerated;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum TicketType {
    DAY,
    WEEK,
    MONTH,
    YEAR,
    @JsonEnumDefaultValue
    UNDEFINED
}
