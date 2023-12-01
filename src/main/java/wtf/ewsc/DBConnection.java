package wtf.ewsc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "wt";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "291068346";

    protected static Connection getConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName(DB_DRIVER);
        return DriverManager.getConnection(DB_URL + DB_NAME,
                DB_USER,
                DB_PASSWORD);
    }
}