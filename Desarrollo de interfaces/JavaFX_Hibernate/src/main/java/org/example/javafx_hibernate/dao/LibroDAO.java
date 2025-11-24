package org.example.javafx_hibernate.dao;

import org.example.javafx_hibernate.entities.Libro;
import java.util.List;

public interface LibroDAO {
    List<Libro> findAll();
    Libro findById(Long id);
    Libro findByIsbn(String isbn);
    List<Libro> findByTitulo(String titulo);
    List<Libro> findByAutorNombre(String nombre);
    List<Libro> findDisponibles(); // no prestados
    Libro create(Libro libro);
    Libro update(Libro libro);
    boolean deleteById(Long id);
}