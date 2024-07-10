package halatsiankova.javafromscratch;

import halatsiankova.javafromscratch.connection.ConnectionDataBasePSQL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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

    @BeforeEach
    void preparedDataBase() throws IOException {
        var sqlQuery = getResource();
        try (Statement statement = con.getConnection().createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    public void clear() {
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

    protected static String getResource() throws IOException {
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/resources/init.sql")));
        } catch (IOException exception) {
            System.out.println("\nCannot read file: " + exception.getMessage());
            throw exception;
        }
    }
}
