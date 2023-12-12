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
            ResultSet rs = stmt.executeQuery(query);

            // Processing the result set
            while (rs.next()) {
                // Replace with your column names and types
                System.out.println(rs.getString(2));
            }

            // Closing resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}