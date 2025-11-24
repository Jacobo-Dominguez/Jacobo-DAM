module org.example.javafx_hibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    // Para la aplicación principal
    opens org.example.javafx_hibernate to javafx.fxml;

    // Para Hibernate (entidades)
    // 🔑 PARA TableView: acceso a getters desde PropertyValueFactory
    opens org.example.javafx_hibernate.entities to org.hibernate.orm.core, javafx.base;

    // Para JavaFX FXML (controladores)
    opens org.example.javafx_hibernate.controller to javafx.fxml;

    // Exportar paquetes principales
    exports org.example.javafx_hibernate;
}