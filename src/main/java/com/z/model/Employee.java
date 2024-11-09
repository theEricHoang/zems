package com.z.model;

public class Employee implements IEmployee {
    private int empID, departmentID;
    private String name, ssn, department, jobTitle;
    private double salary;
    private Address address;
    private Payroll payroll;

    public Employee(int empID, int departmentID, String name, String ssn, String department,
                    String jobTitle, double salary, Address address, Payroll payroll) {
        this.empID = empID;
        this.departmentID = departmentID;
        this.name = name;
        this.ssn = ssn;
        this.department = department;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.address = address;
        this.payroll = payroll;
    }

    // Getters and setters
    public int getEmpID() { return empID; }
    public void setEmpID(int empID) { this.empID = empID; }

    public int getDepartmentID() { return departmentID; }
    public void setDepartmentID(int departmentID) { this.departmentID = departmentID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSSN() { return ssn; }
    public void setSSN(String ssn) { this.ssn = ssn; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public Payroll getPayroll() { return payroll; }
    public void setPayroll(Payroll payroll) { this.payroll = payroll; }
}