package halatsiankova.javafromscratch.repository;

import java.sql.SQLException;
import java.util.Optional;

public interface Repository<T, I> {
    void save(T obj) throws SQLException;
    Optional<T> findById(I id) throws SQLException;
}
