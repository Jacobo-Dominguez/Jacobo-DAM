import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Ejercicio03_Palindromo {
    /*
    Nos solicitan que verifiquemos si una palabra o frase es palíndroma utilizando
    simultáneamente PILA (lectura inversa) y COLA (lectura directa)
    Se debe normalizar el texto (minúsculas y quitar espacios). Se insertan los
    caracteres en una pila y en una cola y se comparan elementos correspondientes al
    desapilar y desencolar.
     */

    public void ejecutar() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce una palabra o frase: ");
        String texto = sc.nextLine();

        // Normalizar texto: quitar espacios y poner minúsculas
        texto = texto.replaceAll("\\s+", "").toLowerCase();

        Stack<Character> pila = new Stack<>();
        Queue<Character> cola = new LinkedList<>();

        for (char c : texto.toCharArray()) {
            pila.push(c);
            cola.add(c);
        }

        boolean esPalindromo = true;
        while (!pila.isEmpty() && !cola.isEmpty()) {
            if (!pila.pop().equals(cola.remove())) {
                esPalindromo = false;
                break;
            }
        }

        if (esPalindromo)
            System.out.println("Es un palíndromo.");
        else
            System.out.println("No es un palíndromo.");

    }

}
