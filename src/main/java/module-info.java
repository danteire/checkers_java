module org.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.dlsc.formsfx;
    requires eu.hansolo.tilesfx;
    requires java.desktop;

    opens org.example.demo1 to javafx.fxml;
    exports org.example.demo1;
}