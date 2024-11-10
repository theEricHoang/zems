package com.z.model;

public interface IEmployee {
    public int getEmpID();
    public void setEmpID(int _empID);
    public void initEmpID();
    
    public int getDivID();
    public void setDivID(int _divID);

    public String getFName();
    public void setFName(String _fName);

    public String getLName();
    public void setLName(String _lName);

    public String getSSN();
    public void setSSN(String _ssn);

    public String getDivision();
    public void setDivision(String _division);

    public String getJobTitle();
    public void setJobTitle(String _jobTitle);

    public double getSalary();
    public void setSalary(double _salary);

    public String getEmail();
    public void setEmail(String _email);

    public String getHireDate();
    public void setHireDate(String _hireDate);
}
