package com.z.controller;

import java.io.IOException;

import com.z.model.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EmployeesController {
    /*
     * COMPONENT DECLARATIONS
     * separate lines because that's how java annotations work :(
     */
    @FXML
    private Button menuButton;
    @FXML
    private Button salariesButton;
    @FXML
    private Button addEmployeeButton;

    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField deptIDField;
    @FXML
    private TextField SSNField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField payrollField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField addressField;

    @FXML
    private void handleAddEmployee()
    {
        String _fName = fNameField.getText();
        String _lName = lNameField.getText();
        String _fullName = _fName + " " + _lName;
        String _title = titleField.getText();
        String _deptID = deptIDField.getText();
        String _ssn = SSNField.getText();
        String _salary = salaryField.getText();
        String _department = departmentField.getText();
        String _address = addressField.getText();

        // _fName and _lName must be checked separately bc _fullName can be non-empty even when only one field is filled
        if (_fName.isEmpty() ||
            _lName.isEmpty() ||
            _title.isEmpty() ||
            _deptID.isEmpty() ||
            _ssn.isEmpty() ||
            _salary.isEmpty() ||
            _department.isEmpty() ||
            _address.isEmpty())
        {
            showAlert("One or more fields is empty!");
            return;
        }

        // TODO: parse address info and add employee
        // Employee newEmployee = new Employee();
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

    private void showAlert(String message)
    {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
