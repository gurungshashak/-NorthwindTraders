package com.pluralsight;
import java.sql.*;

public class main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                "root",
                "yearup");

        Statement statement = connection.createStatement();
        String query = "select * from product";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String name = resultSet.getString("product_name");
            System.out.println(name);
        }
        connection.close();
    }
}
