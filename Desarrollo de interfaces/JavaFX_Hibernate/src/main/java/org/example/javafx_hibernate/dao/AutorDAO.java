package org.example.javafx_hibernate.dao;


import org.example.javafx_hibernate.entities.Autor;
import java.util.List;

public interface AutorDAO {
    List<Autor> findAll();
    Autor findById(Long id);
    List<Autor> findByNombre(String nombre);
    Autor create(Autor autor);
    Autor update(Autor autor);
    boolean deleteById(Long id);
}
