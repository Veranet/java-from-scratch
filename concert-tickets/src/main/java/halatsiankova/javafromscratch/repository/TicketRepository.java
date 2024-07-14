package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.model.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<Ticket> findById(int id);

    List<Ticket> findAllByUserId(int userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
            "UPDATE ticket SET ticket_type = CAST(?2 AS ticket_type) WHERE id = ?1 ;")
    void updateTicketTypeById(int ticketId, String ticketType);
}
