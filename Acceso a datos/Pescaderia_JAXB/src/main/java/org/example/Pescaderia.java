package org.example;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement(name = "pescaderia")
@XmlType(propOrder = {"pescados", "mariscos"})
@XmlAccessorType(XmlAccessType.FIELD)

public class Pescaderia {
    @XmlElementWrapper(name = "pescados")
    @XmlElement(name = "pescado")
    private ArrayList<Pescado> pescados = new ArrayList<>();

    @XmlElementWrapper(name = "mariscos")
    @XmlElement(name = "marisco")
    private ArrayList<Marisco> mariscos = new ArrayList<>();

    public Pescaderia() {}

    public ArrayList<Pescado> getPescados() {
        return pescados;
    }

    public void setPescados(ArrayList<Pescado> pescados) {
        this.pescados = pescados;
    }

    public ArrayList<Marisco> getMariscos() {
        return mariscos;
    }

    public void setMariscos(ArrayList<Marisco> mariscos) {
        this.mariscos = mariscos;
    }

    public ArrayList<Pescado> buscarPescadosPorEstacion(String estacion) {
        ArrayList<Pescado> resultado = new ArrayList<>();
        ArrayList<String> meses = obtenerMesesPorEstacion(estacion);
        for (Pescado pescado : pescados) {
            if (pescado.getTemporada() != null) {
                for (String mes : meses) {
                    if (pescado.getTemporada().contains(mes)) {
                        resultado.add(pescado);
                        break;
                    }
                }
            }
        }
        return resultado;
    }

    public ArrayList<Marisco> buscarMariscosPorEstacion(String estacion) {
        ArrayList<Marisco> resultado = new ArrayList<>();
        ArrayList<String> meses = obtenerMesesPorEstacion(estacion);
        for (Marisco marisco : mariscos) {
            if (marisco.getTemporada() != null) {
                for (String mes : meses) {
                    if (marisco.getTemporada().contains(mes)) {
                        resultado.add(marisco);
                        break;
                    }
                }
            }
        }
        return resultado;
    }

    private ArrayList<String> obtenerMesesPorEstacion(String estacion) {
        ArrayList<String> meses = new ArrayList<>();
        switch (estacion.toLowerCase()) {
            case "invierno":
                meses.add("12"); meses.add("01"); meses.add("02");
                break;
            case "primavera":
                meses.add("03"); meses.add("04"); meses.add("05");
                break;
            case "verano":
                meses.add("06"); meses.add("07"); meses.add("08");
                break;
            case "otoño":
                meses.add("09"); meses.add("10"); meses.add("11");
                break;
        }
        return meses;
    }

}
