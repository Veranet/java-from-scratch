package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.connection.ConnectionDataBasePSQL;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.provider.SessionFactoryProvider;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TicketRepositoryImpl extends ConnectionDataBasePSQL implements TicketRepository {

    @Override
    public void save(Ticket ticket) {
        var session = SessionFactoryProvider.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        session.save(ticket);
        transaction.commit();
        session.close();
    }

    @Override
    public Optional<Ticket> findById(Integer id) throws SQLException {
        return Optional.ofNullable(SessionFactoryProvider
                .getSessionFactory()
                .openSession()
                .get(Ticket.class, id));
    }

    public List<Ticket> findAllByUserId(int userId) throws SQLException {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            var hql = "FROM " + Ticket.class.getCanonicalName() + " T WHERE T.userId = :passed_user";
            var query = session.createSelectionQuery(hql, Ticket.class);
            query.setParameter("passed_user", userId);
            return query.list();
        }
    }

    @Override
    public void update(TicketType ticketType, int ticketId) throws SQLException {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        var ticket = session.get(Ticket.class, ticketId);
        if (ticketType != null) {
            ticket.setType(ticketType);
            session.update(ticket);
            transaction.commit();
            session.close();
        }
    }
}
