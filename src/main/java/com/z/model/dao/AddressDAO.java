/**
 * AddressDAO class
 * 
 * This class, AddressDAO, is responsible for managing all database operations related to employee addresses.
 * It provides methods to add, update, delete, and retrieve address records, making it a key part of the 
 * application's interaction with the database for address management. Each method performs specific SQL 
 * operations, such as inserting a new address, updating an existing address, deleting an address by employee ID, 
 * and retrieving address information based on search criteria. This ensures that the address data remains 
 * consistent and accessible across the application.
 */

 package com.z.model.dao;

 import com.z.model.Address;
 import com.z.service.DatabaseService;
 import javafx.collections.FXCollections;
 import javafx.collections.ObservableList;
 
 import java.sql.*;
 import java.time.LocalDate;
 
 public class AddressDAO {
 
     /**
      * Method: getEmployeeAddress
      * 
      * This method retrieves all employee addresses from the database by running a SQL query that joins several 
      * tables related to employee information. Each address record is then converted into an Address object and 
      * added to an ObservableList, which can be used easily in JavaFX applications.
      */
     public static ObservableList<Address> getEmployeeAddress() throws SQLException {
         ObservableList<Address> addresses = FXCollections.observableArrayList();
         String query = "SELECT * FROM Address e " +
                        "LEFT JOIN employee_job_titles ejt ON e.empid = ejt.empid " +
                        "LEFT JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
                        "LEFT JOIN employee_division ed ON e.empid = ed.empid " +
                        "LEFT JOIN division d ON ed.div_ID = d.ID";
 
         // Establish a connection and execute the query.
         try (Connection conn = DatabaseService.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(query)) {
 
             // Process each result row by creating a new Address object with the retrieved data.
             while (rs.next()) {
                 int empID = rs.getInt("empid");
                 String gender = rs.getString("gender");
                 String pronouns = rs.getString("pronouns");
                 String race = rs.getString("race");
                 LocalDate dob = rs.getDate("dob").toLocalDate();
                 String phone = rs.getString("phone");
                 int cityID = rs.getInt("cityID");
                 int stateID = rs.getInt("stateID");
 
                 // Creates a new Address object and adds it to the list
                 Address address = new Address(empID, gender, pronouns, race, dob, phone, cityID, stateID);
                 addresses.add(address);
             }
         } catch (SQLException e) {
             e.printStackTrace();
             throw e;
         }
         return addresses;
     }
 
     /**
      * Method: addAddress
      * 
      * This method takes an Address object as a parameter and inserts a new address record into the database.
      * It prepares a SQL statement to securely insert each value from the Address object into the database.
      */
     public static void addAddress(Address address) throws SQLException {
         String insertAddressQuery = "INSERT INTO Address (empid, gender, pronouns, race, dob, phone, cityID, stateID) " +
                                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
 
         // Use a prepared statement to avoid SQL injection attacks and handle each value properly.
         try (Connection conn = DatabaseService.getConnection();
              PreparedStatement pstmtAddress = conn.prepareStatement(insertAddressQuery)) {
 
             pstmtAddress.setInt(1, address.getEmpID());
             pstmtAddress.setString(2, address.getGender());
             pstmtAddress.setString(3, address.getPronouns());
             pstmtAddress.setString(4, address.getRace());
             pstmtAddress.setDate(5, Date.valueOf(address.getDob()));
             pstmtAddress.setString(6, address.getPhone());
             pstmtAddress.setInt(7, address.getCityID());
             pstmtAddress.setInt(8, address.getStateID());
             pstmtAddress.executeUpdate();
 
         } catch (SQLException e) {
             e.printStackTrace();
             throw e;
         }
     }
 
     /**
      * Method: deleteAddress
      * 
      * This method deletes an address from the database based on the employee ID. It constructs a SQL delete 
      * statement that removes the record where the employee ID matches the specified empID.
      */
     public static void deleteAddress(int empID) throws SQLException {
         String deleteAddressQuery = "DELETE FROM Address WHERE empid = ?";
 
         // Prepare and execute the SQL delete statement.
         try (Connection conn = DatabaseService.getConnection();
              PreparedStatement pstmtAddress = conn.prepareStatement(deleteAddressQuery)) {
             pstmtAddress.setInt(1, empID);
             pstmtAddress.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
             throw e;
         }
     }
 
     /**
      * Method: updateAddress
      * 
      * This method updates an existing address record in the database using the data from an Address object.
      * It sets new values for each field where the employee ID matches.
      */
     public static void updateAddress(Address address) throws SQLException {
         String updateAddressQuery = "UPDATE Address SET gender = ?, pronouns = ?, race = ?, dob = ?, phone = ?, " +
                                     "cityID = ?, stateID = ? WHERE empid = ?";
 
         // Prepare and execute the update query.
         try (Connection conn = DatabaseService.getConnection();
              PreparedStatement pstmtAddress = conn.prepareStatement(updateAddressQuery)) {
 
             pstmtAddress.setString(1, address.getGender());
             pstmtAddress.setString(2, address.getPronouns());
             pstmtAddress.setString(3, address.getRace());
             pstmtAddress.setDate(4, Date.valueOf(address.getDob()));
             pstmtAddress.setString(5, address.getPhone());
             pstmtAddress.setInt(6, address.getCityID());
             pstmtAddress.setInt(7, address.getStateID());
             pstmtAddress.setInt(8, address.getEmpID());
             pstmtAddress.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
             throw e;
         }
     }
 
     /**
      * Method: searchAddressByEmpID
      * 
      * This method searches for an address record based on the employee ID. If a record is found, it returns 
      * an Address object with the data from the database. If no record is found, it returns null.
      */
     public static Address searchAddressByEmpID(int empID) throws SQLException {
         String query = "SELECT * FROM Address WHERE empid = ?";
         
         // Prepare and execute the query with the provided empID.
         try (Connection conn = DatabaseService.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
             pstmt.setInt(1, empID);
             ResultSet rs = pstmt.executeQuery();
 
             if (rs.next()) {
                 String gender = rs.getString("gender");
                 String pronouns = rs.getString("pronouns");
                 String race = rs.getString("race");
                 LocalDate dob = rs.getDate("dob").toLocalDate();
                 String phone = rs.getString("phone");
                 int cityID = rs.getInt("cityID");
                 int stateID = rs.getInt("stateID");
 
                 return new Address(empID, gender, pronouns, race, dob, phone, cityID, stateID);
             }
         } catch (SQLException e) {
             e.printStackTrace();
             throw e;
         }
         return null;
     }
 
     /**
      * Method: searchAddressByCityOrState
      * 
      * This method searches for addresses by city or state name. It runs a SQL query with wildcard characters 
      * for partial matches, making it flexible for different input formats.
      */
     public static ObservableList<Address> searchAddressByCityOrState(String location) throws SQLException {
         ObservableList<Address> addresses = FXCollections.observableArrayList();
         String query = "SELECT * FROM Address WHERE city LIKE ? OR state LIKE ?";
 
         // Use the provided location parameter with wildcard characters for flexibility.
         try (Connection conn = DatabaseService.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
             pstmt.setString(1, "%" + location + "%");
             pstmt.setString(2, "%" + location + "%");
             ResultSet rs = pstmt.executeQuery();
 
             while (rs.next()) {
                 int empID = rs.getInt("empid");
                 String gender = rs.getString("gender");
                 String pronouns = rs.getString("pronouns");
                 String race = rs.getString("race");
                 LocalDate dob = rs.getDate("dob").toLocalDate();
                 String phone = rs.getString("phone");
                 int cityID = rs.getInt("cityID");
                 int stateID = rs.getInt("stateID");
 
                 Address address = new Address(empID, gender, pronouns, race, dob, phone, cityID, stateID);
                 addresses.add(address);
             }
         } catch (SQLException e) {
             e.printStackTrace();
             throw e;
         }
         return addresses;
     }
 }
 