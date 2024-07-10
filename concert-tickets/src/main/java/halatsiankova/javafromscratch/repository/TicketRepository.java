package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.Ticket;

import java.sql.SQLException;
import java.util.List;

public interface TicketRepository extends Repository<Ticket, Integer> {
    List<Ticket> findTicketByStadiumSector(StadiumSector stadiumSector) throws SQLException;
    List<Ticket> findAllByUserId(int userId) throws SQLException;
    void update (TicketType ticketType, int ticketId) throws SQLException;
}
