package org.example.dao;

import org.example.entities.Persona;
import java.util.List;

public interface PersonaDAO {

    List<Persona> findAll();

    Persona findByDni(String dni);

    Persona create(Persona persona);

    Persona update(Persona persona);

    boolean deleteByDni(String dni);
}
