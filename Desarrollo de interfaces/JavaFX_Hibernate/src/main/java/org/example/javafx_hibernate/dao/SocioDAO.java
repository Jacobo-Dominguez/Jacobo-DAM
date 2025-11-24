package org.example.javafx_hibernate.dao;

import org.example.javafx_hibernate.entities.Socio;
import java.util.List;

public interface SocioDAO {
    List<Socio> findAll();
    Socio findById(Long id);
    List<Socio> findByNombre(String nombre);
    List<Socio> findByTelefono(String telefono);
    Socio create(Socio socio);
    Socio update(Socio socio);
    boolean deleteById(Long id);
}
