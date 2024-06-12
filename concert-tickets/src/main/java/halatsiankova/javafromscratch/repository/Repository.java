package halatsiankova.javafromscratch.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T, I> {
    void save(T obj);
    void saveAll(Collection<T> lists);
    Optional<T> findById(I id);
}
