package com.pluralsight;
import java.sql.*;

public class main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";
        String password = "yearup";

        String query = "select * from Product";

        try{
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("ProductName");
                System.out.println(name);
            }
            conn.close();
    }catch(SQLException e){
        e.printStackTrace();}
    }
}
