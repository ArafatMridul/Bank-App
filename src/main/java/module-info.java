module com.example.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires javafx.graphics;


    opens com.example.gothamcitybank to javafx.fxml;
    exports com.example.gothamcitybank;
    exports com.example.gothamcitybank.Controllers;
    exports com.example.gothamcitybank.Controllers.Admin;
    exports com.example.gothamcitybank.Controllers.Client;
    exports com.example.gothamcitybank.Models;
    exports com.example.gothamcitybank.Views;
}