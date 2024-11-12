/*
 * This class, SearchEmployeeController, allows the user to search for employees
 * in the database by entering search criteria (like SSN, Employee ID, or Name).
 * It interacts with the database through the EmployeeDAO class to find matching
 * employees and display them in a table view. It also provides feedback to the
 * user if no results are found or if invalid input is provided.
 */

 package com.z.controller;

 import com.z.model.Employee;
 import com.z.model.dao.EmployeeDAO;
 import com.z.service.Validation;
 import javafx.collections.FXCollections;
 import javafx.collections.ObservableList;
 import javafx.fxml.FXML;
 import javafx.scene.control.Alert;
 import javafx.scene.control.ChoiceBox;
 import javafx.scene.control.TableView;
 import javafx.scene.control.TextField;
 
 import java.sql.Connection;
 import java.util.List;
 
 public class SearchEmployeeController {
     // TextField where the user enters the search term (SSN, empID, or Name)
     @FXML private TextField searchField;
 
     // Dropdown menu for selecting the search criterion (SSN, empID, or Name)
     @FXML private ChoiceBox<String> searchCriteriaChoiceBox;
 
     // TableView that displays the search results (Employee objects)
     @FXML private TableView<Employee> employeeTable;
 
     // EmployeeDAO object for database interactions
     private EmployeeDAO employeeDAO;
 
     // Constructor that initializes EmployeeDAO with a database connection
     public SearchEmployeeController(Connection connection) {
         this.employeeDAO = new EmployeeDAO(connection); // Passes the connection to EmployeeDAO
     }
 
     /*
      * This initialize() method sets up the dropdown menu with search options
      * (SSN, empID, Name) so that the user can select a criterion before searching.
      * It is called automatically when the controller is loaded.
      */
     @FXML
     public void initialize() {
         // Populates the ChoiceBox with available search criteria
         searchCriteriaChoiceBox.setItems(FXCollections.observableArrayList("SSN", "empID", "Name"));
     }
 
     /*
      * This method, handleSearch(), performs the search when the user initiates it.
      * It checks the selected search criterion and the input value, validates them,
      * and uses EmployeeDAO to find matching employees. If found, it displays them
      * in the table. Otherwise, it shows an alert with relevant feedback.
      */
     @FXML
     private void handleSearch() {
         // Gets the selected search criterion (SSN, empID, or Name) and the input search value
         String criteria = searchCriteriaChoiceBox.getValue();
         String searchValue = searchField.getText();
 
         // ObservableList to store and display the search results
         ObservableList<Employee> results = FXCollections.observableArrayList();
 
         // Checks if both the search criterion and search term have been entered
         if (criteria == null || searchValue.isEmpty()) {
             showAlert("Please select a search criterion and enter a search term.");
             return; // Stops the method if any of these fields are empty
         }
 
         // Determines the search action based on the selected search criterion
         switch (criteria) {
             case "SSN":
                 // Validates the SSN format using the Validation class
                 if (!Validation.isValidSSN(searchValue)) {
                     showAlert("Invalid SSN. Please enter a valid SSN.");
                     return;
                 }
                 // Calls EmployeeDAO to search for an employee by SSN
                 Employee employeeBySSN = employeeDAO.searchEmployeeBySSN(searchValue);
                 
                 // Checks if an employee was found and adds it to the results
                 if (employeeBySSN != null) {
                     results.add(employeeBySSN);
                 } else {
                     showAlert("No employee found with the specified SSN.");
                 }
                 break;
 
             case "empID":
                 // Validates the Employee ID format using the Validation class
                 if (!Validation.isValidEmpID(searchValue)) {
                     showAlert("Invalid Employee ID. Please enter a valid ID.");
                     return;
                 }
                 try {
                     // Converts the search term to an integer for Employee ID
                     int empID = Integer.parseInt(searchValue);
                     // Calls EmployeeDAO to search for an employee by empID
                     Employee employeeByID = employeeDAO.searchEmployeeByEmpID(empID);
 
                     // Checks if an employee was found and adds it to the results
                     if (employeeByID != null) {
                         results.add(employeeByID);
                     } else {
                         showAlert("No employee found with the specified Employee ID.");
                     }
                 } catch (NumberFormatException e) {
                     // Catches any error if the Employee ID is not a valid integer
                     showAlert("Invalid empID format. Please enter a numeric value.");
                 }
                 break;
 
             case "Name":
                 // Calls EmployeeDAO to search for employees by name (can be partial matches)
                 List<Employee> employeesByName = employeeDAO.searchEmployeeByName(searchValue);
 
                 // Checks if any employees were found and adds them to the results
                 if (!employeesByName.isEmpty()) {
                     results.addAll(employeesByName);
                 } else {
                     showAlert("No employees found with the specified name.");
                 }
                 break;
 
             default:
                 // If the criterion is unrecognized, shows an error message
                 showAlert("Invalid search criterion selected.");
                 return;
         }
 
         // Sets the results in the table to display them to the user
         employeeTable.setItems(results);
     }
 
     /*
      * Helper method to display an alert dialog with a given message.
      * This is used to provide feedback to the user, such as notifying them
      * if no employees were found or if an input is invalid.
      */
     private void showAlert(String message) {
         Alert alert = new Alert(Alert.AlertType.INFORMATION); // Creates a new alert dialog of type "Information"
         alert.setTitle("Search Result");                      // Sets the title of the alert window
         alert.setHeaderText(null);                            // Removes the header text
         alert.setContentText(message);                        // Sets the main message text
         alert.showAndWait();                                  // Displays the alert and waits until the user closes it
     }
 }
 