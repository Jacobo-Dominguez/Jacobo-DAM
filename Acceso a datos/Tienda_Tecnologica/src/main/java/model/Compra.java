package model;

public class Compra {
    private int productoId;
    private int cantidad;
    private String fecha;

    public Compra(int productoId, int cantidad, String fecha) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getProductoId() { return productoId; }
    public int getCantidad() { return cantidad; }
    public String getFecha() { return fecha; }

    @Override
    public String toString() {
        return "- Producto ID: " + productoId + ", Cantidad: " + cantidad + ", Fecha: " + fecha;
    }
}
