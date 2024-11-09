package com.z.controller;

import com.z.database.EmployeeDAO;
import com.z.model.Employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeesController {

    @FXML
    private ListView<String> employeeListView;
    @FXML
    private TextField nameField, ssnField, departmentField, jobTitleField, salaryField;

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @FXML
    public void initialize() {
        loadEmployeeData();
    }

    @FXML
    private void switchToMenu(ActionEvent event) {
        try {
            Parent menuView = FXMLLoader.load(getClass().getResource("/view/main_view.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(menuView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeeData() {
        try {
            employeeListView.getItems().clear();
            for (Employee employee : employeeDAO.getAllEmployees()) {
                String employeeInfo = employee.getName() + " - " + employee.getDepartment();
                employeeListView.getItems().add(employeeInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addEmployee() {
        try {
            Employee employee = new Employee(
                0,
                Integer.parseInt(departmentField.getText()),
                nameField.getText(),
                ssnField.getText(),
                departmentField.getText(),
                jobTitleField.getText(),
                Double.parseDouble(salaryField.getText()),
                null,
                null
            );
            employeeDAO.addEmployee(employee);
            loadEmployeeData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
