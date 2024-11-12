/*
 * This class, EmployeeDAO, is responsible for managing database operations
 * related to employees. It provides methods to add, search, delete, and
 * retrieve employees from a database. It interacts with the database
 * through SQL queries and maps the data to Employee objects for use in
 * the application.
 */

package com.z.model.dao;

import com.z.model.Employee;
import com.z.service.DatabaseService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class EmployeeDAO {

    public static ObservableList<Employee> getAllEmployees() throws SQLException {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT * FROM employees e " + 
                        "LEFT JOIN employee_job_titles ejt ON e.empid = ejt.empid " + 
                        "LEFT JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
                        "LEFT JOIN employee_division ed ON e.empid = ed.empid " +
                        "LEFT JOIN division d ON ed.div_ID = d.ID;";

        try (Connection conn = DatabaseService.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int _divID = rs.getInt("div_ID");
                String _division = rs.getString("Name");
                String _jobTitle = rs.getString("job_title");
                int _empID = rs.getInt("empid");
                String _fName = rs.getString("Fname");
                String _lName = rs.getString("Lname");
                String _email = rs.getString("email");
                String _hireDate = rs.getString("HireDate");
                double _salary = rs.getDouble("Salary");
                String _ssn = rs.getString("SSN");

                Employee employee = new Employee(false, _divID, _fName, _lName, _email, _ssn, _hireDate, _division, _jobTitle, _salary);
                employee.setEmpID(_empID);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static void addEmployee(Employee employee) throws SQLException {
        String insertEmployeeQuery = "INSERT INTO employees (empid, Fname, Lname, email, HireDate, Salary, SSN) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertDivisionQuery = "INSERT INTO employee_division (empid, div_ID) VALUES (?, ?)";
        String insertTitleQuery = "INSERT INTO employee_job_titles (empid, job_title_id) VALUES (?, ?)";

        try (Connection conn = DatabaseService.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtEmployee = conn.prepareStatement(insertEmployeeQuery);
                 PreparedStatement pstmtDivision = conn.prepareStatement(insertDivisionQuery);
                 PreparedStatement pstmtTitle = conn.prepareStatement(insertTitleQuery)) {
                pstmtEmployee.setInt(1, employee.getEmpID());
                pstmtEmployee.setString(2, employee.getFName());
                pstmtEmployee.setString(3, employee.getLName());
                pstmtEmployee.setString(4, employee.getEmail());
                pstmtEmployee.setDate(5, java.sql.Date.valueOf(employee.getHireDate()));
                pstmtEmployee.setDouble(6, employee.getSalary());
                pstmtEmployee.setString(7, employee.getSSN());
                pstmtEmployee.executeUpdate();

                pstmtDivision.setInt(1, employee.getEmpID());
                pstmtDivision.setInt(2, employee.getDivID());
                pstmtDivision.executeUpdate();

                int titleID = DatabaseService.fetchTitleID(employee.getJobTitle());
                pstmtTitle.setInt(1, employee.getEmpID());
                pstmtTitle.setInt(2, titleID);
                pstmtTitle.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(int empID) throws SQLException {
        String deleteEmployeeQuery = "DELETE FROM employees WHERE empid = ?";
        String deleteDivisionQuery = "DELETE FROM employee_division WHERE empid = ?";
        String deleteTitleQuery = "DELETE FROM employee_job_titles WHERE empid = ?";

        try (Connection conn = DatabaseService.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtEmployee = conn.prepareStatement(deleteEmployeeQuery);
                 PreparedStatement pstmtDivision = conn.prepareStatement(deleteDivisionQuery);
                 PreparedStatement pstmtTitle = conn.prepareStatement(deleteTitleQuery)) {
                pstmtEmployee.setInt(1, empID);
                pstmtEmployee.executeUpdate();

                pstmtDivision.setInt(1, empID);
                pstmtDivision.executeUpdate();

                pstmtTitle.setInt(1, empID);
                pstmtTitle.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEmployee(Employee employee) throws SQLException
    {
        String updateEmployeeQuery = "UPDATE employees SET Fname = ?, Lname = ?, email = ?, HireDate = ?, Salary = ?, SSN = ? WHERE empid = ?";
        String updateDivisionQuery = "UPDATE employee_division SET div_ID = ? WHERE empid = ?";
        String updateTitleQuery = "UPDATE employee_job_titles SET job_title_id = ? WHERE empid = ?";

        try (Connection conn = DatabaseService.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtEmployee = conn.prepareStatement(updateEmployeeQuery);
                 PreparedStatement pstmtDivision = conn.prepareStatement(updateDivisionQuery);
                 PreparedStatement pstmtTitle = conn.prepareStatement(updateTitleQuery)) {
                pstmtEmployee.setString(1, employee.getFName());
                pstmtEmployee.setString(2, employee.getLName());
                pstmtEmployee.setString(3, employee.getEmail());
                pstmtEmployee.setDate(4, java.sql.Date.valueOf(employee.getHireDate()));
                pstmtEmployee.setDouble(5, employee.getSalary());
                pstmtEmployee.setString(6, employee.getSSN());
                pstmtEmployee.setInt(7, employee.getEmpID());
                pstmtEmployee.executeUpdate();

                pstmtDivision.setInt(1, employee.getDivID());
                pstmtDivision.setInt(2, employee.getEmpID());
                pstmtDivision.executeUpdate();

                int titleID = DatabaseService.fetchTitleID(employee.getJobTitle());
                pstmtTitle.setInt(1, titleID);
                pstmtTitle.setInt(2, employee.getEmpID());
                pstmtTitle.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
 
}
