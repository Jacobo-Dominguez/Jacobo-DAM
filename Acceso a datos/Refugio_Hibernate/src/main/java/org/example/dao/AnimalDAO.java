package org.example.dao;

import org.example.entities.Animal;
import java.util.List;

public interface AnimalDAO {

    // Devuelve todos los animales
    List<Animal> findAll();

    // Devuelve un animal para un id concreto
    Animal findById(Integer id);

    // Devuelve más de un animal por especie
    List<Animal> findByEspecie(String especie);

    // Inserta un nuevo registro
    Animal create(Animal animal);

    // Actualizar
    Animal update(Animal animal);

    // Borra un id concreto
    boolean deleteById(Integer id);

    // Actualiza el estado de un animal en concreto por id
    boolean updateEstado(Integer id, String nuevoEstado);
}