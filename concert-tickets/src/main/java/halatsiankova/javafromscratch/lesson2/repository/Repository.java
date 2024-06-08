package halatsiankova.javafromscratch.lesson2.repository;

import halatsiankova.javafromscratch.lesson2.model.Ticket;

import java.util.List;
import java.util.Collection;

public interface Repository<T, I> {
    void saveAll(Collection<T> lists);
    List<T> findByStadiumSector(Ticket.StadiumSector sector);
}
