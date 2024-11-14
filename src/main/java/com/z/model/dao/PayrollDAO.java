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
    String query = "SELECT * FROM payroll WHERE empID = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, empID);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                payroll = new Payroll(
                    rs.getInt("empID"),
                    rs.getInt("payDate"),
                    rs.getInt("gross"),
                    rs.getInt("federal"),
                    rs.getInt("fedMed"),
                    rs.getInt("fedSS"),
                    rs.getInt("state"),
                    rs.getInt("emp401K"),
                    rs.getInt("healthCare")
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
    String query = "SELECT * FROM payroll p " +
                   "LEFT JOIN employees e ON p.empID = e.empID;";  // Joins with employees table to get employee details if necessary

    try (Connection conn = DatabaseService.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            int _empID = rs.getInt("empID");
            int _payDate = rs.getInt("payDate");
            int _gross = rs.getInt("gross");
            int _federal = rs.getInt("federal");
            int _fedMed = rs.getInt("fedMed");
            int _fedSS = rs.getInt("fedSS");
            int _state = rs.getInt("state");
            int _emp401K = rs.getInt("emp401K");
            int _healthCare = rs.getInt("healthCare");

            // Create Payroll object using data from ResultSet
            Payroll payroll = new Payroll(_empID, _payDate, _gross, _federal, _fedMed, _fedSS, _state, _emp401K, _healthCare);
            
            payrolls.add(payroll);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return payrolls;
}

}
