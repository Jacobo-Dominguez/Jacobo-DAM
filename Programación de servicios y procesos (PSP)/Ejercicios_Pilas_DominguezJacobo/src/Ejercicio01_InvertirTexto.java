import java.util.Scanner;
import java.util.Stack;

public class Ejercicio01_InvertirTexto {
    /*
    Generar una aplicación de consola que invierta el texto introducido usando
    exclusivamente operaciones de una PILA.
    Se recibe una línea de texto. Cada carácter se apila y luego se desapila para
    construir la cadena invertida. No se permite recorrer hacia atrás ni usar utilidades
    de inversión directa.
    Se deberá realizar las operaciones PUSH, POP y ISEMPTY
     */

    public void ejecutar() {
        Scanner sc = new Scanner(System.in);
        Stack<Character> pila = new Stack<>();

        System.out.println("Introduce el texto");
        String texto = sc.nextLine();

        // Apilar cada caracter
        for(char c : texto.toCharArray()) {
            pila.push(c);
        }

        // Desapilar para invertir el texto
        StringBuilder invertido = new StringBuilder();
        while(!pila.isEmpty()) {
            invertido.append(pila.pop());
        }

        System.out.println("Texto invertido: " + invertido);
    }
}
