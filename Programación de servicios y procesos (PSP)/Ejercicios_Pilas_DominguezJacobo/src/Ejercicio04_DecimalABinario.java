import java.util.Scanner;
import java.util.Stack;

public class Ejercicio04_DecimalABinario {
    /*
    Realizar la conversión de un entero decimal positivo a binario a través de una PILA.
    Se deben ir apilando los restos de las divisiones entre 2, y al desapilar la operación,
    obtenemos el binario.
     */

    public void ejecutar() {

        Scanner sc = new Scanner(System.in);
        Stack<Integer> pila = new Stack<>();

        System.out.print("Introduce un número entero positivo: ");
        int num = sc.nextInt();

        if (num == 0) {
            System.out.println("Binario: 0");
            return;
        }

        // Apilar restos
        while (num > 0) {
            pila.push(num % 2);
            num = num / 2;
        }

        System.out.print("Binario: ");
        while (!pila.isEmpty()) {
            System.out.print(pila.pop());
        }
        System.out.println();
    }
}
