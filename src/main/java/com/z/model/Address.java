package com.z.model;

import java.time.LocalDate;

public class Address {
    private int empID;
    private String gender;
    private String pronouns;
    private String race;
    private LocalDate dob;
    private String phone;
    private int cityID;
    private int stateID;

    // Constructor
    public Address(int empID, String gender, String pronouns, String race, LocalDate dob, String phone, int cityID, int stateID) {
        this.empID = empID;
        this.gender = gender;
        this.pronouns = pronouns;
        this.race = race;
        this.dob = dob;
        this.phone = phone;
        this.cityID = cityID;
        this.stateID = stateID;
    }

    public int getEmpID() { return empID; }
    public void setEmpID(int _empID) { empID = _empID; }

    /** Returns the gender of the employee. */
    public String getGender() { return gender; }
    public void setGender(String _gender) { gender = _gender; }

    /** Returns the pronouns used by the employee. */
    public String getPronouns() { return pronouns; }
    public void setPronouns(String _pronouns) { pronouns = _pronouns; }

    /** Returns the race of the employee. */
    public String getRace() { return race; }
    public void setRace(String _race) { race = _race; }

    /** Returns the date of birth of the employee. */
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate _dob) { dob = _dob; }

    /** Returns the phone number of the employee. */
    public String getPhone() { return phone; }
    public void setPhone(String _phone) { phone = _phone;}

    /** Returns the city ID associated with the address. */
    public int getCityID() { return cityID; }
    public void setCityID(int _cityID) { cityID = _cityID; }

    /** Returns the state ID associated with the address. */
    public int getStateID() { return stateID; }
    public void setStateID(int _stateID) { stateID = _stateID; }
}

