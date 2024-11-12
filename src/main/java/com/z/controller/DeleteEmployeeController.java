/*
 * This class, DeleteEmployeeController, allows the user to delete an employee
 * from the database by entering the employee's ID. It connects to the database
 * through the EmployeeDAO class and displays messages to inform the user if the
 * deletion was successful or if there was an error.
 */

 package com.z.controller;

 import com.z.model.dao.EmployeeDAO;
 import javafx.fxml.FXML;
 import javafx.scene.control.Alert;
 import javafx.scene.control.TextField;
 
 import java.sql.Connection;
 
 public class DeleteEmployeeController {
     // TextField where the user can enter the Employee ID to delete
     @FXML private TextField empIDField;
 
     // EmployeeDAO instance to manage database operations
     private EmployeeDAO employeeDAO;
 
     // Constructor that takes a Connection parameter to initialize EmployeeDAO
     public DeleteEmployeeController(Connection connection) {
         this.employeeDAO = new EmployeeDAO(connection); // Creates EmployeeDAO using the database connection
     }
 
     /*
      * This method handles the delete action. It retrieves the Employee ID
      * from the TextField, verifies it, and tries to delete the employee.
      * If successful, it shows a success message. If the ID is invalid or
      * no employee is found, it shows an appropriate message to the user.
      */
     @FXML
     private void handleDelete() {
         // Gets the text entered by the user for the Employee ID
         String empIDText = empIDField.getText();
         
         // Checks if the Employee ID field is empty
         if (empIDText.isEmpty()) {
             showAlert("Please enter an Employee ID to delete."); // Shows an alert if the field is empty
             return; // Exits the method if no ID was entered
         }
 
         try {
             // Converts the Employee ID from text to an integer
             int empID = Integer.parseInt(empIDText);
             
             // Calls deleteEmployee on EmployeeDAO to attempt deletion in the database
             if (employeeDAO.deleteEmployee(empID)) {
                 // Shows success message if deletion was successful
                 showAlert("Employee deleted successfully.");
             } else {
                 // Shows a message if no employee was found with the specified ID
                 showAlert("No employee found with the specified ID.");
             }
         } catch (NumberFormatException e) {
             // If the Employee ID is not a valid number, shows an error message
             showAlert("Invalid Employee ID. Please enter a valid number.");
         }
     }
 
     /*
      * This method shows an alert dialog with a given message.
      * Used to inform the user about the result of the delete action
      * or any errors that occur during the process.
      */
     private void showAlert(String message) {
         Alert alert = new Alert(Alert.AlertType.INFORMATION); // Creates a new alert dialog of type "Information"
         alert.setTitle("Delete Result");                      // Sets the title of the alert window
         alert.setHeaderText(null);                            // Removes the header text
         alert.setContentText(message);                        // Sets the main message text
         alert.showAndWait();                                  // Displays the alert and waits until the user closes it
     }
 }
 