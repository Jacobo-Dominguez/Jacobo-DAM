package org.example.dao;

import org.example.entities.Animal;
import java.util.List;

/**
 * DAO (Data Access Object)
 *
 * Create / Read / Update / Delete
 */
public interface AnimalDAO {

    /**
     * @return todos los animales
     */
    List<Animal> findAll();

    /**
     * @param id
     * @return devuelve un animal por un id concreto
     */
    Animal findById(Integer id);

    /**
     * @param especie
     * @return devuelve más de un animal por especie
     */
    List<Animal> findByEspecie(String especie);

    // Inserta un nuevo registro
    Animal create(Animal animal);

    // Actualizar
    Animal update(Animal animal);

    /**
     * @param id
     * @return borra un id concreto
     */
    boolean deleteById(Integer id);

    /**
     * Actualiza solo el estado de un animal concreto
     * @param id id del animal
     * @param nuevoEstado nuevo valor para el campo estado
     * @return true si se ha actualizado, false si no existe el animal
     */
    boolean updateEstado(Integer id, String nuevoEstado);
}