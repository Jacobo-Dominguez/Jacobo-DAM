package model;

import model.Categoria;
import model.Producto;
import model.Usuario;

import java.util.List;
import java.util.Scanner;

public class Tienda {
    private String nombre;
    private List<Categoria> categorias;
    private List<Usuario> usuarios;

    // Getters y setters
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Categoria> getCategorias() { return categorias; }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario seleccionarUsuario(Scanner sc) {
        System.out.println("\n--- USUARIOS ---");
        for (int i = 0; i < usuarios.size(); i++)
            System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
        System.out.print("Seleccione usuario: ");
        int opcion = Integer.parseInt(sc.nextLine());
        return usuarios.get(opcion - 1);
    }

    public void mostrarProductosPorCategoria(Scanner sc) {
        System.out.println("\n--- CATEGORÍAS ---");
        for (int i = 0; i < categorias.size(); i++)
            System.out.println((i + 1) + ". " + categorias.get(i).getNombre());
        System.out.print("Seleccione categoría: ");
        int opcion = Integer.parseInt(sc.nextLine());
        categorias.get(opcion - 1).mostrarProductos();
    }

    public void hacerCompra(Usuario usuario, Scanner sc) {
        mostrarProductosPorCategoria(sc);
        System.out.print("Seleccione categoría: ");
        int catIndex = Integer.parseInt(sc.nextLine()) - 1;
        Categoria categoria = categorias.get(catIndex);
        Producto producto = categoria.seleccionarProducto(sc);
        usuario.comprarProducto(producto, sc);
    }
}