package com.z.controller;

import com.z.service.Validation;
import com.z.model.Employee;
import com.z.model.dao.EmployeeDAO;
import com.z.service.DatabaseService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEmployeeController {
    @FXML private Button addButton;
    @FXML private Button cancelButton;
    @FXML private TextField fNameField;
    @FXML private TextField lNameField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox<String> divChoice;
    @FXML private TextField jobTitleField;
    @FXML private TextField ssnField;
    @FXML private TextField salaryField;
    @FXML private TextField hireDateField;
    @FXML private TextField genderField;
    @FXML private TextField pronounsField;
    @FXML private TextField dobField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField streetField;
    @FXML private TextField cityField;
    @FXML private ChoiceBox<String> stateChoice;

    @FXML
    public void initialize()
    {
        divChoice.getItems().addAll("Technology Engineering", "Marketing", "Human Resources", "HQ");
        stateChoice.getItems().addAll("AL", "AK", "AZ", "AR", "AS", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL",
                                      "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV",
                                      "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
                                      "UT", "VT", "VA", "WA", "WV", "WI", "WY");
    }

    @FXML
    private void handleAdd()
    {
        String _fName = fNameField.getText();
        String _lName = lNameField.getText();
        String _email = emailField.getText();
        String _division = divChoice.getValue();
        String _jobTitle = jobTitleField.getText();
        String _ssn = ssnField.getText();
        double _salary = 0;
        String _hireDate = hireDateField.getText();
        String _gender = genderField.getText();
        String _pronouns = pronounsField.getText();
        String _dob = dobField.getText();
        String _phoneNumber = phoneNumberField.getText();
        String _street = streetField.getText();
        String _city = cityField.getText();
        String _state = stateChoice.getValue();
        
        if (!Validation.isValidSSN(_ssn) || !Validation.isValidDate(_hireDate) || !Validation.isValidEmail(_email)) {
            showAlert("Please enter valid SSN, hire date, and email.");
            return;
        }

        try {
            _salary = Double.parseDouble(salaryField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid salary. Please enter a numeric value.");
            return;
        }

        if (_fName.isEmpty() ||
            _lName.isEmpty() ||
            _email.isEmpty() ||
            _division == null ||
            _jobTitle.isEmpty() ||
            _ssn.isEmpty() ||
            _salary == 0 ||
            _hireDate.isEmpty() ||
            _gender.isEmpty() ||
            _pronouns.isEmpty() ||
            _dob.isEmpty() ||
            _phoneNumber.isEmpty() ||
            _street.isEmpty() ||
            _city.isEmpty() ||
            _state == null)
        {
            showAlert("Not all fields have been filled in!");
            return;
        }

        int _divID = DatabaseService.fetchDivisionID(_division);

        Employee employee = new Employee(true, _divID, _fName, _lName, _email, _ssn, _hireDate, _division, _jobTitle, _salary);
        try {
            if (EmployeeDAO.addEmployee(employee) /* TODO: handle address and demographic info */) {
                showAlert("Employee added successfully!");
            } else {
                showAlert("Error adding employee. Please try again.");
            }
        } catch (Exception e) {
            showAlert("An unexpected error occurred. Please try again.");
            e.printStackTrace();
        }
        
        // close window after done
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message)
    {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
