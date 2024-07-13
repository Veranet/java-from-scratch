package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.model.BaseUser;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<BaseUser, Integer> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value =
            "INSERT INTO ticket (user_id, ticket_type, creation_date)" +
                    " SELECT ?1, CAST(?2 AS ticket_type), ?3 " +
                    "WHERE (SELECT status from users u where u.id = ?1) = 'ACTIVATED';")
    void updateBaseUserByIdAndTicket(int userId, String type, LocalDateTime creationDate);
}
