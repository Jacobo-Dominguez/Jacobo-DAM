package ejercicio2;

public class Contacto implements Comparable<Contacto> {
    String nombre;
    String apellidos;
    String telefono;
    String email;

    public Contacto(String nombre, String apellidos, String telefono, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public int compareTo(Contacto otro) {
        int cmp = this.apellidos.compareToIgnoreCase(otro.apellidos);
        if (cmp == 0) return this.nombre.compareToIgnoreCase(otro.nombre);
        return cmp;
    }

    @Override
    public String toString() {
        return apellidos + ", " + nombre + " - " + telefono + (email != null ? " (" + email + ")" : "");
    }
}
