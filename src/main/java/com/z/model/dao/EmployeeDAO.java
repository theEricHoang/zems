package com.z.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
