module com.z {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.z to javafx.fxml;
    exports com.z;
}
