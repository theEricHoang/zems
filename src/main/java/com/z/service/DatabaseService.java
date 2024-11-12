package com.z.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseService {

    // Method to establish a database connection using environment variables
    public static Connection getConnection() {
        Dotenv dotenv = Dotenv.load();
        
        String dbUrl = dotenv.get("DB_URL");
        String dbUsername = dotenv.get("DB_USERNAME");
        String dbPassword = dotenv.get("DB_PASSWORD");

        try {
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to fetch the highest employee ID from the database
    public static int fetchHighestEmployeeID(Connection connection) {
        if (connection == null) {
            System.err.println("Connection is null. Cannot fetch highest employee ID.");
            return -1; // Indicate an error with a negative value
        }

        int highestID = 0;
        String query = "SELECT MAX(empid) AS maxID FROM employees";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            if (rs.next()) {
                highestID = rs.getInt("maxID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highestID;
    }

    // Method to fetch the division ID by division name
    public static int fetchDivisionID(String division) {
        int divID = -1; // Use -1 to indicate an invalid ID if not found
        String query = "SELECT ID FROM division WHERE Name = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection != null ? connection.prepareStatement(query) : null) {
             
            if (stmt == null) {
                System.err.println("Connection is null. Cannot fetch division ID.");
                return divID;
            }
             
            stmt.setString(1, division);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    divID = rs.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divID;
    }
}
