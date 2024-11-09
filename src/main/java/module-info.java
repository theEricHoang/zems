module com.z {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;

    requires io.github.cdimascio.dotenv.java;

    requires java.sql;

    opens com.z to javafx.fxml;
    exports com.z;
    
    opens com.z.controller to javafx.fxml;
    exports com.z.controller;
}
