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
    private Button payrollButton;

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

    // Method to switch to the "Payroll" view
    @FXML
    private void switchToPayroll() {
        try {
            Parent payrollView = FXMLLoader.load(getClass().getResource("/view/payroll.fxml"));
            Stage stage = (Stage) payrollButton.getScene().getWindow();
            stage.setScene(new Scene(payrollView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
