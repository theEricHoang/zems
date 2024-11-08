package com.z.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UpdateSalariesController {
    @FXML
    private Button backButton;

    @FXML
    private void closeWindow()
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}