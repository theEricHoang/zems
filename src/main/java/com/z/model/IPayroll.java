package com.z.model;

public interface IPayroll {
    public int getEmpID();
    public void setEmpID(int _empID);

    public int getPayDate();
    public void setPayDate(int _payDate);

    public int getGross();
    public void setGross(int _gross);

    public int getFederal();
    public void setFederal(int _federal);

    public int getFedMed();
    public void setFedMed(int _fedMed);

    public int getFedSS();
    public void setFedSS(int _fedSS);

    public int getState();
    public void setState(int _state);

    public int get401K();
    public void set401K(int _emp401K);

    public int getHealthCare();
    public void setHealthCare(int _healthCare);
}