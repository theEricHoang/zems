package com.z.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseService {
    public static Connection getConnection()
    {
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

    public static int fetchHighestEmployeeID(Connection connection)
    {
        int highestID = 0;
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT MAX(empid) AS maxID FROM employees";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                highestID = rs.getInt("maxID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return highestID;
    }

    public static int fetchDivisionID(String division)
    {
        int divID = 0;
        String query = "SELECT ID FROM division WHERE Name = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, division);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                divID = rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divID;
    }
}
