/**
 * A class to handles Create, Read, Update and Delete (CRUD) operation.
 * It is going to use helper classes to perform these tasks.
 * EmployeeOperations to handle the adding, viewing, updating and deleting employees,
 * RaiseEmployee to handle giving an individual a raise by a set percentage amount
 * RaiseManager to grant a raise to all employees ina specific department based on their salary range.
 */

package com.z.employees;

import java.util.ArrayList;
import java.util.List;
// The List to store the employees and the helper classes
public class EmployeeManager {
    private List<Employee> employees;
    private EmployeeOperations employeeOperations;



    public EmployeeManager() {
        employees = new ArrayList<>();
        employeeOperations = new EmployeeOperations();

    }

    /**
     *
     * @param employee
     */
    public void addEmployee(Employee employee) {
        employeeOperations.addEmployee(employees, employee);
    }

    /**
     *
     * @param empID
     */
    public void viewEmployee(int empID) {
        employeeOperations.viewEmployee(employees, empID);
    }

    /**
     *
     * @param empID
     * @param jobTitle
     * @param salary
     */
    public void updateEmployee(int empID, String jobTitle, double salary) {
        employeeOperations.updateEmployee(employees, empID, jobTitle, salary);
    }

    /**
     * @param empID
     */
    public void deleteEmployee(int empID) {
        employeeOperations.deleteEmployee(employees, empID);
    }

    /**
     *
     * @param empID
     * @param raisePercentage
     */


    public void giveRaiseToEmployee(int empID, double raisePercentage) {
      employeeOperations.giveRaiseToEmployee(employees, empID, raisePercentage);
    }

    /**
     *
     * @param departmentName
     * @param raisePercentage
     * @param minSalary
     * @param maxSalary
     */

    public void giveRaiseToDepartment(String departmentName, double raisePercentage, double minSalary, double maxSalary) {
        employeeOperations.giveRaiseToDepartment(employees, departmentName, raisePercentage, minSalary, maxSalary);
    }
}
