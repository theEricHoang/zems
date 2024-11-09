package com.z.model.dao;

import com.z.model.Employee;
import com.z.service.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees e " + 
                        "LEFT JOIN employee_job_titles ejt ON e.empid = ejt.empid " + 
                        "INNER JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
                        "LEFT JOIN employee_division ed ON e.empid = ed.empid " +
                        "INNER JOIN division d ON ed.div_ID = d.ID;";

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
                String _fullName = _fName + " " + _lName;
                String _email = rs.getString("email");
                String _hireDate = rs.getString("HireDate");
                double _salary = rs.getDouble("Salary");
                String _ssn = rs.getString("SSN");

                Employee employee = new Employee(_divID, _fullName, _email, _ssn, _hireDate, _division, _jobTitle, _salary);
                employee.setEmpID(_empID);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO employees (empid, Fname, Lname, email, HireDate, Salary, SSN) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String[] nameStrings = employee.getName().split(" ");

            stmt.setInt(1, employee.getEmpID());
            stmt.setString(2, nameStrings[0]);
            stmt.setString(3, nameStrings[1]);
            stmt.setString(4, employee.getEmail());
            stmt.setString(5, employee.getHireDate());
            stmt.setDouble(6, employee.getSalary());
            stmt.setString(7, employee.getSSN());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int empID) throws SQLException {
        String query = "DELETE FROM employees WHERE empid = ?";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, empID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
