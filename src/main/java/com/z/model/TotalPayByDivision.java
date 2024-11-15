package com.z.model;

public class TotalPayByDivision implements ITotalPayByDivision 
{
    private String month, year, division;
    private Double totalPay;

    public TotalPayByDivision(String _month, String _year, String _division, Double _totalPay)
    {
        setMonth(_month);
        setYear(_year);
        setDivision(_division);
        setTotalPay(_totalPay);
    }

    public String getMonth() { return month; }
    public void setMonth(String _month) { month = _month; } 

    public String getYear() { return year; }
    public void setYear(String _year) { year = _year; }

    public String getDivision() { return division; }
    public void setDivision(String _division) { division = _division; }

    public Double getTotalPay() { return totalPay; }
    public void setTotalPay(Double _totalPay) { totalPay = _totalPay; }
}
