module mvc.pbl {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.desktop;

    opens mvc.pbl to javafx.fxml;
    exports mvc.pbl;
    exports mvc.pbl.controller;
    opens mvc.pbl.controller to javafx.fxml;
}