module com.example.projekt_java {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projekt_java to javafx.fxml;
    exports com.example.projekt_java;
}