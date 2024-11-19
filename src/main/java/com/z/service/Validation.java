package com.z.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validation {

    public static boolean isValidSSN(String ssn) {
        return ssn != null && ssn.matches("\\d{3}-\\d{2}-\\d{4}");
    }

    public static boolean isInteger(String str) {
        if (str == null) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidEmpID(String empID) {
        try {
            int id = Integer.parseInt(empID);
            return id > 0; // Check if empID is positive
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDate(String dateStr) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches("^[a-zA-Z\\s]+$");
    }

    public static boolean validateCityID(int cityID) {
        return cityID > 0; // Assuming valid cityIDs are positive
    }

    public static boolean validateStateID(int stateID) {
        return stateID > 0; // Assuming valid stateIDs are positive
    }

    public static boolean validateGender(String gender) {
        return gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("Other");
    }

    public static boolean validatePronouns(String pronouns) {
        return pronouns != null && !pronouns.trim().isEmpty();
    }

    public static boolean validateRace(String race) {
        return race != null && !race.trim().isEmpty();
    }

    public static boolean validateDOB(String dob) {
        try {
            LocalDate _dob = formatDOB(dob);
            return _dob != null && _dob.isBefore(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }

    public static LocalDate formatDOB(String dob) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate _dob = LocalDate.parse(dob, dateFormat);
        return _dob;
    }

    public static boolean validatePhone(String phone) {
        return phone.matches("\\d{3}-\\d{3}-\\d{4}");
    }
}
