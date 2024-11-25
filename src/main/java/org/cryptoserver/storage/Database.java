package org.cryptoserver.storage;

import org.cryptoserver.config.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private final String HOST;
    private final String USERNAME;
    private final String PASSWORD;
    private final String NAME;

    public Database(String host, String username, String password, String name) {
        this.HOST = host;
        this.USERNAME = username;
        this.PASSWORD = password;
        this.NAME = name;
    }

    public String getHost() {
        return this.HOST;
    }

    public String getName() {
        return this.NAME;
    }

    public String getUsername() {
        return this.USERNAME;
    }

    public String getPassword() {
        return this.PASSWORD;
    }

    public String getUrl() {
        String url = "jdbc:mysql://" + this.getHost() + ":3306/" + this.getName();
        return url;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(this.getUrl(), this.getUsername(), this.getPassword());
        return connection;
    }

    /**
     * Return database object after testing it
     * @return instance:
     */
    public static Database getInstance() {
        if (instance == null) {
            try {
                instance = new Database(Configuration.DB_HOST, Configuration.DB_USER, Configuration.DB_PASSWORD, Configuration.DB_NAME);
                // Test connection
                Connection connection = instance.getConnection();
                connection.close();
                System.out.println("Connected to the database!");
            } catch (SQLException e) {
                System.out.println("Loading database error!");
                e.printStackTrace();
                System.exit(0);
            }
        }

        return instance;
    }
}
