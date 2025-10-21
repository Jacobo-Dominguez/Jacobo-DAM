import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1) Invertir texto (PILA)");
            System.out.println("2) Simular fila de clientes (COLA)");
            System.out.println("3) Verificar palíndromo (PILA y COLA)");
            System.out.println("4) Decimal a binario (PILA)");
            System.out.println("5) Salir");
            System.out.print("Elige opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> new Ejercicio01_InvertirTexto().ejecutar();
                case 2 -> new Ejercicio02_ColaClientes().ejecutar();
                case 3 -> new Ejercicio03_Palindromo().ejecutar();
                case 4 -> new Ejercicio04_DecimalABinario().ejecutar();
                case 5 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }
}
