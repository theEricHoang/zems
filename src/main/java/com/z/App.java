package com.z;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App extends Application {

    private static Connection databaseConnection;
    private static Stage primaryStage;

    // Initialize the database connection
    static {
        try {
            databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedata", "16618", "rtyDFGHJ!5");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get the database connection
    public static Connection getDatabaseConnection() {
        return databaseConnection;
    }

    // Method to get the primary stage
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_view.fxml"));
        stage.setTitle("Employee Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
