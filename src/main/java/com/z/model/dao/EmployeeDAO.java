/*
 * This class, EmployeeDAO, is responsible for managing database operations
 * related to employees. It provides methods to add, search, delete, and
 * retrieve employees from a database. It interacts with the database
 * through SQL queries and maps the data to Employee objects for use in
 * the application.
 */

 package com.z.model.dao;

 import com.z.model.Employee;
 
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.List;
 
 public class EmployeeDAO {
     // This connection object allows us to connect to and interact with the database.
     private Connection connection;
 
     // Constructor that takes a database connection as a parameter and saves it for later use.
     public EmployeeDAO(Connection connection) {
         this.connection = connection;
     }
 
     /*
      * Adds a new employee to the database.
      * Uses an SQL INSERT statement to add a row to the employees table.
      * Returns true if the employee was successfully added, otherwise false.
      */
     public boolean addEmployee(Employee employee) {
         String query = "INSERT INTO employees (empID, name, ssn /*, other columns */) VALUES (?, ?, ? /*, other values */)";
         
         // We use a PreparedStatement to safely set the values and prevent SQL injection.
         try (PreparedStatement statement = connection.prepareStatement(query)) {
             statement.setInt(1, employee.getEmpID());  // Sets the employee ID value.
             statement.setString(2, employee.getName()); // Sets the employee's name.
             statement.setString(3, employee.getSSN());  // Sets the employee's SSN.
             // Additional fields could be set here if needed.
             
             int rowsAffected = statement.executeUpdate(); // Executes the query.
             return rowsAffected > 0; // Returns true if at least one row was added.
         } catch (SQLException e) {
             e.printStackTrace(); // If an error occurs, it prints the error details.
             return false; // If there's an error, returns false to indicate failure.
         }
     }
 
     /*
      * Searches for a single employee by SSN.
      * This method returns an Employee object if an employee with the specified SSN is found.
      * If no matching employee is found, it returns null.
      */
     public Employee searchEmployeeBySSN(String ssn) {
         String query = "SELECT * FROM employees WHERE ssn = ?"; // SQL to find an employee by SSN.
         
         try (PreparedStatement statement = connection.prepareStatement(query)) {
             statement.setString(1, ssn); // Sets the SSN parameter in the SQL query.
             ResultSet resultSet = statement.executeQuery(); // Executes the query and gets the result.
 
             if (resultSet.next()) { // If a result is found, map it to an Employee object.
                 return mapRowToEmployee(resultSet); // Convert the row data into an Employee object.
             }
         } catch (SQLException e) {
             e.printStackTrace(); // Prints the error if something goes wrong.
         }
         
         return null; // If no employee is found, returns null.
     }
 
     /*
      * Searches for a single employee by employee ID.
      * This method returns an Employee object if an employee with the specified ID is found.
      * If no matching employee is found, it returns null.
      */
     public Employee searchEmployeeByEmpID(int empID) {
         String query = "SELECT * FROM employees WHERE empID = ?"; // SQL to find an employee by ID.
 
         try (PreparedStatement statement = connection.prepareStatement(query)) {
             statement.setInt(1, empID); // Sets the employee ID parameter in the SQL query.
             ResultSet resultSet = statement.executeQuery(); // Executes the query and gets the result.
 
             if (resultSet.next()) { // If a result is found, map it to an Employee object.
                 return mapRowToEmployee(resultSet); // Convert the row data into an Employee object.
             }
         } catch (SQLException e) {
             e.printStackTrace(); // Prints the error if something goes wrong.
         }
 
         return null; // If no employee is found, returns null.
     }
 
     /*
      * Searches for employees by name (partial or full match).
      * This method returns a list of Employee objects with names that match the search term.
      * If no employees are found, it returns an empty list.
      */
     public List<Employee> searchEmployeeByName(String name) {
         List<Employee> employees = new ArrayList<>(); // List to store the search results.
         String query = "SELECT * FROM employees WHERE name LIKE ?"; // SQL query with LIKE for partial matches.
         
         try (PreparedStatement statement = connection.prepareStatement(query)) {
             statement.setString(1, "%" + name + "%"); // Sets the name parameter with wildcard characters.
             ResultSet resultSet = statement.executeQuery(); // Executes the query and gets the result.
 
             // Loops through each result row and converts it to an Employee object.
             while (resultSet.next()) {
                 Employee employee = mapRowToEmployee(resultSet);
                 employees.add(employee); // Adds each Employee to the list.
             }
         } catch (SQLException e) {
             e.printStackTrace(); // Prints the error if something goes wrong.
         }
         
         return employees; // Returns the list of matching employees.
     }
 
     /*
      * Deletes an employee from the database using their employee ID.
      * Uses an SQL DELETE statement to remove the row from the employees table.
      * Returns true if an employee was successfully deleted, otherwise false.
      */
     public boolean deleteEmployee(int empID) {
         String query = "DELETE FROM employees WHERE empID = ?"; // SQL to delete an employee by ID.
         
         try (PreparedStatement statement = connection.prepareStatement(query)) {
             statement.setInt(1, empID); // Sets the employee ID parameter in the SQL query.
             
             int rowsAffected = statement.executeUpdate(); // Executes the query.
             return rowsAffected > 0; // Returns true if at least one row was deleted.
         } catch (SQLException e) {
             e.printStackTrace(); // Prints the error if something goes wrong.
             return false; // If there's an error, returns false to indicate failure.
         }
     }
 
     /*
      * Retrieves all employees from the database.
      * This method returns a list of all employees in the employees table.
      * If there are no employees, it returns an empty list.
      */
     public List<Employee> getAllEmployees() {
         List<Employee> employees = new ArrayList<>(); // List to store all employees.
         String query = "SELECT * FROM employees"; // SQL to select all employees.
         
         try (PreparedStatement statement = connection.prepareStatement(query);
              ResultSet resultSet = statement.executeQuery()) {
             
             // Loops through each result row and converts it to an Employee object.
             while (resultSet.next()) {
                 Employee employee = mapRowToEmployee(resultSet);
                 employees.add(employee); // Adds each Employee to the list.
             }
         } catch (SQLException e) {
             e.printStackTrace(); // Prints the error if something goes wrong.
         }
         
         return employees; // Returns the list of all employees.
     }
 
     /*
      * Helper method that converts a ResultSet row into an Employee object.
      * Takes a single row from the ResultSet and maps it to the Employee fields.
      * This method is used internally in the class.
      */
     private Employee mapRowToEmployee(ResultSet resultSet) throws SQLException {
         Employee employee = new Employee(); // Creates a new Employee object.
         employee.setEmpID(resultSet.getInt("empID")); // Sets the employee ID.
         employee.setName(resultSet.getString("name")); // Sets the employee's name.
         employee.setSSN(resultSet.getString("ssn")); // Sets the employee's SSN.
         // Other fields can be set here as needed.
         return employee; // Returns the mapped Employee object.
     }
 }
 