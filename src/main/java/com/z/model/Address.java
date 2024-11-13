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

    public int getEmpID() {
        return empID;
    }

    /** Returns the gender of the employee. */
    public String getGender() {
        return gender;
    }

    /** Returns the pronouns used by the employee. */
    public String getPronouns() {
        return pronouns;
    }

    /** Returns the race of the employee. */
    public String getRace() {
        return race;
    }

    /** Returns the date of birth of the employee. */
    public LocalDate getDob() {
        return dob;
    }

    /** Returns the phone number of the employee. */
    public String getPhone() {
        return phone;
    }

    /** Returns the city ID associated with the address. */
    public int getCityID() {
        return cityID;
    }

    /** Returns the state ID associated with the address. */
    public int getStateID() {
        return stateID;
    }
}

