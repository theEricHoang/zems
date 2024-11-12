package com.z.model;

public abstract class AbstractAddress {
    private String street, city, state, gender, pronouns, race;
    private int zipCode, DOB, phoneNum;

    public AbstractAddress(String street, String city, String state, int zipCode, String gender, 
                            String pronouns, String race, int DOB, int phoneNum) 
    {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.gender = gender;
        this.pronouns = pronouns;
        this.race = race;
        this.DOB = DOB;
        this.phoneNum = phoneNum;
    }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public int getZipCode() { return zipCode; }
    public void setZipCode(int zipCode) { this.zipCode = zipCode; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPronouns() { return pronouns; }
    public void setPronouns(String pronouns) { this.pronouns = pronouns; }

    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }
    
    public int getDOB() { return DOB; }
    public void setDOB(int DOB) { this.DOB = DOB; }

    public int getPhoneNum() { return phoneNum; }
    public void setPhoneNum(int phoneNum) { this.phoneNum = phoneNum; }
}