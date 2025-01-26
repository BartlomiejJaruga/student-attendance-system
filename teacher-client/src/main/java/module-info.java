module com.example.teacherclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires spring.web;

    opens teacherclient to javafx.fxml;
    exports teacherclient;

    opens entities to javafx.base;
    exports entities;

    opens controllers to javafx.fxml;
    exports controllers;

    opens utils to javafx.fxml;
    exports utils;

}