/*
 * AddressController class is responsible for handling user interactions 
 * related to address data within the Employee Management System. 
 * It provides methods to add, update, delete, and search address records, 
 * while validating data using the Validation class and managing database 
 * operations through the AddressDAO.
 */

 package com.z.controller;

 import javafx.collections.FXCollections;
 import com.z.model.Address;
 import com.z.model.dao.AddressDAO;
 import com.z.service.Validation;
 import javafx.collections.ObservableList;
 import javafx.scene.control.Alert;
 import java.sql.SQLException;
 import java.time.LocalDate;
 
 public class AddressController {
 
     /*
      * Adds a new address record after validating the input fields.
      * If validation passes, it inserts the record into the database via AddressDAO.
      */
     public boolean addAddress(int empID, int cityID, int stateID, String gender, String pronouns, String race, LocalDate dob, String phone) {
         // Validate all fields before proceeding
         if (!Validation.validateCityID(cityID) || !Validation.validateStateID(stateID)) {
             showAlert("Error", "Invalid city or state ID.");
             return false;
         }
         if (!Validation.validateGender(gender) || !Validation.validatePronouns(pronouns)) {
             showAlert("Error", "Invalid gender or pronouns.");
             return false;
         }
         if (!Validation.validateRace(race)) {
             showAlert("Error", "Invalid race.");
             return false;
         }
         if (!Validation.validateDOB(dob)) {
             showAlert("Error", "Date of birth must be in the past.");
             return false;
         }
         if (!Validation.validatePhone(phone)) {
             showAlert("Error", "Invalid phone number format. Use XXX-XXX-XXXX.");
             return false;
         }
 
         // If all validations pass, create a new Address object
         Address address = new Address(empID, gender, pronouns, race, dob, phone, cityID, stateID);
 
         try {
             // Call DAO to add the address to the database
             AddressDAO.addAddress(address);
             return true; // Return true if the addition is successful
         } catch (SQLException e) {
             e.printStackTrace();
             showAlert("Database Error", "Failed to add address to the database.");
             return false;
         }
     }
 
     /*
      * Updates an existing address record after validating the input fields.
      * If validation passes, it updates the record in the database via AddressDAO.
      */
     public boolean updateAddress(int empID, int cityID, int stateID, String gender, String pronouns, String race, LocalDate dob, String phone) {
         // Validate all fields before proceeding
         if (!Validation.validateCityID(cityID) || !Validation.validateStateID(stateID)) {
             showAlert("Error", "Invalid city or state ID.");
             return false;
         }
         if (!Validation.validateGender(gender) || !Validation.validatePronouns(pronouns)) {
             showAlert("Error", "Invalid gender or pronouns.");
             return false;
         }
         if (!Validation.validateRace(race)) {
             showAlert("Error", "Invalid race.");
             return false;
         }
         if (!Validation.validateDOB(dob)) {
             showAlert("Error", "Date of birth must be in the past.");
             return false;
         }
         if (!Validation.validatePhone(phone)) {
             showAlert("Error", "Invalid phone number format. Use XXX-XXX-XXXX.");
             return false;
         }
 
         // If all validations pass, create a new Address object
         Address address = new Address(empID, gender, pronouns, race, dob, phone, cityID, stateID);
 
         try {
             // Call DAO to update the address in the database
             AddressDAO.updateAddress(address);
             return true; // Return true if the update is successful
         } catch (SQLException e) {
             e.printStackTrace();
             showAlert("Database Error", "Failed to update address in the database.");
             return false;
         }
     }
 
     /*
      * Deletes an address record by employee ID.
      * Calls AddressDAO to perform the deletion in the database.
      */
     public boolean deleteAddress(int empID) {
         try {
             // Call DAO to delete the address
             AddressDAO.deleteAddress(empID);
             return true; // Return true if the deletion is successful
         } catch (SQLException e) {
             e.printStackTrace();
             showAlert("Database Error", "Failed to delete address from the database.");
             return false;
         }
     }
 
     /*
      * Searches for an address by employee ID.
      * Returns the Address object if found, or null if not found.
      */
     public Address searchAddressByEmpID(int empID) {
         try {
             return AddressDAO.searchAddressByEmpID(empID); // Call DAO to search for address
         } catch (SQLException e) {
             e.printStackTrace();
             showAlert("Database Error", "Failed to retrieve address from the database.");
             return null;
         }
     }
 
     /*
      * Searches for addresses by city or state name.
      * Returns an ObservableList of Address objects that match the search criteria.
      */
     public ObservableList<Address> searchAddressByLocation(String location) {
         try {
             return FXCollections.observableArrayList(AddressDAO.searchAddressByCityOrState(location)); // Call DAO to search for addresses
         } catch (SQLException e) {
             e.printStackTrace();
             showAlert("Database Error", "Failed to retrieve addresses from the database.");
             return FXCollections.observableArrayList(); // Return empty list if an error occurs
         }
     }
 
     /*
      * Helper method to show an alert message in the GUI.
      * Used for displaying validation and database error messages to the user.
      */
     private void showAlert(String title, String message) {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle(title);
         alert.setHeaderText(null);
         alert.setContentText(message);
         alert.showAndWait();
     }
 }
 
