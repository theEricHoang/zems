package com.z.model;

public interface IPayroll {
    public int getEmpID();
    public void setEmpID(int _empID);

    public String getPayDate();
    public void setPayDate(String _payDate);

    public double getGross();
    public void setGross(double _gross);

    public double getFederal();
    public void setFederal(double _federal);

    public double getFedMed();
    public void setFedMed(double _fedMed);

    public double getFedSS();
    public void setFedSS(double _fedSS);

    public double getState();
    public void setState(double _state);

    public double get401K();
    public void set401K(double _emp401K);

    public double getHealthCare();
    public void setHealthCare(double _healthCare);
}