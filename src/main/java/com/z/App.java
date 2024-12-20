package com.z;

import java.sql.Connection;
import java.sql.SQLException;

import com.z.model.Employee;
import com.z.service.DatabaseService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/main_view.fxml"));
            stage.setTitle("Z Employee Management System");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public static void main(String[] args)
    {
        try (Connection connection = DatabaseService.getConnection()) {
            // set employee counter
            int highestID = DatabaseService.fetchHighestEmployeeID(connection);
            Employee.setInitialIDCounter(highestID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        launch(args);
    }
}