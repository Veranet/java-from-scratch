package halatsiankova.javafromscratch.converters;

import halatsiankova.javafromscratch.dto.TicketDto;
import halatsiankova.javafromscratch.model.Ticket;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TicketToTicketDtoConverter implements Converter<Ticket, TicketDto> {

    @Override
    public TicketDto convert(Ticket source) {
        return new TicketDto(source.getType(), source.getCreatedDateTime(), source.getUserId());
    }
}
