package halatsiankova.javafromscratch.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBasePSQL {
    private static final String url = "jdbc:postgresql://localhost:5432/my_ticket_service_db";
    private static final String user = "myuser";
    private static final String password = "mypassword";
    public Connection connection;

    public ConnectionDataBasePSQL() {
        try {
            this.connection = DriverManager.getConnection(
                    url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
