package com.z.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TotalPayController {
    @FXML
    private Button menuButton;
    @FXML
    private Button employeesButton;
    @FXML
    private Button salariesButton;

    @FXML
    private Button divisionButton;
    @FXML
    private Button titleButton;
    @FXML
    private Button backButton;

    @FXML
    private TableView<?> divisionTable;
    @FXML
    private TableView<?> titleTable;

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

    @FXML
    private void switchToSalaries()
    {
        try {
            Parent salariesView = FXMLLoader.load(getClass().getResource("/view/salaries.fxml"));
            Scene scene = new Scene(salariesView);
            Stage stage = (Stage) salariesButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showDivisionTable()
    {
        divisionTable.setVisible(true);
        titleTable.setVisible(false);
    }

    @FXML
    private void showTitleTable()
    {
        divisionTable.setVisible(false);
        titleTable.setVisible(true);
    }
}