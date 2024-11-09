package com.z.model;

public class Employee implements IEmployee
{
    // For incrementing IDs with each new employee.
    // Will be updated from database, so each session
    // should retrieve current highest ID and set
    // idCounter accordingly.
    private static int idCounter = 0;

    private int empID, departmentID;
    private String name, ssn, department, jobTitle;
    private double salary;
    private Address address;

    public Employee(int _departmentID, String _name, String _ssn,
                    String _department, String _jobTitle, double _salary,
                    Address _address)
    {
        setEmpID();
        setDepartmentID(_departmentID);
        setName(_name);
        setSSN(_ssn);
        setDepartment(_department);
        setJobTitle(_jobTitle);
        setSalary(_salary);
        setAddress(_address);
    }

    public static void setInitialIDCounter(int _initialID)
    {
        idCounter = _initialID;
    }

    public int getEmpID()
    {
        return empID;
    }
    public void setEmpID()
    {
        empID = ++idCounter;
    }
    
    public int getDepartmentID()
    {
        return departmentID;
    }
    public void setDepartmentID(int _departmentID)
    {
        departmentID = _departmentID;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String _name)
    {
        name = _name;
    }
    
    public String getSSN()
    {
        return ssn;
    }
    public void setSSN(String _ssn)
    {
        ssn = _ssn;
    }

    public String getDepartment()
    {
        return department;
    }
    public void setDepartment(String _department)
    {
        department = _department;
    }

    public String getJobTitle()
    {
        return jobTitle;
    }
    public void setJobTitle(String _jobTitle)
    {
        jobTitle = _jobTitle;
    }

    public double getSalary()
    {
        return salary;
    }
    public void setSalary(double _salary)
    {
        salary = _salary;
    }

    public Address getAddress()
    {
        return address;
    }
    public void setAddress(Address _address)
    {
        address = _address;
    }
}
