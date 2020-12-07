package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection initDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
//        return DriverManager.getConnection(
//                "jdbc:postgresql://placebo.coyqu3v8uxec.us-east-2.rds.amazonaws.com:5432/placebo",
//                "postgres",
//                "placebo_123"
//        );
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/placebo",
                "postgres",
                "root"
        );
    }
}
