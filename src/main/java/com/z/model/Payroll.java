package com.z.model;

public class Payroll implements IPayroll 
{
    private int empID;
    private double gross, federal, fedMed, fedSS, state, emp401K, healthCare;
    private String name, email, payDate;

    public Payroll(String _name, String _email, int _empID, String _payDate, double _gross, 
                    double _federal, double _fedMed, double _fedSS, double _state, double _emp401K, double _healthCare) 
    {
        setName(_name);
        setEmail(_email);
        setEmpID(_empID);
        setPayDate(_payDate);
        setGross(_gross);
        setFederal(_federal);
        setFedMed(_fedMed);
        setFedSS(_fedSS);
        setState(_state);
        set401K(_emp401K);
        setHealthCare(_healthCare);
    }

    public String getName() { return name; }
    public void setName(String _name) { name = _name; }

    public String getEmail() { return email; }
    public void setEmail(String _email) { email = _email; }

    public int getEmpID() { return empID; }
    public void setEmpID(int _empID) { empID = _empID; }

    public String getPayDate() { return payDate; }
    public void setPayDate(String _payDate) { payDate = _payDate; }

    public double getGross() { return gross; }
    public void setGross(double _gross) { gross = _gross; }

    public double getFederal() { return federal; }
    public void setFederal(double _federal) { federal = _federal; }

    public double getFedMed() { return fedMed; }
    public void setFedMed(double _fedMed) { fedMed = _fedMed; }

    public double getFedSS() { return fedSS; }
    public void setFedSS(double _fedSS) { fedSS = _fedSS; }

    public double getState() { return state; }
    public void setState(double _state) { state = _state; }

    public double get401K() { return emp401K; }
    public void set401K(double _emp401K) { emp401K = _emp401K; }

    public double getHealthCare() { return healthCare; }
    public void setHealthCare(double _healthCare) { healthCare = _healthCare; }
}
