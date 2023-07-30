module com.social {
    requires javafx.controls;
    requires bluecove;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.social to javafx.fxml;
    exports com.social;
}
