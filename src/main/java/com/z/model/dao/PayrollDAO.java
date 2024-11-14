package com.z.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.z.model.Payroll;
import com.z.service.DatabaseService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PayrollDAO {
    
// Update the salary of employees based on a salary range
public static void updateEmployeeSalaryByRange(double percentageIncrease, double minSalary, double maxSalary, Connection conn) throws SQLException {
    // Calculate the increase factor (e.g., 3.2% -> 1.032)
    double increaseFactor = 1 + (percentageIncrease / 100);

    // SQL query to update salaries within a specific range
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

// Get the total salary by job title
public static double getJobTitlePay(Connection conn, String jobTitle) throws SQLException {
    double totalSalary = 0;
    String query = "SELECT SUM(e.salary) FROM employees e WHERE e.jobTitle = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, jobTitle);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                totalSalary = rs.getDouble(1);  // Retrieve the total salary for the job title
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error retrieving total salary by job title.", e);
    }
    return totalSalary;
}

// Get the total salary by division
public static double getDivisionPay(Connection conn, String division) throws SQLException {
    double totalSalary = 0;
    String query = "SELECT SUM(e.salary) FROM employees e WHERE e.division = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, division);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                totalSalary = rs.getDouble(1);  // Retrieve the total salary for the division
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error retrieving total salary by division.", e);
    }
    return totalSalary;
}

// Get Payroll information for an employee by empID
public static Payroll getPayrollInfoByEmpID(int empID, Connection conn) throws SQLException {
    Payroll payroll = null;
    String query = "SELECT CONCAT(Fname, ' ', Lname) AS name, jt.job_title AS title, e.email, e.empid, "
                    + "p.pay_date, p.earnings, p.fed_tax, p.fed_med, p.fed_SS, p.state_tax, p.retire_401k, p.health_care "
                    + "FROM employees e "
                    + "JOIN payroll p ON e.empid = p.empid "
                    + "JOIN employee_job_titles ejt ON e.empid = ejt.empid "
                    + "JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id "
                    + "WHERE e.empid = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, empID);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                payroll = new Payroll(
                    rs.getString("name"),
                    rs.getString("title"),
                    rs.getString("email"),
                    rs.getInt("empid"),
                    rs.getString("pay_date"),
                    rs.getInt("earnings"),
                    rs.getInt("fed_tax"),
                    rs.getInt("fed_med"),
                    rs.getInt("fed_SS"),
                    rs.getInt("state_tax"),
                    rs.getInt("retire_401k"),
                    rs.getInt("health_care")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error retrieving payroll information.", e);
    }
    return payroll;
}

//get all the employee payroll infor default for payroll page
public static ObservableList<Payroll> getAllPayrolls() throws SQLException {
    ObservableList<Payroll> payrolls = FXCollections.observableArrayList();
    String query = "SELECT CONCAT(e.Fname, ' ', e.Lname) AS name, e.email, p.empid, p.pay_date, p.earnings, p.fed_tax, p.fed_med, p.fed_SS, p.state_tax, p.retire_401k, p.health_care, jt.job_title AS title " +
                    "FROM payroll p " +
                    "LEFT JOIN employees e ON e.empid = p.empid " +
                    "LEFT JOIN employee_job_titles ejt ON ejt.empid = p.empid " +
                    "LEFT JOIN job_titles jt ON jt.job_title_id = ejt.job_title_id;";

    try (Connection conn = DatabaseService.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            String _name = rs.getString("name");
            String _title = rs.getString("title");
            String _email = rs.getString("email");
            int _empID = rs.getInt("empid");
            String _payDate = rs.getString("pay_date");
            double _gross = rs.getDouble("earnings");
            double _federal = rs.getDouble("fed_tax");
            double _fedMed = rs.getDouble("fed_med");
            double _fedSS = rs.getDouble("fed_SS");
            double _state = rs.getDouble("state_tax");
            double _emp401K = rs.getDouble("retire_401k");
            double _healthCare = rs.getDouble("health_care");

            // Create Payroll object using data from ResultSet
            Payroll payroll = new Payroll(_name, _title, _email, _empID, _payDate, _gross, _federal, _fedMed, _fedSS, _state, _emp401K, _healthCare);
            
            payrolls.add(payroll);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return payrolls;
}

}
