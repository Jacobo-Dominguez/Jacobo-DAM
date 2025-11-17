package org.example.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @Column(length = 10)
    private String dni;

    private String nombre;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "persona_animal",
            joinColumns = @JoinColumn(name = "dni_persona"),
            inverseJoinColumns = @JoinColumn(name = "id_animal")
    )
    private Set<Animal> animales = new HashSet<>();

    public Persona() {}

    public Persona(String dni, String nombre, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
    }

    // GETTERS/SETTERS

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<Animal> getAnimales() { return animales; }
    public void setAnimales(Set<Animal> animales) { this.animales = animales; }
}