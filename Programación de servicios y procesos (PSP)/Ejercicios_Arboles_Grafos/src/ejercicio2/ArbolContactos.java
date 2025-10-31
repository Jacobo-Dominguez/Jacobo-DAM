package ejercicio2;

import java.util.*;

public class ArbolContactos {
    private NodoContacto raiz;

    public void insertar(Contacto c) {
        raiz = insertarRec(raiz, c);
    }

    private NodoContacto insertarRec(NodoContacto actual, Contacto c) {
        if (actual == null) return new NodoContacto(c);
        if (c.compareTo(actual.contacto) < 0)
            actual.izquierda = insertarRec(actual.izquierda, c);
        else if (c.compareTo(actual.contacto) > 0)
            actual.derecha = insertarRec(actual.derecha, c);
        else
            System.out.println("Contacto duplicado: " + c);
        return actual;
    }

    public Contacto buscarPorTelefono(String telefono) {
        return buscarTelefonoRec(raiz, telefono);
    }

    private Contacto buscarTelefonoRec(NodoContacto actual, String telefono) {
        if (actual == null) return null;
        if (actual.contacto.telefono.equals(telefono)) return actual.contacto;
        Contacto izq = buscarTelefonoRec(actual.izquierda, telefono);
        if (izq != null) return izq;
        return buscarTelefonoRec(actual.derecha, telefono);
    }

    public void mostrarInOrden() {
        mostrarInOrdenRec(raiz);
    }

    private void mostrarInOrdenRec(NodoContacto nodo) {
        if (nodo == null) return;
        mostrarInOrdenRec(nodo.izquierda);
        System.out.println(nodo.contacto);
        mostrarInOrdenRec(nodo.derecha);
    }

    public List<Contacto> buscarPorPrefijo(String prefijo) {
        List<Contacto> resultados = new ArrayList<>();
        buscarPrefijoRec(raiz, prefijo.toLowerCase(), resultados);
        return resultados;
    }

    private void buscarPrefijoRec(NodoContacto nodo, String prefijo, List<Contacto> lista) {
        if (nodo == null) return;
        if (nodo.contacto.apellidos.toLowerCase().startsWith(prefijo))
            lista.add(nodo.contacto);
        buscarPrefijoRec(nodo.izquierda, prefijo, lista);
        buscarPrefijoRec(nodo.derecha, prefijo, lista);
    }
}
