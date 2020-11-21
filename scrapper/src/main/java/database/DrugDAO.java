package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DrugDAO {
    public static void insert(Drug drug) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.initDB();

        String query = "INSERT INTO Drug (name, type, description, price, store_url, image_url) VALUES(?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, drug.getName());
        statement.setString(2, drug.getType());
        statement.setString(3, drug.getDescription());
        statement.setDouble(4, drug.getPrice());
        statement.setString(5, drug.getStoreUrl());
        statement.setString(6, drug.getImageUrl());

        System.out.println(statement.toString());

        statement.executeUpdate();
        connection.close();
    }
}
