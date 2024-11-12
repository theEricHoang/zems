package com.z.controller;

import com.z.model.Employee; // Ensure Employee is imported correctly
import com.z.model.dao.EmployeeDAO;
import com.z.service.Validation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;

public class AddEmployeeController {
    @FXML private TextField fNameField;
    @FXML private TextField lNameField;
    @FXML private TextField emailField;
    @FXML private TextField ssnField;
    @FXML private TextField jobTitleField;
    @FXML private TextField hireDateField;
    @FXML private TextField salaryField;
    @FXML private ChoiceBox<String> stateChoice;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    private EmployeeDAO employeeDAO;

    // Constructor that accepts a Connection parameter
    public AddEmployeeController(Connection connection) {
        this.employeeDAO = new EmployeeDAO(connection); // Pass the connection to EmployeeDAO
    }

    @FXML
    public void initialize() {
        // Populate the state dropdown
        stateChoice.setItems(FXCollections.observableArrayList(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI",
            "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", 
            "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", 
            "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", 
            "VT", "VA", "WA", "WV", "WI", "WY"
        ));
    }

    @FXML
    private void handleAdd() {
        // Collect and validate data
        String fName = fNameField.getText();
        String lName = lNameField.getText();
        String email = emailField.getText();
        String ssn = ssnField.getText();
        String jobTitle = jobTitleField.getText();
        String hireDate = hireDateField.getText();
        String state = stateChoice.getValue();
        double salary;

        if (!Validation.isValidSSN(ssn) || !Validation.isValidDate(hireDate) || !Validation.isValidEmail(email)) {
            showAlert("Please enter valid SSN, hire date, and email.");
            return;
        }

        try {
            salary = Double.parseDouble(salaryField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid salary. Please enter a numeric value.");
            return;
        }

        Employee employee = new Employee(fName, lName, email, ssn, "", jobTitle, hireDate, salary, state);

        try {
            if (employeeDAO.addEmployee(employee)) {
                showAlert("Employee added successfully!");
                closeWindow();
            } else {
                showAlert("Error adding employee. Please try again.");
            }
        } catch (Exception e) {
            showAlert("An unexpected error occurred. Please try again.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
