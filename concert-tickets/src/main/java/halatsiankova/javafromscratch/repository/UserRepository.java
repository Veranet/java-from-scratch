package halatsiankova.javafromscratch.repository;

import halatsiankova.javafromscratch.model.BaseUser;

import java.sql.SQLException;

public interface UserRepository extends Repository<BaseUser, Integer> {
    boolean deleteById(int userId) throws SQLException;
}
