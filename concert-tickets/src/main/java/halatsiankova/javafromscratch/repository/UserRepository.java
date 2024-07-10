package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.model.BaseUser;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends Repository<BaseUser, Integer> {
    List<BaseUser> findAll() throws SQLException;
    boolean deleteById(int userId) throws SQLException;
}
