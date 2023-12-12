package org.pluralsight;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.Scanner;

public class App {
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        String username = System.getenv("USERNAME");
        String password = System.getenv("PASSWORD");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        String userInput = "";
        while (true) {
            System.out.println("""
                    What do you want to do?
                    1) Display all products
                    2) Display all customers
                    3) Display all categories
                    0) Exit""");
            userInput = scan.nextLine();
            switch(userInput){
                case "1":
                    displayProducts(dataSource);
                    break;
                case "2":
                    displayCustomers(dataSource);
                    break;
                case "3":
                    displayCategories(dataSource);
                    break;
                case "0":
                    scan.close();
                    dataSource.close();
                    System.exit(0);
                default:
                    System.out.println("ERROR: Please choose a valid option");
                    break;
            }
        }
    }

    public static void displayProducts(BasicDataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from products;")){
            try( ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    System.out.println("ID: " + results.getString(1));
                    System.out.println("Name: " + results.getString(2));
                    System.out.println("Price: " + results.getDouble(6));
                    System.out.println("Stock: " + results.getString(7));
                    System.out.println("__________________________________________________________");
                    System.out.println();
                }
            }
        }
        catch (SQLException e){
            System.out.println("An error has occurred in the search");
        }
    }
    public static void displayCustomers(BasicDataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from customers;");){
            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    System.out.println("ID: " + results.getString(1));
                    System.out.println("Name: " + results.getString(3));
                    System.out.println("Address: " + results.getString(5));
                    System.out.println("City: " + results.getString(6));
                    System.out.println("Country: " + results.getString(9));
                    System.out.println("__________________________________________________________");
                    System.out.println();
                }
            }
        }
        catch (SQLException e){
            System.out.println("An error has occurred in the search");
        }
    }
    public static void displayCategories(BasicDataSource dataSource){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from categories order by categoryid;");){
            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    System.out.println("ID: " + results.getString(1));
                    System.out.println("Name: " + results.getString(2));
                    System.out.println("__________________________________________________________");
                    System.out.println();
                }
            }
        }
        catch (SQLException e){
            System.out.println("An error has occurred in the search");
        }
        String userInput = "";
        while(!userInput.equalsIgnoreCase("0")){
            System.out.println("Which category would you like to view the products of? (0 to exit, please select by ID)");
            userInput = scan.nextLine();
            switch(userInput){
                case "1", "2", "3", "4", "5", "6", "7", "8":
                    try (Connection connection = dataSource.getConnection();
                         PreparedStatement statement = connection.prepareStatement("select * from products where categoryid = " + userInput + ";")){
                        try( ResultSet results = statement.executeQuery()) {
                            while (results.next()) {
                                System.out.println("ID: " + results.getString(1));
                                System.out.println("Name: " + results.getString(2));
                                System.out.println("Price: " + results.getDouble(6));
                                System.out.println("Stock: " + results.getString(7));
                                System.out.println("__________________________________________________________");
                                System.out.println();
                            }
                        }
                    }
                    catch (SQLException e){
                        System.out.println("An error has occurred in the search");
                    }
                    break;
                case "0":
                    break;
                default:
                    System.out.println("ERROR: Please choose a category by ID or enter 0");
                    break;
            }
        }
    }
}