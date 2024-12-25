module com.example.skyline {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires org.hibernate.orm.core;

    exports application;
    exports controller;
    opens controller to javafx.fxml;
    opens database to javafx.base, javafx.fxml, org.hibernate.orm.core;
    opens model to javafx.base, org.hibernate.orm.core;


    requires java.naming;
    requires java.sql;
    requires mysql.connector.java;

    requires annotations;

    requires java.desktop;

}