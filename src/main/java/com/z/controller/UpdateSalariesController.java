package com.z.controller;

import com.z.model.dao.PayrollDAO;
import com.z.service.DatabaseService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateSalariesController {
    @FXML private TextField minSalaryField;
    @FXML private TextField maxSalaryField;
    @FXML private TextField percentageField;
    @FXML private Button updateButton;
    @FXML private Button backButton;

    @FXML
    private void initialize()
    {
        updateButton.setOnAction(event -> handleUpdateSalaries());
        backButton.setOnAction(event -> closeWindow());
    }

    @FXML
    private void handleUpdateSalaries()
    {
        String minSalaryInput = minSalaryField.getText().trim();
        String maxSalaryInput = maxSalaryField.getText().trim();
        String percentageInput = percentageField.getText().trim();

        if (minSalaryInput.isEmpty() || maxSalaryInput.isEmpty() || percentageInput.isEmpty()) 
        {
            showAlert("All fields are required.");
            return;
        }

        try {
            double minSalary = Double.parseDouble(minSalaryInput);
            double maxSalary = Double.parseDouble(maxSalaryInput);
            double percentage = Double.parseDouble(percentageInput);

            if (minSalary >= maxSalary) {
                showAlert("Minimum salary must be less than maximum salary!");
                return;
            }

            if (percentage <= 0) {
                showAlert("Percentage must be greater than 0!");
                return;
            }

            try (Connection conn = DatabaseService.getConnection()) {
                PayrollDAO.updateEmployeeSalaryByRange(percentage, minSalary, maxSalary, conn);
                showAlert("Salaries updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Unable to update salaries: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter numeric values for salary and percentage.");
        }
    }

    @FXML
    private void closeWindow()
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message)
    {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}