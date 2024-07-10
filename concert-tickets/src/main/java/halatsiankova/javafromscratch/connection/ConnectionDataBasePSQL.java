package halatsiankova.javafromscratch.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBasePSQL {
    private static Connection connection;

    public ConnectionDataBasePSQL() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/my_ticket_service_db", "myuser", "mypassword");
    }

    public Connection getConnection() {
        return connection;
    }
}
