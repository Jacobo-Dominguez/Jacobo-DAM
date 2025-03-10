package UML;

public class Tienda {
    private String nombre;
    private String propietario;
    private int identidficadorTributario;
    private int numeroComputadores;
    private Computador[] computadores;

    public Tienda(String nombre, String propietario, int identidficadorTributario, int capacidad) {
        this.nombre = nombre;
        this.propietario = propietario;
        this.identidficadorTributario = identidficadorTributario;
        this.numeroComputadores = 0;
        this.computadores = new Computador[capacidad];
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public int getIdentidficadorTributario() {
        return identidficadorTributario;
    }

    public void setIdentidficadorTributario(int identidficadorTributario) {
        this.identidficadorTributario = identidficadorTributario;
    }

    public int getNumeroComputadores() {
        return numeroComputadores;
    }

    public void setNumeroComputadores(int numeroComputadores) {
        this.numeroComputadores = numeroComputadores;
    }

    public Computador[] getComputadores() {
        return computadores;
    }

    public void setComputadores(Computador[] computadores) {
        this.computadores = computadores;
    }

    public boolean tiendaLlena(){
        return numeroComputadores == computadores.length;
    }

    public boolean tiendaVacia(){
        return numeroComputadores == 0;
    }

    public void añadir(Computador computador){
        if (!tiendaLlena()){
            computadores[numeroComputadores] = computador;
            numeroComputadores++;
        }else{
            System.out.println("La tienda esta llena");
        }
    }

    public boolean eliminar(String marcaComputador){
        for (int i = 0; i < numeroComputadores; i++) {
            if (computadores[i].getMarca().equalsIgnoreCase(marcaComputador)) {
                // Mover los computadores restantes una posición hacia la izquierda
                for (int j = i; j < numeroComputadores - 1; j++) {
                    computadores[j] = computadores[j + 1];
                }
                computadores[numeroComputadores - 1] = null; // Eliminar referencia
                numeroComputadores--; // Reducir el contador
                return true; // Se eliminó correctamente
            }
        }
        return false; // No se encontró el computador
    }

    public int buscar(String marca){
        for (int i = 0; i < numeroComputadores; i++) {
            if (computadores[i].getMarca().equalsIgnoreCase(marca)) {
                return i; // Retorna el índice del computador encontrado
            }
        }
        return -1; // No se encontró el computador
    }


    public String imprimir() {
        return "Tienda{" +
                "nombre='" + nombre + '\'' +
                ", propietario='" + propietario + '\'' +
                ", identidficadorTributario=" + identidficadorTributario + '\'' +
                ", numeroComputadores=" + numeroComputadores +
                '}';
    }
}
