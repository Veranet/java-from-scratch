package halatsiankova.javafromscratch.lesson2.repository;

import java.util.Collection;

public interface Repository<T, I> {
    void save(T obj);
    void saveAll(Collection<T> lists);
}
