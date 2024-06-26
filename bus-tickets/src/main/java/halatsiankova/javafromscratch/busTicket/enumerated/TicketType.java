package halatsiankova.javafromscratch.busticket.enumerated;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum TicketType {
    DAY,
    WEEK,
    MONTH,
    YEAR,
    @JsonEnumDefaultValue
    UNDEFINED
}
