package com.z.model;

public interface ITotalPayByTitle 
{
    public String getMonth();
    public void setMonth(String _month);

    public String getYear();
    public void setYear(String _year);

    public String getTitle();
    public void setTitle(String _title);

    public Double getTotalPay();
    public void setTotalPay(Double _totalPay);
}
