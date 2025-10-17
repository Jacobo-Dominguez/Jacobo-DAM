package org.example;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "marisco")
@XmlAccessorType(XmlAccessType.FIELD)

public class Marisco {

    @XmlElement(name="nombre")
    private String nombre;

    @XmlElement(name="temporada")
    private String temporada;

    @XmlElement(name="procedencia")
    private String procedencia;

    public Marisco() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }
}
