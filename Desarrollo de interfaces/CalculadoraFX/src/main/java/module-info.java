module org.example.calculadorafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.calculadorafx to javafx.fxml;
    exports org.example.calculadorafx;
}