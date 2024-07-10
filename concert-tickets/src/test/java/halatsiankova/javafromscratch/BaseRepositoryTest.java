package halatsiankova.javafromscratch;

import halatsiankova.javafromscratch.connection.ConnectionDataBasePSQL;
import org.junit.jupiter.api.AfterAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseRepositoryTest {
    public static final ConnectionDataBasePSQL con;

    static {
            con = new ConnectionDataBasePSQL();
    }

    @AfterAll
    public static void clear() {
        String sqlQuery;
        try {
            sqlQuery = new String(Files.readAllBytes(Paths.get("src/main/resources/clear.sql")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (Statement statement = con.getConnection().createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
