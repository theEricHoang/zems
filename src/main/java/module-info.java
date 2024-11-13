module com.z {
    requires transitive java.sql;
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;
     

    requires io.github.cdimascio.dotenv.java;

    opens com.z to javafx.fxml;
    exports com.z;

    opens com.z.controller to javafx.fxml;
    exports com.z.controller;

    opens com.z.model to javafx.fxml;
    exports com.z.model;
    exports com.z.model.dao;
}