/*
 * This class, EmployeeDAO, is responsible for managing database operations
 * related to employees. It provides methods to add, search, delete, and
 * retrieve employees from a database. It interacts with the database
 * through SQL queries and maps the data to Employee objects for use in
 * the application.
 */

package com.z.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.z.model.Address;
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
                        "LEFT JOIN division d ON ed.div_ID = d.ID " +
                        "LEFT JOIN address a ON e.empid = a.empid;";

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

                String _gender = rs.getString("gender");
                String _pronouns = rs.getString("pronouns");
                String _race = rs.getString("race");
                LocalDate _dob = rs.getDate("DOB").toLocalDate();
                String _phone = rs.getString("phone");
                int _cityID = rs.getInt("city_id");
                int _stateID = rs.getInt("state_id");

                Address _address = new Address(_empID, _gender, _pronouns, _race, _dob, _phone, _cityID, _stateID);
                Employee employee = new Employee(false, _divID, _fName, _lName, _email, _ssn, _hireDate, _division, _jobTitle, _salary, _address);
                employee.setEmpID(_empID);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static boolean addEmployee(Employee employee) throws SQLException {
        String insertEmployeeQuery = "INSERT INTO employees (empid, Fname, Lname, email, HireDate, Salary, SSN) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertDivisionQuery = "INSERT INTO employee_division (empid, div_ID) VALUES (?, ?)";
        String insertTitleQuery = "INSERT INTO employee_job_titles (empid, job_title_id) VALUES (?, ?)";
        String insertAddressQuery = "INSERT INTO address (empid, city_id, state_id, gender, pronouns, race, DOB, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseService.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtEmployee = conn.prepareStatement(insertEmployeeQuery);
                 PreparedStatement pstmtDivision = conn.prepareStatement(insertDivisionQuery);
                 PreparedStatement pstmtTitle = conn.prepareStatement(insertTitleQuery);
                 PreparedStatement pstmtAddress = conn.prepareStatement(insertAddressQuery)) {
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

                pstmtAddress.setInt(1, employee.getEmpID());
                pstmtAddress.setInt(2, employee.getAddress().getCityID());
                pstmtAddress.setInt(3, employee.getAddress().getStateID());
                pstmtAddress.setString(4, employee.getAddress().getGender());
                pstmtAddress.setString(5, employee.getAddress().getPronouns());
                pstmtAddress.setString(6, employee.getAddress().getRace());
                pstmtAddress.setDate(7, java.sql.Date.valueOf(employee.getAddress().getDob()));
                pstmtAddress.setString(8, employee.getAddress().getPhone());
                pstmtAddress.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void deleteEmployee(int empID) throws SQLException {
        String deleteEmployeeQuery = "DELETE FROM employees WHERE empid = ?";
        String deleteDivisionQuery = "DELETE FROM employee_division WHERE empid = ?";
        String deleteTitleQuery = "DELETE FROM employee_job_titles WHERE empid = ?";
        String deleteAddressQuery = "DELETE FROM address WHERE empid = ?";

        try (Connection conn = DatabaseService.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtEmployee = conn.prepareStatement(deleteEmployeeQuery);
                 PreparedStatement pstmtDivision = conn.prepareStatement(deleteDivisionQuery);
                 PreparedStatement pstmtTitle = conn.prepareStatement(deleteTitleQuery);
                 PreparedStatement pstmtAddress = conn.prepareStatement(deleteAddressQuery)) {
                pstmtAddress.setInt(1, empID);
                pstmtAddress.executeUpdate();
                
                pstmtTitle.setInt(1, empID);
                pstmtTitle.executeUpdate();

                pstmtDivision.setInt(1, empID);
                pstmtDivision.executeUpdate();

                pstmtEmployee.setInt(1, empID);
                pstmtEmployee.executeUpdate();

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
        String updateAddressQuery = "UPDATE address SET city_id = ?, state_id = ?, gender = ?, pronouns = ?, race = ?, DOB = ?, phone = ? WHERE empid = ?";

        try (Connection conn = DatabaseService.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtEmployee = conn.prepareStatement(updateEmployeeQuery);
                 PreparedStatement pstmtDivision = conn.prepareStatement(updateDivisionQuery);
                 PreparedStatement pstmtTitle = conn.prepareStatement(updateTitleQuery);
                 PreparedStatement pstmtAddress = conn.prepareStatement(updateAddressQuery)) {
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

                pstmtAddress.setInt(1, employee.getAddress().getCityID());
                pstmtAddress.setInt(2, employee.getAddress().getStateID());
                pstmtAddress.setString(3, employee.getAddress().getGender());
                pstmtAddress.setString(4, employee.getAddress().getPronouns());
                pstmtAddress.setString(5, employee.getAddress().getRace());
                pstmtAddress.setDate(6, java.sql.Date.valueOf(employee.getAddress().getDob()));
                pstmtAddress.setString(7, employee.getAddress().getPhone());
                pstmtAddress.setInt(8, employee.getEmpID());
                pstmtAddress.executeUpdate();

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
    public static Employee searchEmployeeBySSN(String ssn) {
        String query = "SELECT * FROM employees e " + 
            "LEFT JOIN employee_job_titles ejt ON e.empid = ejt.empid " + 
            "LEFT JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
            "LEFT JOIN employee_division ed ON e.empid = ed.empid " +
            "LEFT JOIN division d ON ed.div_ID = d.ID " +
            "LEFT JOIN address a ON e.empid = a.empid " +
            "WHERE e.SSN = ?";
         
        try (Connection connection = DatabaseService.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query)) {
           stmt.setString(1, ssn);
           try (ResultSet rs = stmt.executeQuery()) {
               if (rs.next()) {
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

                    String _gender = rs.getString("gender");
                    String _pronouns = rs.getString("pronouns");
                    String _race = rs.getString("race");
                    LocalDate _dob = rs.getDate("DOB").toLocalDate();
                    String _phone = rs.getString("phone");
                    int _cityID = rs.getInt("city_id");
                    int _stateID = rs.getInt("state_id");
    
                    Address _address = new Address(_empID, _gender, _pronouns, _race, _dob, _phone, _cityID, _stateID);
                    Employee employee = new Employee(false, _divID, _fName, _lName, _email, _ssn, _hireDate, _division, _jobTitle, _salary, _address);
                    employee.setEmpID(_empID);
                    return employee;
               }
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
    public static Employee searchEmployeeByEmpID(int empID) {
        String query = "SELECT * FROM employees e " + 
            "LEFT JOIN employee_job_titles ejt ON e.empid = ejt.empid " + 
            "LEFT JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
            "LEFT JOIN employee_division ed ON e.empid = ed.empid " +
            "LEFT JOIN division d ON ed.div_ID = d.ID " +
            "LEFT JOIN address a ON e.empid = a.empid " +
            "WHERE e.empid = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, empID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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

                    String _gender = rs.getString("gender");
                    String _pronouns = rs.getString("pronouns");
                    String _race = rs.getString("race");
                    LocalDate _dob = rs.getDate("DOB").toLocalDate();
                    String _phone = rs.getString("phone");
                    int _cityID = rs.getInt("city_id");
                    int _stateID = rs.getInt("state_id");
    
                    Address _address = new Address(_empID, _gender, _pronouns, _race, _dob, _phone, _cityID, _stateID);
                    Employee employee = new Employee(false, _divID, _fName, _lName, _email, _ssn, _hireDate, _division, _jobTitle, _salary, _address);
                    employee.setEmpID(_empID);
                    return employee;
                }
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
    public static ObservableList<Employee> searchEmployeeByName(String name) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        
        String[] nameParts = name.trim().split(" ");
        String fName = nameParts[0];
        String lName = nameParts.length > 1 ? nameParts[1] : "";

        String query = "SELECT * FROM employees e " + 
                        "LEFT JOIN employee_job_titles ejt ON e.empid = ejt.empid " + 
                        "LEFT JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
                        "LEFT JOIN employee_division ed ON e.empid = ed.empid " +
                        "LEFT JOIN division d ON ed.div_ID = d.ID " +
                        "LEFT JOIN address a ON e.empid = a.empid " +
                        "WHERE (e.FName LIKE ? AND e.LName LIKE ?) " +
                        "OR e.FName LIKE ? " +
                        "OR e.LName LIKE ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            if (lName.isEmpty()) {
                stmt.setString(1, "%" + fName + "%");
                stmt.setString(2, "%" + fName + "%");
                stmt.setString(3, "%" + fName + "%");
                stmt.setString(4, "%" + fName + "%");
            } else {
                stmt.setString(1, "%" + fName + "%");
                stmt.setString(2, "%" + lName + "%");
                stmt.setString(3, "%" + fName + "%");
                stmt.setString(4, "%" + lName + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
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

                    String _gender = rs.getString("gender");
                    String _pronouns = rs.getString("pronouns");
                    String _race = rs.getString("race");
                    LocalDate _dob = rs.getDate("DOB").toLocalDate();
                    String _phone = rs.getString("phone");
                    int _cityID = rs.getInt("city_id");
                    int _stateID = rs.getInt("state_id");
    
                    Address _address = new Address(_empID, _gender, _pronouns, _race, _dob, _phone, _cityID, _stateID);
                    Employee employee = new Employee(false, _divID, _fName, _lName, _email, _ssn, _hireDate, _division, _jobTitle, _salary, _address);
                    employee.setEmpID(_empID);
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Prints the error if something goes wrong.
        }
         
        return employees; // Returns the list of matching employees.
    }
 
}
