package com.pluralsight;
import java.sql.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("========================\n");
            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("3) Display all categories");
            System.out.println("4) Display all products by ID");
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
                case "3":
                    displayCategories();
                    break;
                case "4":
                    displayCategoriesAndProducts(scanner);
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
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("Contact Name : " + rs.getString("ContactName"));
                System.out.println("Company Name : " + rs.getString("CompanyName"));
                System.out.println("City         : " + rs.getString("City"));
                System.out.println("Country      : " + rs.getString("Country"));
                System.out.println("Phone        : " + rs.getString("Phone"));
                System.out.println("----------------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private static void displayCategories() {
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "yearup";

        System.out.println("\n--- Categories ---");

        String sqlCategories = "SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID";


        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sqlCategories);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                System.out.printf("ID:[%d]: %s%n", categoryId, categoryName);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving categories: " + e.getMessage());
        }
    }

    private static void displayCategoriesAndProducts(Scanner scanner) {
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "yearup";

        System.out.println("\n--- Categories ---");

        String sqlCategories = "SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sqlCategories);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                System.out.printf("ID:[%d]: %s%n", categoryId, categoryName);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving categories: " + e.getMessage());
            return;
        }


        System.out.print("\nEnter a Category ID to view its products: ");
        int selectedId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n--- Products in Selected Category ---");

        String sqlProducts = """
            SELECT ProductID, ProductName, UnitPrice, UnitsInStock
            FROM Products
            WHERE CategoryID = ?
            ORDER BY ProductID
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sqlProducts)) {

            stmt.setInt(1, selectedId);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    int productId = rs.getInt("ProductID");
                    String productName = rs.getString("ProductName");
                    double unitPrice = rs.getDouble("UnitPrice");
                    int unitsInStock = rs.getInt("UnitsInStock");

                    System.out.printf("%d) %s | $%.2f | Stock: %d%n",
                            productId, productName, unitPrice, unitsInStock);
                }

                if (!found) {
                    System.out.println("No products found for this category.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
    }


}
