package com.z.database;

import com.z.model.Employee;
import com.z.model.Address;
import com.z.model.Payroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("empid"),
                        rs.getInt("departmentID"),
                        rs.getString("name"),
                        rs.getString("ssn"),
                        rs.getString("department"),
                        rs.getString("jobTitle"),
                        rs.getDouble("salary"),
                        new Address(rs.getString("street"), rs.getString("city"),
                                    rs.getString("state"), rs.getInt("zipCode")),
                        new Payroll());
                employees.add(employee);
            }
        }
        return employees;
    }

    public void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO employees (name, ssn, department, jobTitle, salary, departmentID) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getSSN());
            stmt.setString(3, employee.getDepartment());
            stmt.setString(4, employee.getJobTitle());
            stmt.setDouble(5, employee.getSalary());
            stmt.setInt(6, employee.getDepartmentID());
            stmt.executeUpdate();
        }
    }

    public void deleteEmployee(int empID) throws SQLException {
        String query = "DELETE FROM employees WHERE empid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, empID);
            stmt.executeUpdate();
        }
    }
}
