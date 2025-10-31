package ejercicio2;

public class NodoContacto {
    Contacto contacto;
    NodoContacto izquierda, derecha;

    public NodoContacto(Contacto contacto) {
        this.contacto = contacto;
        this.izquierda = this.derecha = null;
    }
}