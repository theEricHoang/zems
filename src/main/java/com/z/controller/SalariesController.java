package com.z.controller;

import com.z.model.Payroll;
import com.z.model.dao.PayrollDAO;
import com.z.service.DatabaseService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SalariesController {
    @FXML private Button menuButton;
    @FXML private Button employeesButton;
    @FXML private Button updateSalariesButton;
    @FXML private Button totalPayButton;

    @FXML private TableView<Payroll> payrollTable;
    @FXML private TableColumn<Payroll, String> employeeName;
    @FXML private TableColumn<Payroll, String> employeeTitle;
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
    private void handleSearch()
    {
        String search = searchField.getText().trim();

        // Check if the search field is empty
        if (search.isEmpty()) {
            loadPayrollData(); // Reset the table view to show all payroll data
            return;
        }

        // Validate that the search input is a valid integer for empID
        try {
            int empID = Integer.parseInt(search);

            // Query PayrollDAO to retrieve the payroll information by empID
            try (Connection conn = DatabaseService.getConnection()) {
                payrollData.clear();
                ObservableList<Payroll> payrolls = PayrollDAO.getPayrollInfoByEmpID(empID, conn);

                // Display the result in the payroll table
                if (payrolls != null) {
                    payrollData.clear();  // Clear any existing data
                    payrollData = payrolls;
                    payrollTable.setItems(payrollData);  // Update the table view
                } else {
                    showAlert("No payroll record found for Employee ID: " + empID);
                    loadPayrollData();  // Reset the table if no record is found
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Employee ID. Please enter a numeric value.");
        }
    }

    @FXML
    public void initialize()
    {
        employeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
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

        searchButton.setOnAction(event -> handleSearch());

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

