package com.z.model;

public class Employee implements IEmployee
{
    // For incrementing IDs with each new employee.
    // Will be updated from database, so each session
    // should retrieve current highest ID and set
    // idCounter accordingly.
    private static int idCounter = 0;

    private int empID, divID;
    private String fName, lName, ssn, division, jobTitle, email, hireDate;
    private double salary;

    public Employee(boolean _newEmployee, int _divisionID, String _fName, String _lName, String _email, String _ssn,
                    String _hireDate, String _division, String _jobTitle, double _salary)
    {
        if (_newEmployee) { initEmpID(); }
        setDivID(_divisionID);
        setFName(_fName);
        setLName(_lName);
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
    
    public int getDivID() { return divID; }
    public void setDivID(int _departmentID) { divID = _departmentID; }

    public String getFName() { return fName; }
    public void setFName(String _fName) { fName = _fName; }

    public String getLName() { return lName; }
    public void setLName(String _lName) { lName = _lName; }

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
