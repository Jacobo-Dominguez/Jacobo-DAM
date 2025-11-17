package org.example.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String especie;

    @Column(length = 500)
    private String descripcionPerdida;

    private String estado; // recién abandonado, tiempo en refugio, próximamente en acogida

    @Enumerated(EnumType.STRING)
    private TipoAlimento tipoAlimento;

    // Persona <-> Animal: ManyToMany -> persona_animal
    @ManyToMany(mappedBy = "animales")
    private Set<Persona> personas = new HashSet<>();

    // Animal <-> Clasificacion: ManyToMany -> animal_clasificacion
    @ManyToMany
    @JoinTable(
            name = "animal_clasificacion",
            joinColumns = @JoinColumn(name = "id_animal"),
            inverseJoinColumns = @JoinColumn(name = "id_clasificacion")
    )
    private Set<Clasificacion> clasificaciones = new HashSet<>();

    public Animal() {}

    public Animal(String nombre, String especie, String descripcionPerdida,
                  String estado, TipoAlimento tipoAlimento) {
        this.nombre = nombre;
        this.especie = especie;
        this.descripcionPerdida = descripcionPerdida;
        this.estado = estado;
        this.tipoAlimento = tipoAlimento;
    }

    // ===== GETTERS/SETTERS =====

    public Integer getId() {
        return id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getDescripcionPerdida() { return descripcionPerdida; }
    public void setDescripcionPerdida(String descripcionPerdida) { this.descripcionPerdida = descripcionPerdida; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public TipoAlimento getTipoAlimento() { return tipoAlimento; }
    public void setTipoAlimento(TipoAlimento tipoAlimento) { this.tipoAlimento = tipoAlimento; }

    public Set<Persona> getPersonas() { return personas; }
    public void setPersonas(Set<Persona> personas) { this.personas = personas; }

    public Set<Clasificacion> getClasificaciones() { return clasificaciones; }
    public void setClasificaciones(Set<Clasificacion> clasificaciones) { this.clasificaciones = clasificaciones; }
}