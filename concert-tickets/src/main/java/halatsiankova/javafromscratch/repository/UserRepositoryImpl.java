package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.connection.ConnectionDataBasePSQL;
import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.provider.SessionFactoryProvider;

import org.hibernate.Session;

import java.sql.SQLException;
import java.util.Optional;

public class UserRepositoryImpl extends ConnectionDataBasePSQL implements UserRepository {

    public UserRepositoryImpl() {
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
}
