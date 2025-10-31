package ejercicio2;

import java.util.*;

public class MainListin {
    public static void main(String[] args) {
        ArbolContactos listin = new ArbolContactos();

        listin.insertar(new Contacto("Ana", "García", "1111", "ana@gmail.com"));
        listin.insertar(new Contacto("Luis", "Fernández", "2222", "luis@hotmail.com"));
        listin.insertar(new Contacto("María", "García", "3333", "maria@yahoo.com"));

        System.out.println("Listín Telefónico:");
        listin.mostrarInOrden();

        System.out.println("\nBúsqueda por prefijo 'Gar':");
        for (Contacto c : listin.buscarPorPrefijo("Gar"))
            System.out.println(" - " + c);

        System.out.println("\nBuscar teléfono 3333:");
        Contacto encontrado = listin.buscarPorTelefono("3333");
        System.out.println(encontrado != null ? encontrado : "No encontrado.");
    }
}