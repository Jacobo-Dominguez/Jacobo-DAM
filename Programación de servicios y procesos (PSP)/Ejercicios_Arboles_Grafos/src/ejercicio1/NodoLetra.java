package ejercicio1;

public class NodoLetra {
    char letra;
    NodoLetra izquierda;
    NodoLetra derecha;

    public NodoLetra(char letra) {
        this.letra = letra;
        this.izquierda = null;
        this.derecha = null;
    }
}