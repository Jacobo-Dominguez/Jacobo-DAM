package entrada_salida;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LectorJSON {

    public static Tienda cargarTienda() {
        Tienda tienda = new Tienda();
        try {
            JSONParser parser = new JSONParser();
            JSONObject raiz = (JSONObject) parser.parse(new FileReader("src/main/resources/tienda.json"));
            JSONObject tiendaJson = (JSONObject) raiz.get("tienda");

            tienda.setNombre((String) tiendaJson.get("nombre"));

            // Cargar usuarios
            List<Usuario> usuarios = new ArrayList<>();
            JSONArray usuariosJson = (JSONArray) tiendaJson.get("usuarios");
            for (Object o : usuariosJson) {
                JSONObject u = (JSONObject) o;
                Usuario usuario = new Usuario();
                usuario.setId((int) (long) u.get("id"));
                usuario.setNombre((String) u.get("nombre"));
                usuario.setEmail((String) u.get("email"));

                // Dirección
                JSONObject dir = (JSONObject) u.get("direccion");
                Direccion direccion = new Direccion();
                direccion.setCalle((String) dir.get("calle"));
                direccion.setNumero((int) (long) dir.get("numero"));
                direccion.setCiudad((String) dir.get("ciudad"));
                direccion.setPais((String) dir.get("pais"));
                usuario.setDireccion(direccion);

                // Historial compras
                List<Compra> historial = new ArrayList<>();
                JSONArray comprasJson = (JSONArray) u.get("historialCompras");
                if (comprasJson != null) {
                    for (Object cObj : comprasJson) {
                        JSONObject c = (JSONObject) cObj;
                        Compra compra = new Compra(
                                (int) (long) c.get("productoId"),
                                (int) (long) c.get("cantidad"),
                                (String) c.get("fecha")
                        );
                        historial.add(compra);
                    }
                }
                usuario.setHistorialCompras(historial);
                usuarios.add(usuario);
            }
            tienda.setUsuarios(usuarios);

            // Cargar categorías y productos
            List<Categoria> categorias = new ArrayList<>();
            JSONArray categoriasJson = (JSONArray) tiendaJson.get("categorias");
            for (Object o : categoriasJson) {
                JSONObject c = (JSONObject) o;
                Categoria categoria = new Categoria();
                categoria.setId((int) (long) c.get("id"));
                categoria.setNombre((String) c.get("nombre"));

                List<Producto> productos = new ArrayList<>();
                JSONArray productosJson = (JSONArray) c.get("productos");
                for (Object pObj : productosJson) {
                    JSONObject p = (JSONObject) pObj;
                    Producto producto = new Producto();
                    producto.setId((int) (long) p.get("id"));
                    producto.setNombre((String) p.get("nombre"));
                    producto.setPrecio((double) p.get("precio"));
                    producto.setDescripcion((String) p.get("descripcion"));
                    producto.setInventario((long) p.get("inventario"));

                    // Características
                    JSONObject car = (JSONObject) p.get("caracteristicas");
                    Caracteristicas caracteristicas = new Caracteristicas();
                    caracteristicas.setPantalla((String) car.get("pantalla"));
                    caracteristicas.setCamara((String) car.get("camara"));
                    caracteristicas.setBateria((String) car.get("bateria"));
                    producto.setCaracteristicas(caracteristicas);

                    productos.add(producto);
                }
                categoria.setProductos(productos);
                categorias.add(categoria);
            }
            tienda.setCategorias(categorias);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tienda;
    }
}