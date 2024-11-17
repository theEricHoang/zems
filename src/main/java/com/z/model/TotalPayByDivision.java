package com.z.model;

public class TotalPayByDivision implements ITotalPayByDivision 
{
    private String month, division;
    private Double totalPay;

    public TotalPayByDivision(String _month, String _division, Double _totalPay)
    {
        setMonth(_month);
        setDivision(_division);
        setTotalPay(_totalPay);
    }

    public String getMonth() { return month; }
    public void setMonth(String _month) { month = _month; }

    public String getDivision() { return division; }
    public void setDivision(String _division) { division = _division; }

    public Double getTotalPay() { return totalPay; }
    public void setTotalPay(Double _totalPay) { totalPay = _totalPay; }
}
