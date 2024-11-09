package com.z.controller;

import javafx.fxml.FXML;
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