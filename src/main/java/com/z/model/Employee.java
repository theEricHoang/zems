package com.z.model;

public class Employee implements IEmployee
{
    // For incrementing IDs with each new employee.
    // Will be updated from database, so each session
    // should retrieve current highest ID and set
    // idCounter accordingly.
    private static int idCounter = 0;

    private int empID, departmentID;
    private String name, ssn, division, jobTitle, email, hireDate;
    private double salary;

    public Employee(int _divisionID, String _name, String _email, String _ssn,
                    String _hireDate, String _division, String _jobTitle, double _salary)
    {
        initEmpID();
        setDepartmentID(_divisionID);
        setName(_name);
        setEmail(_email);
        setSSN(_ssn);
        setHireDate(_hireDate);
        setDivision(_division);
        setJobTitle(_jobTitle);
        setSalary(_salary);
    }

    public static void setInitialIDCounter(int _initialID) { idCounter = _initialID; }

    public int getEmpID() { return empID; }
    public void setEmpID(int _empID) { empID = _empID; }
    public void initEmpID() { empID = ++idCounter; }
    
    public int getDepartmentID() { return departmentID; }
    public void setDepartmentID(int _departmentID) { departmentID = _departmentID; }

    public String getName() { return name; }
    public void setName(String _name) { name = _name; }

    public String getSSN() { return ssn; }
    public void setSSN(String _ssn) { ssn = _ssn; }

    public String getDivision() { return division; }
    public void setDivision(String _division) { division = _division; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String _jobTitle) { jobTitle = _jobTitle; }

    public double getSalary() { return salary; }
    public void setSalary(double _salary) { salary = _salary; }

    public String getEmail() { return email; }
    public void setEmail(String _email) { email = _email; }

    public String getHireDate() { return hireDate; }
    public void setHireDate(String _hireDate) { hireDate = _hireDate; }
}
