package com.z.controller;

import com.z.App;
import com.z.model.Employee;
import com.z.model.dao.EmployeeDAO;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeesController {
    /*
     * COMPONENT DECLARATIONS
     * separate lines because that's how java annotations work :(
     */
    @FXML private Button menuButton;
    @FXML private Button salariesButton;
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
    @FXML private TableColumn<Employee, Void> editButtons;
    @FXML private TableColumn<Employee, Void> deleteButtons;
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    /*
     * Searchbar and Options
     */
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> searchChoice;

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

        searchChoice.getItems().addAll("ID", "First Name", "Last Name", "SSN");

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
    private void switchToSalaries()
    {
        try {
            Parent salariesView = FXMLLoader.load(getClass().getResource("/view/salaries.fxml"));
            Scene scene = new Scene(salariesView);
            Stage stage = (Stage) salariesButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEditButtons() {
        editButtons.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            {
                editButton.setOnAction(event -> {
                    Employee employee = getTableView().getItems().get(getIndex());
                    handleEdit(employee);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
    }

    private void addDeleteButtons() {
        deleteButtons.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            {
                deleteButton.setOnAction(event -> {
                    Employee employee = getTableView().getItems().get(getIndex());
                    handleDelete(employee);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void handleEdit(Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_employee.fxml"));
            Parent root = loader.load();

            EditEmployeeController editController = loader.getController();
            editController.setEmployee(employee);

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
