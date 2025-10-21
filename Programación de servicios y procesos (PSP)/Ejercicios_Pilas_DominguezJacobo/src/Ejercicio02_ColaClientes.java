import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Ejercicio02_ColaClientes {
    /*
    Se requiere la simulación de una fila de clientes atendidos por orden de llegada usando una COLA.
    Se gestionan por consola; la llegada del cliente (se encola su nombre), atención (se
    desencola y se muestra el atendido) y la consulta del estado de la cola
     */

    public void ejecutar() {
        Scanner sc = new Scanner(System.in);
        Queue<String> cola = new LinkedList<String>();
        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1) Llegada de cliente");
            System.out.println("2) Atender cliente");
            System.out.println("3) Mostrar cola");
            System.out.println("4) Volver al menú principal");
            System.out.print("Elige opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {

                case 1:
                    System.out.print("Nombre del cliente: ");
                    String cliente = sc.nextLine();
                    cola.add(cliente);
                    System.out.println(cliente + " ha llegado y se ha añadido a la cola.");
                    break;

                case 2:
                    if (!cola.isEmpty()) {
                        System.out.println("Atendiendo a: " + cola.remove());
                    } else {
                        System.out.println("No hay clientes en la cola.");
                    }
                    break;

                case 3:
                    if (cola.isEmpty()) {
                        System.out.println("Cola vacía.");
                    } else {
                        System.out.println("Clientes en la cola: " + cola);
                    }
                    break;

                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }while (opcion != 4);
    }
}
