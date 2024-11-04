package com.z.employees;

public abstract class AbstractAddress 
{
    public String street, city, state;
    public int zipCode; 

    public AbstractAddress(String _street, String _city, String _state, int _zipCode)
    {
        street = _street;
        city = _city;
        state = _state;
        zipCode = _zipCode;
    }

    public String getStreet()
    {
        return street;
    }
    public void setStreet(String _street)
    {
        street = _street;
    }

    public String getCity()
    {
        return city;
    }
    public void setCity(String _city)
    {
        city = _city;
    }

    public String getState()
    {
        return state;
    }
    public void setState(String _state)
    {
        state = _state;
    }

    public int getZipCode()
    {
        return zipCode;
    }
    public void setZipCode(int _zipCode)
    {
        zipCode = _zipCode;
    }
}
