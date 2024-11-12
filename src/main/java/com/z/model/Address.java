package com.z.model;

public class Address extends AbstractAddress {
    public Address(String street, String city, String state, int zipCode, String gender, 
                    String pronouns, String race, int DOB, int phoneNum) 
    {
        super(street, city, state, zipCode, gender, pronouns, race, DOB, phoneNum);
    }
}