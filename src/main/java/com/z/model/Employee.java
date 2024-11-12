package com.z.model;

public class Employee {
    private int empID;
    private String fName;
    private String lName;
    private String email;
    private String ssn;
    private String division;
    private String jobTitle;
    private String hireDate;
    private double salary;
    private String state;

    // Constructor with empID
    public Employee(int empID, String fName, String lName, String email, String ssn, String division, String jobTitle, String hireDate, double salary, String state) {
        this.empID = empID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.ssn = ssn;
        this.division = division;
        this.jobTitle = jobTitle;
        this.hireDate = hireDate;
        this.salary = salary;
        this.state = state;
    }
    
    // Constructor without empID
    public Employee(String fName, String lName, String email, String ssn, String division, String jobTitle, String hireDate, double salary, String state) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.ssn = ssn;
        this.division = division;
        this.jobTitle = jobTitle;
        this.hireDate = hireDate;
        this.salary = salary;
        this.state = state;
    }

    // No-argument constructor
    public Employee() {}

    // Getters and Setters for each field
    public int getEmpID() { return empID; }
    public void setEmpID(int empID) { this.empID = empID; }

    public String getFName() { return fName; }
    public void setFName(String fName) { this.fName = fName; }

    public String getLName() { return lName; }
    public void setLName(String lName) { this.lName = lName; }

    public String getName() { return fName + " " + lName; }
    
    // setName method that accepts a single name string and splits it into first and last names
    public void setName(String name) {
        String[] parts = name.split(" ");
        if (parts.length > 0) this.fName = parts[0];
        if (parts.length > 1) this.lName = parts[1];
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSSN() { return ssn; }
    public void setSSN(String ssn) { this.ssn = ssn; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getHireDate() { return hireDate; }
    public void setHireDate(String hireDate) { this.hireDate = hireDate; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}
