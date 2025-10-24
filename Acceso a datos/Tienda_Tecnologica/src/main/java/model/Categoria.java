package model;

import java.util.List;
import java.util.Scanner;

public class Categoria {
    private int id;
    private String nombre;
    private List<Producto> productos;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }

    public void mostrarProductos() {
        System.out.println("\n--- PRODUCTOS EN " + nombre + " ---");
        for (int i = 0; i < productos.size(); i++) {
            System.out.println((i + 1) + ". " + productos.get(i));
        }
    }

    public Producto seleccionarProducto(Scanner sc) {
        mostrarProductos();
        System.out.print("Seleccione producto: ");
        int opcion = Integer.parseInt(sc.nextLine()) - 1;
        return productos.get(opcion);
    }
}
