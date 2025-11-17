package org.example.dao;

import org.example.entities.Clasificacion;
import org.example.entities.TipoAnimal;

public interface ClasificacionDAO {

    Clasificacion findByTipoAnimal(TipoAnimal tipoAnimal);

    Clasificacion create(Clasificacion clasificacion);
}
