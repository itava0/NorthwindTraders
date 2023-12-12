package org.pluralsight;
import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = dotenv.get("PASSWORD");

        String query = "SELECT * FROM Products";
        try {
            // Establishing connection
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            // Executing query
            ResultSet results = stmt.executeQuery(query);

            // Processing the result set
            while (results.next()) {
                System.out.println("ProductID " + results.getString("ProductID"));
                System.out.println("ProductName " + results.getString("ProductName"));
                System.out.println("UnitPrice " + results.getString("UnitPrice"));
                System.out.println("UnitsInStock " + results.getString("UnitsInStock"));
                System.out.println("""
                        ----------------------------------
                        """);

            }

            // Closing resources
            results.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}