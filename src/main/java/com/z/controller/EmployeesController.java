/*
 * This class, EmployeesController, is responsible for managing the
 * display of employee data in a table, as well as handling user interactions
 * like adding new employees and searching for specific ones.
 * It uses data from the database through the EmployeeDAO class and 
 * displays it in a TableView for easy viewing in the application.
 */

 package com.z.controller;

 import com.z.App;
 import com.z.model.Employee;
 import com.z.model.dao.EmployeeDAO;
 import javafx.collections.FXCollections;
 import javafx.collections.ObservableList;
 import javafx.fxml.FXML;
 import javafx.fxml.FXMLLoader;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import javafx.scene.control.*;
 import javafx.scene.control.cell.PropertyValueFactory;
 import javafx.stage.Modality;
 import javafx.stage.Stage;
 
 import java.io.IOException;
 import java.sql.Connection;
 import java.util.List;
 
 public class EmployeesController {
     // Buttons for user actions within the UI
     @FXML private Button menuButton;
     @FXML private Button salariesButton;
     @FXML private Button addEmployeeButton;
 
     // TableView and columns to display employee details
     @FXML private TableView<Employee> employeeTable;
     @FXML private TableColumn<Employee, Integer> employeeIDs;
     @FXML private TableColumn<Employee, String> employeeFNames;
     @FXML private TableColumn<Employee, String> employeeLNames;
     @FXML private TableColumn<Employee, String> employeeDivisions;
     @FXML private TableColumn<Employee, String> employeeJobTitles;
     @FXML private TableColumn<Employee, String> employeeEmails;
     @FXML private TableColumn<Employee, String> employeeSSNs;
     @FXML private TableColumn<Employee, String> employeeHireDates;
     @FXML private TableColumn<Employee, Double> employeeSalaries;
 
     // UI elements for search functionality
     @FXML private ChoiceBox<String> searchCriteriaChoiceBox; // Dropdown to select search criteria
     @FXML private TextField searchField; // Field where users enter their search term
 
     // ObservableList to hold data for displaying in the table
     private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
     
     // EmployeeDAO object for database operations
     private EmployeeDAO employeeDAO;
 
     // Constructor to set up EmployeeDAO with a database connection
     public EmployeesController() {
         Connection connection = App.getDatabaseConnection(); // Get the connection from the main App class
         this.employeeDAO = new EmployeeDAO(connection); // Pass connection to create EmployeeDAO instance
     }
 
     /*
      * This initialize() method runs automatically when the controller is loaded.
      * It sets up the table to display the correct data, populates the search criteria,
      * and loads initial employee data into the table.
      */
     @FXML
     private void initialize() {
         // Set up the columns in the table to show employee details
         employeeIDs.setCellValueFactory(new PropertyValueFactory<>("empID"));         // Maps empID to the employeeIDs column
         employeeFNames.setCellValueFactory(new PropertyValueFactory<>("fName"));      // Maps first name to employeeFNames column
         employeeLNames.setCellValueFactory(new PropertyValueFactory<>("lName"));      // Maps last name to employeeLNames column
         employeeDivisions.setCellValueFactory(new PropertyValueFactory<>("division"));// Maps division to employeeDivisions column
         employeeJobTitles.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));// Maps job title to employeeJobTitles column
         employeeEmails.setCellValueFactory(new PropertyValueFactory<>("email"));      // Maps email to employeeEmails column
         employeeSSNs.setCellValueFactory(new PropertyValueFactory<>("ssn"));          // Maps SSN to employeeSSNs column
         employeeHireDates.setCellValueFactory(new PropertyValueFactory<>("hireDate"));// Maps hire date to employeeHireDates column
         employeeSalaries.setCellValueFactory(new PropertyValueFactory<>("salary"));   // Maps salary to employeeSalaries column
 
         // Populate the search criteria dropdown with options
         searchCriteriaChoiceBox.setItems(FXCollections.observableArrayList("Name", "SSN", "empID"));
 
         // Load all employee data from the database and display it in the table
         loadEmployeeData();
     }
 
     /*
      * Opens a new window for adding an employee.
      * Loads the add_employee.fxml view and opens it as a modal window,
      * which means it stays on top until the user closes it.
      */
     @FXML
     private void handleAddEmployee() {
         try {
             // Load the add employee view from the FXML file
             Parent root = FXMLLoader.load(getClass().getResource("/view/add_employee.fxml"));
             
             // Create a new window (Stage) for the add employee view
             Stage stage = new Stage();
             stage.setTitle("Add New Employee"); // Set the title of the new window
             stage.initModality(Modality.WINDOW_MODAL); // Makes the new window modal (blocks interaction with other windows)
             stage.initOwner(App.getPrimaryStage()); // Sets the main application window as the owner
             
             // Set up the scene with the loaded FXML and display the window
             stage.setScene(new Scene(root));
             stage.showAndWait(); // Wait until this window is closed before returning to the main application
         } catch (IOException e) {
             e.printStackTrace(); // Print the error if loading the FXML file fails
         }
     }
 
     /*
      * Loads all employees from the database and displays them in the table.
      * This method retrieves data using EmployeeDAO, then adds the data to the ObservableList.
      */
     private void loadEmployeeData() {
         // Get the list of all employees from the database
         List<Employee> employees = employeeDAO.getAllEmployees();
         
         // Convert the list to an ObservableList, which is required for TableView
         employeeData = FXCollections.observableArrayList(employees);
         
         // Set the table's items to the ObservableList, which updates the table display
         employeeTable.setItems(employeeData);
     }
 }
 