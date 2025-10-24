package model;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String descripcion;
    private Caracteristicas caracteristicas;
    private long inventario;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Caracteristicas getCaracteristicas() { return caracteristicas; }
    public void setCaracteristicas(Caracteristicas caracteristicas) { this.caracteristicas = caracteristicas; }

    public long getInventario() { return inventario; }
    public void setInventario(long inventario) { this.inventario = inventario; }

    @Override
    public String toString() {
        return nombre + " - " + precio + "€ (" + inventario + " disponibles)";
    }
}
