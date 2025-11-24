package org.example.javafx_hibernate.dao;

import org.example.javafx_hibernate.entities.Prestamo;
import org.example.javafx_hibernate.entities.Socio;
import java.util.List;

public interface PrestamoDAO {
    List<Prestamo> findAll();
    List<Prestamo> findPrestamosActivos(); // donde fechaDevolucion IS NULL
    List<Prestamo> findHistorialBySocio(Socio socio);
    Prestamo create(Prestamo prestamo);
    boolean marcarComoDevuelto(Long id); // actualiza fechaDevolucion = hoy
}
