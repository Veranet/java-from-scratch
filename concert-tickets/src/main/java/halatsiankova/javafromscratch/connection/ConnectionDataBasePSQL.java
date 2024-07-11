package halatsiankova.javafromscratch.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBasePSQL {

    public Connection connection;

    public ConnectionDataBasePSQL(String url, String user, String password) {
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
