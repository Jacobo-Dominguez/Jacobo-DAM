package org.example;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.*;

public class LecturaJAXB {

    public static void main(String[] args) throws JAXBException{

        // Crear el contexto JAXB
        JAXBContext jaxbContext = JAXBContext.newInstance(Pescaderia.class);

        // Crear unmarshaller
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // Cargar XML
        Pescaderia pescaderia = (Pescaderia) unmarshaller.unmarshal(new File("src/main/resources/Pescaderia.xml"));

        // Mostrar datos
        System.out.println("\n ---Pescados: ---");
        ArrayList<Pescado> pescados = pescaderia.getPescados();
        for (Pescado pescado : pescados) {
            System.out.println("Nombre: " + pescado.getNombre() + ", Tipo: " + pescado.getTipo() +
                    ", Temporada: " + pescado.getTemporada() + ", Procedencia: " + pescado.getProcedencia());
        }

        System.out.println("\n ---Mariscos: ---");
        ArrayList<Marisco> mariscos = pescaderia.getMariscos();
        for (Marisco marisco : mariscos) {
            System.out.println("Nombre: " + marisco.getNombre() +
                    ", Temporada: " + marisco.getTemporada() + ", Procedencia: " + marisco.getProcedencia());
        }

        System.out.println("\n--- Buscar pescados por estación (primavera) ---");
        String estacionBuscada = "primavera";
        ArrayList<Pescado> pescadosEstacion = pescaderia.buscarPescadosPorEstacion(estacionBuscada);
        for (Pescado pescado : pescadosEstacion) {
            System.out.println("Nombre: " + pescado.getNombre() + ", Tipo: " + pescado.getTipo() +
                    ", Temporada: " + pescado.getTemporada() + ", Procedencia: " + pescado.getProcedencia());
        }

        System.out.println("\n--- Buscar mariscos por estación (otoño) ---");
        String estacionBuscada2 = "otoño";
        ArrayList<Marisco> mariscosEstacion = pescaderia.buscarMariscosPorEstacion(estacionBuscada2);
        for (Marisco  marisco : mariscosEstacion) {
            System.out.println("Nombre: " + marisco.getNombre() + ", Temporada: " + marisco.getTemporada()
                    + ", Procedencia: " + marisco.getProcedencia());
        }

    }
}
