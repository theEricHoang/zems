/*
 * This class, EmployeesController, is responsible for managing the
 * display of employee data in a table, as well as handling user interactions
 * like adding new employees and searching for specific ones.
 * It uses data from the database through the EmployeeDAO class and 
 * displays it in a TableView for easy viewing in the application.
 */
package com.z.controller;

import com.z.App;
import com.z.model.Employee;
import com.z.model.dao.EmployeeDAO;
import com.z.service.DatabaseService;
import com.z.service.Validation;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeesController {
    /*
     * COMPONENT DECLARATIONS
     * separate lines because that's how java annotations work :(
     */
    @FXML private Button menuButton;
    @FXML private Button payrollButton;
    @FXML private Button addEmployeeButton;

    /*
     * TableView and Columns
     */
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Integer> employeeIDs;
    @FXML private TableColumn<Employee, String> employeeFNames;
    @FXML private TableColumn<Employee, String> employeeLNames;
    @FXML private TableColumn<Employee, Integer> employeeDivIDs;
    @FXML private TableColumn<Employee, String> employeeDivisions;
    @FXML private TableColumn<Employee, String> employeeJobTitles;
    @FXML private TableColumn<Employee, String> employeeEmails;
    @FXML private TableColumn<Employee, String> employeeSSNs;
    @FXML private TableColumn<Employee, String> employeeHireDates;
    @FXML private TableColumn<Employee, Double> employeeSalaries;

    @FXML private TableColumn<Employee, String> employeeCities;
    @FXML private TableColumn<Employee, String> employeeStates;
    @FXML private TableColumn<Employee, String> employeeGenders;
    @FXML private TableColumn<Employee, String> employeePronouns;
    @FXML private TableColumn<Employee, String> employeeRaces;
    @FXML private TableColumn<Employee, LocalDate> employeeDobs;
    @FXML private TableColumn<Employee, String> employeePhones;
    
    @FXML private TableColumn<Employee, Void> editButtons;
    @FXML private TableColumn<Employee, Void> deleteButtons;
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    /*
     * Searchbar and Options
     */
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> searchChoice;
    @FXML private Button searchButton;

    @FXML
    private void handleAddEmployee()
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/add_employee.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add New Employee");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(App.getPrimaryStage());
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadEmployeeData(); // refresh
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        employeeIDs.setCellValueFactory(new PropertyValueFactory<>("empID"));
        employeeFNames.setCellValueFactory(new PropertyValueFactory<>("fName"));
        employeeLNames.setCellValueFactory(new PropertyValueFactory<>("lName"));
        employeeDivIDs.setCellValueFactory(new PropertyValueFactory<>("divID"));
        employeeDivisions.setCellValueFactory(new PropertyValueFactory<>("division"));
        employeeJobTitles.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        employeeEmails.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeeSSNs.setCellValueFactory(new PropertyValueFactory<>("SSN"));
        employeeHireDates.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        employeeSalaries.setCellValueFactory(new PropertyValueFactory<>("salary"));

        employeeCities.setCellValueFactory(cellData -> new SimpleStringProperty(DatabaseService.fetchCity(cellData.getValue().getAddress().getCityID())));
        employeeStates.setCellValueFactory(cellData -> new SimpleStringProperty(DatabaseService.fetchState(cellData.getValue().getAddress().getStateID())));
        employeeGenders.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getGender()));
        employeePronouns.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPronouns()));
        employeeRaces.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getRace()));
        employeeDobs.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAddress().getDob()));
        employeePhones.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPhone()));

        searchChoice.getItems().addAll("ID", "Name", "SSN");

        loadEmployeeData();
        addEditButtons();
        addDeleteButtons();
    }

    @FXML
    private void handleSearch() {
        String search = searchField.getText();
        String searchFilter = searchChoice.getValue();

        // call EmployeeDAO to query database and search for employees by attribute
        // return it to employees
        // ObservableList to store and display the search results
        ObservableList<Employee> results = FXCollections.observableArrayList();
 
         // Checks if both the search criterion and search term have been entered
        if (searchFilter == null || search.isEmpty()) {
           loadEmployeeData(); // reset tableview when search is empty
           return; // Stops the method if any of these fields are empty
        }
 
        // Determines the search action based on the selected search criterion
        switch (searchFilter) {
            case "SSN":
                // Validates the SSN format using the Validation class
                if (!Validation.isValidSSN(search)) {
                    showAlert("Invalid SSN. Please enter a valid SSN.");
                    return;
                }
                 // Calls EmployeeDAO to search for an employee by SSN
                Employee employeeBySSN = EmployeeDAO.searchEmployeeBySSN(search);
                 
                // Checks if an employee was found and adds it to the results
                if (employeeBySSN != null) {
                    results.add(employeeBySSN);
                } else {
                   showAlert("No employee found with the specified SSN.");
                }
                break;
 
            case "ID":
                // Validates the Employee ID format using the Validation class
                if (!Validation.isValidEmpID(search)) {
                    showAlert("Invalid Employee ID. Please enter a valid ID.");
                    return;
                }
                try {
                    // Converts the search term to an integer for Employee ID
                    int empID = Integer.parseInt(search);
                    // Calls EmployeeDAO to search for an employee by empID
                    Employee employeeByID = EmployeeDAO.searchEmployeeByEmpID(empID);
 
                    // Checks if an employee was found and adds it to the results
                    if (employeeByID != null) {
                        results.add(employeeByID);
                    } else {
                        showAlert("No employee found with the specified Employee ID.");
                    }
                } catch (NumberFormatException e) {
                    // Catches any error if the Employee ID is not a valid integer
                    showAlert("Invalid empID format. Please enter a numeric value.");
                }
                break;
 
            case "Name":
                // Calls EmployeeDAO to search for employees by name (can be partial matches)
                ObservableList<Employee> employeesByName = EmployeeDAO.searchEmployeeByName(search);
 
                // Checks if any employees were found and adds them to the results
                if (!employeesByName.isEmpty()) {
                    results = employeesByName;
                } else {
                    showAlert("No employees found with the specified name.");
                }
                break;
 
            default:
                // If the criterion is unrecognized, shows an error message
                showAlert("Invalid search criterion selected.");
                return;
        }
 
        // Sets the results in the table to display them to the user
        employeeTable.setItems(results);
    }

    @FXML
    private void switchToMenu() {
        try {
            Parent menuView = FXMLLoader.load(getClass().getResource("/view/main_view.fxml"));
            Scene scene = new Scene(menuView);
            Stage stage = (Stage) menuButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToPayroll()
    {
        try {
            Parent payrollView = FXMLLoader.load(getClass().getResource("/view/payroll.fxml"));
            Scene scene = new Scene(payrollView);
            Stage stage = (Stage) payrollButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEditButtons() {
        editButtons.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    editButton.setOnAction(event -> {
                        Employee employee = getTableView().getItems().get(getIndex());
                        handleEdit(employee);
                    });

                    HBox hBox = new HBox(editButton);
                    hBox.setAlignment(Pos.CENTER);
                    setGraphic(hBox);
                    setText(null);
                }
            }
        });
    }

    private void addDeleteButtons() {
        deleteButtons.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    deleteButton.setOnAction(event -> {
                        Employee employee = getTableView().getItems().get(getIndex());
                        handleDelete(employee);
                    });

                    HBox hBox = new HBox(deleteButton);
                    hBox.setAlignment(Pos.CENTER);
                    setGraphic(hBox);
                    setText(null);
                }
            }
        });
    }

    private void handleEdit(Employee employee/* , Address address*/) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_employee.fxml"));
            Parent root = loader.load();

            EditEmployeeController editController = loader.getController();
            editController.setEmployee(employee);
            //editController.setAddress(address);

            Stage stage = new Stage();
            stage.setTitle("Edit Employee");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(App.getPrimaryStage());
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadEmployeeData(); // refresh
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(Employee employee) {
        try {
            EmployeeDAO.deleteEmployee(employee.getEmpID());
            loadEmployeeData();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error deleting employee!");
        }
    }

    private void loadEmployeeData() {
        employeeData.clear();

        try {
            employeeData = EmployeeDAO.getAllEmployees();
            employeeTable.setItems(employeeData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message)
    {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
