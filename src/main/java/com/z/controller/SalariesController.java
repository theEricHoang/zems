package com.z.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SalariesController {
    @FXML
    private Button menuButton;

    @FXML
    private Button employeesButton;

    @FXML
    private Button updateSalariesButton;

    @FXML
    private Button totalPayButton;

    @FXML
    private void switchToUpdateSalaries()
    {
        try {
            Parent updateSalariesView = FXMLLoader.load(getClass().getResource("/view/update_salaries.fxml"));
            Stage updateSalariesStage = new Stage();
            updateSalariesStage.setTitle("Update Salaries");
            Scene scene = new Scene(updateSalariesView);
            updateSalariesStage.setScene(scene);
            updateSalariesStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToTotalPay()
    {
        try {
            Parent totalPayView = FXMLLoader.load(getClass().getResource("/view/total_pay.fxml"));
            Scene scene = new Scene(totalPayView);
            Stage stage = (Stage) totalPayButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToMenu()
    {
        try {
            Parent menuView = FXMLLoader.load(getClass().getResource("/view/main_view.fxml"));
            Scene scene = new Scene(menuView);
            Stage stage = (Stage) menuButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEmployees()
    {
        try {
            Parent employeesView = FXMLLoader.load(getClass().getResource("/view/employees.fxml"));
            Scene scene = new Scene(employeesView);
            Stage stage = (Stage) employeesButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
