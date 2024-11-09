package com.z.service;

import java.sql.Connection;
import java.sql.DriverManager;
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

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
}
