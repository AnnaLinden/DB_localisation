module com.example.db_localisation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    requires org.kordamp.bootstrapfx.core;

    opens com.example.db_localisation to javafx.fxml;
    exports com.example.db_localisation;
}