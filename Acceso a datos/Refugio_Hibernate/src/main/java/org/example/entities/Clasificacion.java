package org.example.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clasificacion")
public class Clasificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoAnimal tipoAnimal;

    private String nombre;

    @ManyToMany(mappedBy = "clasificaciones")
    private Set<Animal> animales = new HashSet<>();

    public Clasificacion() {}

    public Clasificacion(TipoAnimal tipoAnimal, String nombre) {
        this.tipoAnimal = tipoAnimal;
        this.nombre = nombre;
    }

    // GETTERS y SETTERS

    public Integer getId() { return id; }

    public TipoAnimal getTipoAnimal() { return tipoAnimal; }
    public void setTipoAnimal(TipoAnimal tipoAnimal) { this.tipoAnimal = tipoAnimal; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Set<Animal> getAnimales() { return animales; }
    public void setAnimales(Set<Animal> animales) { this.animales = animales; }
}