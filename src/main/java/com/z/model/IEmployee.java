package com.z.model;

public interface IEmployee {
    int getEmpID();
    void setEmpID(int empID);

    int getDepartmentID();
    void setDepartmentID(int departmentID);

    String getName();
    void setName(String name);

    String getSSN();
    void setSSN(String ssn);

    String getDepartment();
    void setDepartment(String department);

    String getJobTitle();
    void setJobTitle(String jobTitle);

    double getSalary();
    void setSalary(double salary);

    Address getAddress();
    void setAddress(Address address);

    Payroll getPayroll();
    void setPayroll(Payroll payroll);
}