package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.connection.ConnectionDataBasePSQL;
import halatsiankova.javafromscratch.enumerated.Status;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.provider.SessionFactoryProvider;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.Optional;

public class UserRepositoryImpl  implements UserRepository {

    private final ConnectionDataBasePSQL connection;

    public UserRepositoryImpl(ConnectionDataBasePSQL connection) {
        this.connection = connection;
    }

    @Override
    public void save(BaseUser user) {
        var session = SessionFactoryProvider.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public Optional<BaseUser> findById(Integer id) throws SQLException {
        return Optional.ofNullable(SessionFactoryProvider
                .getSessionFactory()
                .openSession()
                .get(BaseUser.class, id));
    }

    @Override
    public boolean deleteById(int userId) {
        boolean deleted = false;

        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();

            try {
                int ticketsDeleted = session.createQuery("DELETE FROM Ticket WHERE userId = :userId")
                        .setParameter("userId", userId)
                        .executeUpdate();

                var hql = "DELETE FROM " + BaseUser.class.getCanonicalName() + " WHERE id = :userId";
                int userDeleted = session.createQuery(hql)
                        .setParameter("userId", userId)
                        .executeUpdate();

                transaction.commit();

                deleted = (ticketsDeleted > 0) && (userDeleted > 0);

            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public void updateUserAndSaveTicket(BaseUser user, Ticket ticket) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (user.getStatus() == Status.ACTIVATED) {
                session.merge(user);
                ticket.setUserId(user.getId());
                session.persist(ticket);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
