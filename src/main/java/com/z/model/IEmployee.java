package com.z.model;

public interface IEmployee {
    public int getEmpID();
    public void setEmpID(int _empID);
    
    public int getDepartmentID();
    public void setDepartmentID(int _departmentID);

    public String getName();
    public void setName(String _name);
    
    public String getSSN();
    public void setSSN(String _ssn);

    public String getDepartment();
    public void setDepartment(String _department);

    public String getJobTitle();
    public void setJobTitle(String _jobTitle);

    public double getSalary();
    public void setSalary(double _salary);

    public Address getAddress();
    public void setAddress(Address _address);

    public Payroll getPayroll();
    public void setPayroll(Payroll _payroll);
}
