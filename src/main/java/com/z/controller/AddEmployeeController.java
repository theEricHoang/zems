package com.z.controller;

import com.z.service.Validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.z.model.Address;
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
    @FXML private ChoiceBox<String> jobTitleChoice;
    @FXML private TextField ssnField;
    @FXML private TextField salaryField;
    @FXML private TextField hireDateField;
    @FXML private TextField raceField;
    @FXML private TextField genderField;
    @FXML private TextField pronounsField;
    @FXML private TextField dobField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField streetField;
    @FXML private ChoiceBox<String> cityChoice;
    @FXML private ChoiceBox<String> stateChoice;

    @FXML
    public void initialize()
    {
        divChoice.getItems().addAll("Technology Engineering", "Marketing", "Human Resources", "HQ");
        jobTitleChoice.getItems().addAll("software manager", "software architect", "software engineer", "software developer", "marketing manager",
                                        "marketing associate", "marketing assistant", "Chief Exec. Officer", "Chief Finn. Officer", "Chief Info. Officer");
        stateChoice.getItems().addAll("GA", "NY");
        cityChoice.getItems().addAll("Atlanta", "New York");
    }

    @FXML
    private void handleAdd()
    {
        String _fName = fNameField.getText();
        String _lName = lNameField.getText();
        String _email = emailField.getText();
        String _division = divChoice.getValue();
        String _jobTitle = jobTitleChoice.getValue();
        String _ssn = ssnField.getText();
        double _salary = 0;
        String _hireDate = hireDateField.getText();
        String _race = raceField.getText();
        String _gender = genderField.getText();
        String _pronouns = pronounsField.getText();
        String _dob = dobField.getText();
        String _phoneNumber = phoneNumberField.getText();
        String _street = streetField.getText();
        String _city = cityChoice.getValue();
        String _state = stateChoice.getValue();
        
        /*
         * VALIDATION CHECKS
         */
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

        if (!Validation.validateGender(_gender) || !Validation.validatePronouns(_pronouns)) {
            showAlert("Invalid gender or pronouns.");
            return;
        }
        if (!Validation.validateRace(_race)) {
            showAlert("Invalid race.");
            return;
        }
        if (!Validation.validateDOB(_dob)) {
            showAlert("Date of birth must be in the past.");
            return;
        }
        if (!Validation.validatePhone(_phoneNumber)) {
            showAlert("Invalid phone number format. Use XXX-XXX-XXXX.");
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
            _race.isEmpty() ||
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

        /*
         * Data preprocessing
         */
        int _divID = DatabaseService.fetchDivisionID(_division);
        int _cityID = DatabaseService.fetchCityID(_city);
        int _stateID = DatabaseService.fetchStateID(_state);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate _dateOfBirth = LocalDate.parse(_dob, dateFormat);

        Employee employee = new Employee(true, _divID, _fName, _lName, _email, _ssn, _hireDate, _division, _jobTitle, _salary, null);
        Address address = new Address(employee.getEmpID(), _gender, _pronouns, _race, _dateOfBirth, _phoneNumber, _cityID, _stateID);
        employee.setAddress(address);
        
        try {
            if (EmployeeDAO.addEmployee(employee)) {
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
