package org.example;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pescado")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pescado {

    @XmlElement(name="nombre")
    private String nombre;

    @XmlElement(name="tipo")
    private String tipo;

    @XmlElement(name="temporada")
    private String temporada;

    @XmlElement(name="procedencia")
    private String procedencia;

    public Pescado() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return  tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
