package model;
import java.util.List;
import java.util.Scanner;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private Direccion direccion;
    private List<Compra> historialCompras;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }

    public List<Compra> getHistorialCompras() { return historialCompras; }
    public void setHistorialCompras(List<Compra> historialCompras) { this.historialCompras = historialCompras; }

    public void mostrarDatos() {
        System.out.println("\n--- DATOS DEL USUARIO ---");
        System.out.println("Nombre: " + nombre);
        System.out.println("Email: " + email);
        System.out.println("Dirección: " + direccion);
    }

    public void mostrarHistorial() {
        System.out.println("\n--- HISTORIAL DE COMPRAS ---");
        if (historialCompras == null || historialCompras.isEmpty()) {
            System.out.println("Sin compras registradas.");
        } else {
            for (Compra c : historialCompras) {
                System.out.println(c);
            }
        }
    }

    public void comprarProducto(Producto producto, Scanner sc) {
        System.out.print("Cantidad a comprar: ");
        int cantidad = Integer.parseInt(sc.nextLine());
        if (cantidad > producto.getInventario()) {
            System.out.println("No hay suficiente stock.");
            return;
        }
        producto.setInventario(producto.getInventario() - cantidad);
        historialCompras.add(new Compra(producto.getId(), cantidad, "2025-10-20"));
        System.out.println("Compra registrada correctamente.");
    }
}