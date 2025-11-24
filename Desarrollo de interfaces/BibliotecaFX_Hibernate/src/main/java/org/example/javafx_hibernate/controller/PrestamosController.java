package org.example.javafx_hibernate.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.javafx_hibernate.dao.*;
import org.example.javafx_hibernate.entities.Libro;
import org.example.javafx_hibernate.entities.Prestamo;
import org.example.javafx_hibernate.entities.Socio;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PrestamosController {
    @FXML private ComboBox<Libro> comboLibro;
    @FXML private ComboBox<Socio> comboSocio;
    @FXML private TableView<Prestamo> tablaPrestamos;
    @FXML private TableColumn<Prestamo, String> colLibro;
    @FXML private TableColumn<Prestamo, String> colSocio;
    @FXML private TableColumn<Prestamo, String> colFechaPrestamo;

    private final LibroDAO libroDAO = new LibroDAOImpl();
    private final SocioDAO socioDAO = new SocioDAOImpl();
    private final PrestamoDAO prestamoDAO = new PrestamoDAOImpl();
    private final ObservableList<Prestamo> prestamosData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colLibro.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getLibro().getTitulo()
                )
        );
        colSocio.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getSocio().getNombre()
                )
        );
        colFechaPrestamo.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getFechaPrestamo().toString()
                )
        );

        tablaPrestamos.setItems(prestamosData);
        cargarCombos();
        listarPrestamosActivos();
    }

    private void cargarCombos() {
        List<Libro> librosDisponibles = libroDAO.findDisponibles();
        comboLibro.setItems(FXCollections.observableArrayList(librosDisponibles));

        List<Socio> socios = socioDAO.findAll();
        comboSocio.setItems(FXCollections.observableArrayList(socios));
    }

    @FXML
    private void onRegistrarPrestamo() {
        Libro libro = comboLibro.getValue();
        Socio socio = comboSocio.getValue();

        if (libro == null || socio == null) {
            showAlert("Error", "Selecciona un libro disponible y un socio.");
            return;
        }

        Prestamo prestamo = new Prestamo(libro, socio, LocalDate.now());
        prestamoDAO.create(prestamo);

        // Marcar libro como no disponible
        libro.setDisponible(false);
        libroDAO.update(libro);

        showAlert("Éxito", "Préstamo registrado.");
        cargarCombos();
        listarPrestamosActivos();
    }

    @FXML
    private void onDevolverLibro() {
        Prestamo prestamo = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (prestamo == null) {
            showAlert("Advertencia", "Selecciona un préstamo activo.");
            return;
        }

        // Marcar como devuelto
        if (prestamoDAO.marcarComoDevuelto(prestamo.getId())) {
            // Actualizar libro
            Libro libro = prestamo.getLibro();
            libro.setDisponible(true);
            libroDAO.update(libro);

            showAlert("Éxito", "Libro devuelto.");
            cargarCombos();
            listarPrestamosActivos();
        } else {
            showAlert("Error", "No se pudo registrar la devolución.");
        }
    }

    @FXML
    private void onVerHistorial() {
        Socio socio = comboSocio.getValue();
        if (socio == null) {
            showAlert("Advertencia", "Selecciona un socio en el combo.");
            return;
        }
        List<Prestamo> historial = prestamoDAO.findHistorialBySocio(socio);
        if (historial.isEmpty()) {
            showAlert("Info", "Sin préstamos en el historial.");
        } else {
            String historialTexto = historial.stream()
                    .map(p -> p.getLibro().getTitulo() + " - " +
                            (p.isDevuelto() ? "Devuelto el " + p.getFechaDevolucion() : "Prestado"))
                    .collect(Collectors.joining("\n"));
            new Alert(Alert.AlertType.INFORMATION, historialTexto).showAndWait();
        }
    }

    @FXML
    private void onActualizarListas() {
        cargarCombos();
        listarPrestamosActivos();
    }

    private void listarPrestamosActivos() {
        List<Prestamo> activos = prestamoDAO.findPrestamosActivos();
        prestamosData.setAll(activos);
    }

    private void showAlert(String titulo, String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }
}
