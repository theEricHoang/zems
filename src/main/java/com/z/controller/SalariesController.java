package com.z.controller;

import com.z.model.Payroll;
import com.z.model.dao.PayrollDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.sql.SQLException;

public class SalariesController {
    @FXML private Button menuButton;
    @FXML private Button employeesButton;
    @FXML private Button updateSalariesButton;
    @FXML private Button totalPayButton;

    @FXML private TableView<Payroll> payrollTable;
    @FXML private TableColumn<Payroll, String> employeeName;
    @FXML private TableColumn<Payroll, String> employeeEmail;
    @FXML private TableColumn<Payroll, Integer> employeeID; 
    @FXML private TableColumn<Payroll, Double> employeePayDate;
    @FXML private TableColumn<Payroll, Double> employeeGross;
    @FXML private TableColumn<Payroll, Double> employeeFederal;
    @FXML private TableColumn<Payroll, Double> employeeFedMed;
    @FXML private TableColumn<Payroll, Double> employeeFedSS;
    @FXML private TableColumn<Payroll, Double> employeeState;
    @FXML private TableColumn<Payroll, Double> employee401K;
    @FXML private TableColumn<Payroll, Double> employeeHealthCare;
    @FXML private TableColumn<Payroll, Void> editButtons;
    @FXML private TableColumn<Payroll, Void> deleteButtons;
    private ObservableList<Payroll> payrollData = FXCollections.observableArrayList();

    @FXML private TextField searchField;
    @FXML private Button searchButton;

    @FXML
    public void initialize()
    {
        employeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeeID.setCellValueFactory(new PropertyValueFactory<>("empID"));
        employeePayDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        employeeGross.setCellValueFactory(new PropertyValueFactory<>("gross"));
        employeeFederal.setCellValueFactory(new PropertyValueFactory<>("federal"));
        employeeFedMed.setCellValueFactory(new PropertyValueFactory<>("fedMed"));
        employeeFedSS.setCellValueFactory(new PropertyValueFactory<>("fedSS"));
        employeeState.setCellValueFactory(new PropertyValueFactory<>("state"));
        employee401K.setCellValueFactory(new PropertyValueFactory<>("emp401K"));
        employeeHealthCare.setCellValueFactory(new PropertyValueFactory<>("healthCare"));

        loadPayrollData();
    }

    private void loadPayrollData() {
        payrollData.clear();

        try {
            payrollData = PayrollDAO.getAllPayrolls();
            payrollTable.setItems(payrollData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

