module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;

    opens org.example to javafx.fxml;
    exports org.example to javafx.graphics;
}
