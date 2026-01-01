module hellofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens business to javafx.fxml; // Gives access to fxml files
    exports business; // Exports the class inheriting from javafx.application.Application
}