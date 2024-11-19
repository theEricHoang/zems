package com.z.controller;

import java.sql.SQLException;

import com.z.model.Employee;
import com.z.model.dao.EmployeeDAO;
import com.z.service.DatabaseService;
import com.z.service.Validation;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditEmployeeController {
    @FXML private Button saveButton;
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

    private Employee employee;

    @FXML
    public void initialize()
    {
        divChoice.getItems().addAll("Technology Engineering", "Marketing", "Human Resources", "HQ");
        jobTitleChoice.getItems().addAll("software manager", "software architect", "software engineer", "software developer", "marketing manager",
                                        "marketing associate", "marketing assistant", "Chief Exec. Officer", "Chief Finn. Officer", "Chief Info. Officer");
        stateChoice.getItems().addAll("GA", "NY");
        cityChoice.getItems().addAll("Atlanta", "New York");
    }

    public void setEmployee(Employee _employee)
    {
        employee = _employee;

        fNameField.setText(employee.getFName());
        lNameField.setText(employee.getLName());
        emailField.setText(employee.getEmail());
        divChoice.setValue(employee.getDivision());
        jobTitleChoice.setValue(employee.getJobTitle());
        ssnField.setText(employee.getSSN());
        salaryField.setText(String.valueOf(employee.getSalary()));
        hireDateField.setText(employee.getHireDate());

        raceField.setText(employee.getAddress().getRace());
        genderField.setText(employee.getAddress().getGender());
        pronounsField.setText(employee.getAddress().getPronouns());
        dobField.setText(String.valueOf(employee.getAddress().getDob()));
        phoneNumberField.setText(employee.getAddress().getPhone());
        // streetField.setText(employee.getAddress().getStreet());

        cityChoice.setValue(DatabaseService.fetchCity(employee.getAddress().getCityID()));
        stateChoice.setValue(DatabaseService.fetchState(employee.getAddress().getStateID()));
    }

    @FXML
    private void handleSave()
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

        int _divID = DatabaseService.fetchDivisionID(_division);
        employee.setFName(_fName);
        employee.setLName(_lName);
        employee.setEmail(_email);
        employee.setDivID(_divID);
        employee.setDivision(_division);
        employee.setJobTitle(_jobTitle);
        employee.setSSN(_ssn);
        employee.setSalary(_salary);
        employee.setHireDate(_hireDate);
        
        employee.getAddress().setCityID(DatabaseService.fetchCityID(_city));
        employee.getAddress().setStateID(DatabaseService.fetchStateID(_state));
        employee.getAddress().setGender(_gender);
        employee.getAddress().setPronouns(_pronouns);
        employee.getAddress().setRace(_race);
        employee.getAddress().setDob(Validation.formatDOB(_dob));
        employee.getAddress().setPhone(_phoneNumber);

        try {
            EmployeeDAO.updateEmployee(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // close window after done
        Stage stage = (Stage) saveButton.getScene().getWindow();
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
