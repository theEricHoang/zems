package com.z.model;
 
public class Payroll implements IPayroll 
{
    private int empID, payDate, gross, federal, fedMed, fedSS, state, emp401K, healthCare;

    public Payroll(int _empID, int _payDate, int _gross, int _federal, int _fedMed, 
                    int _fedSS, int _state, int _emp401K, int _healthCare) 
    {
        setPayDate(_payDate);
        setGross(_gross);
        setFederal(_federal);
        setFedMed(_fedMed);
        setFedSS(_fedSS);
        setState(_state);
        set401K(_emp401K);
        setHealthCare(_healthCare);
    }

    public int getEmpID() { return empID; }
    public void setEmpID(int _empID) { empID = _empID; }

    public int getPayDate() { return payDate; }
    public void setPayDate(int _payDate) { payDate = _payDate; }

    public int getGross() { return gross; }
    public void setGross(int _gross) { gross = _gross; }

    public int getFederal() { return federal; }
    public void setFederal(int _federal) { federal = _federal; }

    public int getFedMed() { return fedMed; }
    public void setFedMed(int _fedMed) { fedMed = _fedMed; }

    public int getFedSS() { return fedSS; }
    public void setFedSS(int _fedSS) { fedSS = _fedSS; }

    public int getState() { return state; }
    public void setState(int _state) { state = _state; }

    public int get401K() { return emp401K; }
    public void set401K(int _emp401K) { emp401K = _emp401K; }

    public int getHealthCare() { return healthCare; }
    public void setHealthCare(int _healthCare) { healthCare = _healthCare; }
}