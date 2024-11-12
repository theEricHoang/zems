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

    public static StringBuilder getPayStatementHistory(int empID, Connection conn) throws SQLException {
        StringBuilder output = new StringBuilder();
        String query = "SELECT p.empid, p.pay_date, p.earnings, p.fed_tax, p.fed_med, p.fed_SS, " +
                       "p.state_tax, p.retire_401k, p.health_care " +
                       "FROM payroll p " +
                       "WHERE p.empid = ? " +
                       "ORDER BY p.pay_date;";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, empID);
            try (ResultSet rs = pstmt.executeQuery()) {
                output.append("\tEMP ID\tPAY DATE\tGROSS\tFederal\tFedMed\tFedSS\tState\t401K\tHealthCare\n");
    
                while (rs.next()) {
                    output.append("\t").append(rs.getInt("empid")).append("\t");
                    output.append(rs.getDate("pay_date")).append("\t").append(rs.getDouble("earnings")).append("\t");
                    output.append(rs.getDouble("fed_tax")).append("\t").append(rs.getDouble("fed_med")).append("\t");
                    output.append(rs.getDouble("fed_SS")).append("\t");
                    output.append(rs.getDouble("state_tax")).append("\t");
                    output.append(rs.getDouble("retire_401k")).append("\t");
                    output.append(rs.getDouble("health_care")).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving pay statement history.", e);
        }
        return output;
    }

    public static StringBuilder getTotalPayByMonthJobTitle(int month, int year, Connection conn) throws SQLException {
        StringBuilder output = new StringBuilder();
        String query = "SELECT jt.job_title, SUM(p.earnings) AS total_earnings " +
                       "FROM payroll p " +
                       "JOIN employee_job_titles ejt ON p.empid = ejt.empid " +
                       "JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
                       "WHERE MONTH(p.pay_date) = ? AND YEAR(p.pay_date) = ? " +
                       "GROUP BY jt.job_title " +
                       "ORDER BY jt.job_title;";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                output.append("JOB TITLE\tTOTAL PAY\n");
                while (rs.next()) {
                    output.append(rs.getString("job_title")).append("\t");
                    output.append(rs.getDouble("total_earnings")).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving total pay by job title.", e);
        }
        return output;
    }

    public static StringBuilder getTotalPayByMonthDivision(int month, int year, Connection conn) throws SQLException {
        StringBuilder output = new StringBuilder();
        String query = "SELECT d.Name AS division_name, SUM(p.earnings) AS total_earnings " +
                       "FROM payroll p " +
                       "JOIN employee_division ed ON p.empid = ed.empid " +
                       "JOIN division d ON ed.div_ID = d.ID " +
                       "WHERE MONTH(p.pay_date) = ? AND YEAR(p.pay_date) = ? " +
                       "GROUP BY d.Name " +
                       "ORDER BY d.Name;";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                output.append("DIVISION\tTOTAL PAY\n");
                while (rs.next()) {
                    output.append(rs.getString("division_name")).append("\t");
                    output.append(rs.getDouble("total_earnings")).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving total pay by division.", e);
        }
        return output;
    }
    

    public static void updateEmployeeSalaryByRange(double percentageIncrease, double minSalary, double maxSalary, Connection conn) throws SQLException {
        // Calculate the increase factor (e.g., 3.2% -> 1.032)
        double increaseFactor = 1 + (percentageIncrease / 100);
    
        String updateSalaryQuery = "UPDATE employees SET Salary = Salary * ? WHERE Salary >= ? AND Salary < ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(updateSalaryQuery)) {
            pstmt.setDouble(1, increaseFactor);  // Set the increase factor (e.g., 1.032 for 3.2%)
            pstmt.setDouble(2, minSalary);       // Set the minimum salary limit (e.g., 58000)
            pstmt.setDouble(3, maxSalary);       // Set the maximum salary limit (e.g., 105000)
    
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " employee(s) salary updated.");
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating employee salaries.", e);
        }
    }


}
