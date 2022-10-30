package net.coldlight.dbcrudapp.repository;


import java.sql.*;

public class JdbcUtils {
    private static JdbcUtils dataBaseConnection;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/test1";

    private static final String USER = "Coldlight";
    private static final String PASSWORD = "Coldlight";
    private Connection connection;

    private JdbcUtils() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static JdbcUtils getInstance() {
        if (dataBaseConnection == null) {
            dataBaseConnection = new JdbcUtils();
        }
        return dataBaseConnection;
    }

    private Connection getConnection() {
        return connection;
    }

    public static Statement getStatement () throws SQLException {
        return getInstance().connection.createStatement();
    }

    public static PreparedStatement getPreparedStatement (String sql) throws SQLException {
        return getInstance().connection.prepareStatement(sql);
    }
}


