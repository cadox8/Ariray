package es.cadox8.ariray.database;

import es.cadox8.ariray.Ariray;
import es.cadox8.ariray.utils.Log;

import java.sql.*;

public class MySQL {

    private final Ariray plugin = Ariray.getInstance();

    protected Connection connection;

    private final String user, database, password, port, hostname;

    public MySQL(String hostname, String port, String database, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
    }

    public boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    public Connection getConnection() {
        try {
            return this.openConnection();
        } catch (SQLException | ClassNotFoundException e) {
            Log.error(e.getMessage());
            return null;
        }
    }

    public boolean closeConnection() throws SQLException {
        if (connection == null) return false;
        connection.close();
        return true;
    }

    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if (checkConnection()) return connection;

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"
                + this.hostname + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password);
        return connection;
    }
}
