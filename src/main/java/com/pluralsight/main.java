package com.pluralsight;
import java.sql.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllProducts();
                    break;
                case "2":
                    displayAllCustomers();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Press ENTER to continue...");
                    scanner.nextLine();
            }
        }
        scanner.close();
    }


    public static void displayAllProducts() {
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "yearup";


        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        try
                (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {


            System.out.println("=== Option 1: Stacked Information ===");
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("UnitPrice");
                int stock = rs.getInt("UnitsInStock");

                System.out.println("Product Id: " + id);
                System.out.println("Name: " + name);
                System.out.println("Price: " + String.format("%.2f", price));
                System.out.println("Stock: " + stock);
                System.out.println("------------------");
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void displayAllCustomers() {
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "yearup";


        String query = "SELECT ContactName, CompanyName, City, Country, Phone " + "FROM Customers " + "ORDER BY Country";
       try{
           Connection conn = DriverManager.getConnection(url, user, password);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(query);
           while (rs.next()) {
               System.out.println("Contact Name : " + rs.getString("ContactName"));
               System.out.println("Company Name : " + rs.getString("CompanyName"));
               System.out.println("City         : " + rs.getString("City"));
               System.out.println("Country      : " + rs.getString("Country"));
               System.out.println("Phone        : " + rs.getString("Phone"));
               System.out.println("----------------------------------------------------");
           }
           conn.close();
           rs.close();
           stmt = conn.createStatement();

       } catch(Exception e){
           System.out.println(e.getMessage());

       }

    }
}
