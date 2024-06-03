module mvc.pbl {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.desktop;

    opens mvc to javafx.fxml;
    exports mvc;
    exports mvc.controller;
    opens mvc.controller to javafx.fxml;
}