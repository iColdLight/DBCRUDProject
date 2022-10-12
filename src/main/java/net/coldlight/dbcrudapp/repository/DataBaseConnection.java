package net.coldlight.dbcrudapp.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static DataBaseConnection dataBaseConnection;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/test1";

    private static final String USER = "Coldlight";
    private static final String PASSWORD = "Coldlight";
    private Connection connection;

    private DataBaseConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataBaseConnection getInstance() {
        if (dataBaseConnection == null) {
            dataBaseConnection = new DataBaseConnection();
        }
        return dataBaseConnection;
    }

    public Connection getConnection() {
        return connection;
    }

}
