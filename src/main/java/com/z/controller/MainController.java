package com.z.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button employeesButton;

    @FXML
    private Button salariesButton;

    // Method to switch to the "Employees" view
    @FXML
    private void switchToEmployees() {
        try {
            Parent employeesView = FXMLLoader.load(getClass().getResource("/view/employees.fxml"));
            Stage stage = (Stage) employeesButton.getScene().getWindow();
            stage.setScene(new Scene(employeesView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to switch to the "Salaries" view
    @FXML
    private void switchToSalaries() {
        try {
            Parent salariesView = FXMLLoader.load(getClass().getResource("/view/salaries.fxml"));
            Stage stage = (Stage) salariesButton.getScene().getWindow();
            stage.setScene(new Scene(salariesView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
