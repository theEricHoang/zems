package com.z.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.z.model.Payroll;
import com.z.model.TotalPayByDivision;
import com.z.model.TotalPayByTitle;
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
public static ObservableList<TotalPayByTitle> getJobTitlePay(Connection conn, String month, String year) throws SQLException {
    ObservableList<TotalPayByTitle> titlePayList = FXCollections.observableArrayList();
    String query = "SELECT jt.job_title, SUM(e.salary) AS total_pay " +
                   "FROM employees e " +
                   "JOIN employee_job_titles ejt ON ejt.empid = e.empid " +
                   "JOIN job_titles jt ON jt.job_title_id = ejt.job_title_id " +
                   "JOIN payroll p on p.empid = e.empid " +
                   "WHERE MONTH(p.pay_date) = ? AND YEAR(p.pay_date) = ? " +
                   "GROUP BY jt.job_title";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, month);
        pstmt.setString(2, year);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String title = rs.getString("job_title");
                Double totalPay = rs.getDouble("total_pay");
                titlePayList.add(new TotalPayByTitle(month, year, title, totalPay));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error retrieving total salary by job title.", e);
    }
    return titlePayList;
}

// Get the total salary by division
public static ObservableList<TotalPayByDivision> getDivisionPay(Connection conn, String month, String year) throws SQLException {
    ObservableList<TotalPayByDivision> divisionPayList = FXCollections.observableArrayList();
    String query = "SELECT d.Name AS division, SUM(e.salary) AS total_pay "
                    + "FROM employees e "
                    + "JOIN employee_division ed ON ed.empid = e.empid "
                    + "JOIN division d ON d.ID = ed.div_ID "
                    + "JOIN payroll p ON p.empid = e.empid "
                    + "WHERE MONTH(p.pay_date) = ? AND YEAR(p.pay_date) = ? "
                    + "GROUP BY d.Name";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, month);
        pstmt.setString(2, year);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String division = rs.getString("division");
                Double totalPay = rs.getDouble("total_pay");
                divisionPayList.add(new TotalPayByDivision(month, year, division, totalPay));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error retrieving total salary by division.", e);
    }
    return divisionPayList;
}

// Get Payroll information for an employee by empID
public static ObservableList<Payroll> getPayrollInfoByEmpID(int empID, Connection conn) throws SQLException {
    ObservableList<Payroll> payrolls = FXCollections.observableArrayList();
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
            while (rs.next()) {
                String name = rs.getString("name");
                String title = rs.getString("title");
                String email = rs.getString("email");
                int empId = rs.getInt("empid");
                String payDate = rs.getString("pay_date");
                int earnings = rs.getInt("earnings");
                int fedTax = rs.getInt("fed_tax");
                int fedMed = rs.getInt("fed_med");
                int fedSS = rs.getInt("fed_SS");
                int stateTax = rs.getInt("state_tax");
                int retire401k = rs.getInt("retire_401k");
                int healthCare = rs.getInt("health_care");
                
                Payroll payroll = new Payroll(name, title, email, empId, payDate, earnings, fedTax, fedMed, fedSS, stateTax, retire401k, healthCare);
                payrolls.add(payroll);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error retrieving payroll information.", e);
    }
    
    return payrolls;
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
