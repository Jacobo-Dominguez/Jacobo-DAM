package org.example;


import model.Tienda;
import model.Usuario;
import entrada_salida.EscritorJSON;
import entrada_salida.LectorJSON;

import java.util.Scanner;

public class MenuTienda {
    private Tienda tienda;
    private Scanner sc;

    public MenuTienda() {
        sc = new Scanner(System.in);
        tienda = LectorJSON.cargarTienda();
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n==== MENÚ TIENDA ====");
            System.out.println("1. Ver datos del usuario");
            System.out.println("2. Ver productos de una categoría");
            System.out.println("3. Ver historial de compras de un usuario");
            System.out.println("4. Hacer una compra");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    Usuario usuario1 = tienda.seleccionarUsuario(sc);
                    usuario1.mostrarDatos();
                    break;
                case 2:
                    tienda.mostrarProductosPorCategoria(sc);
                    break;
                case 3:
                    Usuario usuario3 = tienda.seleccionarUsuario(sc);
                    usuario3.mostrarHistorial();
                    break;
                case 4:
                    Usuario usuario4 = tienda.seleccionarUsuario(sc);
                    tienda.hacerCompra(usuario4, sc);
                    EscritorJSON.guardarTienda(tienda);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
}

