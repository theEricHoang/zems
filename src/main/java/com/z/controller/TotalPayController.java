package com.z.controller;

import com.z.model.TotalPayByDivision;
import com.z.model.TotalPayByTitle;
import com.z.model.dao.PayrollDAO;
import com.z.service.DatabaseService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TotalPayController {
    @FXML private Button menuButton;
    @FXML private Button employeesButton;
    @FXML private Button salariesButton;
    @FXML private Button divisionButton;
    @FXML private Button titleButton;

    @FXML private TableView<TotalPayByDivision> divisionTable;
    @FXML private TableColumn<TotalPayByDivision, String> employeeDivision;
    @FXML private TableColumn<TotalPayByDivision, Double> employeeDivisionTotalPay;

    @FXML private TableView<TotalPayByTitle> titleTable;
    @FXML private TableColumn<TotalPayByTitle, String> employeeTitle;
    @FXML private TableColumn<TotalPayByTitle, Double> employeeTitleTotalPay;

    private ObservableList<TotalPayByDivision> divisionData = FXCollections.observableArrayList();
    private ObservableList<TotalPayByTitle> titleData = FXCollections.observableArrayList();

    @FXML
    private void initialize()
    {
        // Populating columns for total pay by month by division
        employeeDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        employeeDivisionTotalPay.setCellValueFactory(new PropertyValueFactory<>("totalPay"));

        // Populating columns for total pay by month by job titles
        employeeTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        employeeTitleTotalPay.setCellValueFactory(new PropertyValueFactory<>("totalPay"));

        loadDivisionData();
        loadTitleData();
    }

    private void loadDivisionData() 
    {
        divisionData.clear();

        try (Connection conn = DatabaseService.getConnection()) {
            divisionData = FXCollections.observableArrayList(PayrollDAO.getDivisionPay(conn));
            divisionTable.setItems(divisionData);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading division pay data.");
        }
    }

    private void loadTitleData() 
    {
        titleData.clear();
        
        try (Connection conn = DatabaseService.getConnection()) {
            titleData = FXCollections.observableArrayList(PayrollDAO.getJobTitlePay(conn));
            titleTable.setItems(titleData);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading title pay data.");
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