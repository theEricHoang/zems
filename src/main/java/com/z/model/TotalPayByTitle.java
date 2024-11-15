package com.z.model;

public class TotalPayByTitle implements ITotalPayByTitle 
{
    private String month, year, title;
    private Double totalPay;

    public TotalPayByTitle(String _month, String _year, String _title, Double _totalPay)
    {
        setMonth(_month);
        setYear(_year);
        setTitle(_title);
        setTotalPay(_totalPay);
    }

    public String getMonth() { return month; }
    public void setMonth(String _month) { month = _month; }

    public String getYear() { return year; }
    public void setYear(String _year) { year = _year; }

    public String getTitle() { return title; }
    public void setTitle(String _title) { title = _title; }

    public Double getTotalPay() { return totalPay; }
    public void setTotalPay(Double _totalPay) { totalPay = _totalPay; }
}
