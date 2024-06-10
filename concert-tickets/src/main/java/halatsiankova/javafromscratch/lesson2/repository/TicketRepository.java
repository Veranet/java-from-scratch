package halatsiankova.javafromscratch.lesson2.repository;

import halatsiankova.javafromscratch.lesson2.enumerated.StadiumSector;
import halatsiankova.javafromscratch.lesson2.model.Ticket;

import java.util.List;

public interface TicketRepository extends Repository<Ticket, String> {
    List<Ticket> findTicketByStadiumSector(StadiumSector stadiumSector);
}