package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.model.Ticket;

import java.util.List;

public interface TicketRepository extends Repository<Ticket, String> {
    List<Ticket> findTicketByStadiumSector(StadiumSector stadiumSector);
}
