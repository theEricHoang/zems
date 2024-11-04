package com.z.controller;

import com.z.model.Employee;
//import com.z.service.EmployeeManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    private ListView<Employee> employeeListView;
    @FXML
    private TextField nameField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField salaryField;

    //private EmployeeService employeeService = new EmployeeService();

    @FXML
    private void addEmployee() {
        //updateEmployeeList();
    }

    private void updateEmployeeList() {
        //employeeListView.getItems().clear();
        //employeeListView.getItems().addAll(employeeService.getAllEmployees());
    }
}
