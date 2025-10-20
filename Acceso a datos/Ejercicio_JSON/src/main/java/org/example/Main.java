package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Leer el archivo JSON
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("src/main/java/org/example/tienda.JSON"));
            JSONObject raiz = (JSONObject) obj;
            JSONObject tienda = (JSONObject) raiz.get("tienda");

            JSONArray usuarios = (JSONArray) tienda.get("usuarios");
            JSONObject usuario = (JSONObject) usuarios.get(0); // primer usuario

            JSONArray categorias = (JSONArray) tienda.get("categorias");
            JSONObject categoria = (JSONObject) categorias.get(0); // primera categoría
            JSONArray productos = (JSONArray) categoria.get("productos");

            int opcion;
            do {
                System.out.println("\n==== MENÚ TIENDA ====");
                System.out.print("Seleccione una opción: ");
                System.out.println("1. Ver datos del usuario");
                System.out.println("2. Ver productos");
                System.out.println("3. Ver historial de compras");
                System.out.println("4. Hacer una compra");
                System.out.println("5. Agregar nuevo producto");
                System.out.println("0. Salir");
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        mostrarUsuario(usuario);
                        break;

                    case 2:
                        mostrarProductos(categoria);
                        break;

                    case 3:
                        mostrarHistorial(usuario);
                        break;

                    case 4:
                        hacerCompra(usuario, productos);
                        guardarJSON(raiz);
                        break;

                    case 5:
                        agregarProducto(categoria, sc);
                        guardarJSON(raiz);
                        break;

                    case 0:
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                }

            } while (opcion != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mostrar datos del usuario
    public static void mostrarUsuario(JSONObject usuario) {
        System.out.println("\n--- DATOS DEL USUARIO ---");
        System.out.println("Nombre: " + usuario.get("nombre"));
        System.out.println("Email: " + usuario.get("email"));
        JSONObject direccion = (JSONObject) usuario.get("direccion");
        System.out.println("Dirección: " + direccion.get("calle") + " " + direccion.get("numero") + ", "
                + direccion.get("ciudad") + " (" + direccion.get("pais") + ")");
    }

    // Mostrar productos
    public static void mostrarProductos(JSONObject categoria) {
        System.out.println("\n--- PRODUCTOS EN " + categoria.get("nombre") + " ---");
        JSONArray productos = (JSONArray) categoria.get("productos");
        for (Object p : productos) {
            JSONObject prod = (JSONObject) p;
            System.out.println("ID: " + prod.get("id") + " | " + prod.get("nombre") +
                    " - " + prod.get("precio") + "€ (" + prod.get("inventario") + " disponibles)");
        }
    }

    // Mostrar historial de compras
    public static void mostrarHistorial(JSONObject usuario) {
        System.out.println("\n--- HISTORIAL DE COMPRAS ---");
        JSONArray historial = (JSONArray) usuario.get("historialCompras");
        if (historial == null || historial.isEmpty()) {
            System.out.println("Sin compras registradas.");
        } else {
            for (Object h : historial) {
                JSONObject compra = (JSONObject) h;
                System.out.println("- Producto ID: " + compra.get("productoId"));
                System.out.println("  Cantidad: " + compra.get("cantidad"));
                System.out.println("  Fecha: " + compra.get("fecha"));
            }
        }
    }

    // Registrar una compra
    public static void hacerCompra(JSONObject usuario, JSONArray productos) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el ID del producto a comprar: ");
        int idBuscado = Integer.parseInt(sc.nextLine());

        JSONObject producto = null;
        for (Object p : productos) {
            JSONObject prod = (JSONObject) p;
            if (((Long) prod.get("id")).intValue() == idBuscado) {
                producto = prod;
                break;
            }
        }

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(sc.nextLine());

        long inventario = (Long) producto.get("inventario");
        if (cantidad > inventario) {
            System.out.println("No hay suficiente stock.");
            return;
        }

        producto.put("inventario", inventario - cantidad);

        JSONObject nuevaCompra = new JSONObject();
        nuevaCompra.put("productoId", producto.get("id"));
        nuevaCompra.put("cantidad", cantidad);
        nuevaCompra.put("fecha", "2025-10-20");

        JSONArray historial = (JSONArray) usuario.get("historialCompras");
        historial.add(nuevaCompra);

        System.out.println("Compra registrada correctamente.");
    }

    // Agregar un nuevo producto
    public static void agregarProducto(JSONObject categoria, Scanner sc) {
        JSONArray productos = (JSONArray) categoria.get("productos");

        JSONObject nuevoProducto = new JSONObject();
        System.out.print("Ingrese ID del nuevo producto: ");
        nuevoProducto.put("id", Long.parseLong(sc.nextLine()));

        System.out.print("Nombre del producto: ");
        nuevoProducto.put("nombre", sc.nextLine());

        System.out.print("Precio: ");
        nuevoProducto.put("precio", Double.parseDouble(sc.nextLine()));

        System.out.print("Descripción: ");
        nuevoProducto.put("descripcion", sc.nextLine());

        JSONObject caracteristicas = new JSONObject();
        System.out.print("Pantalla: ");
        caracteristicas.put("pantalla", sc.nextLine());
        System.out.print("Cámara: ");
        caracteristicas.put("camara", sc.nextLine());
        System.out.print("Batería: ");
        caracteristicas.put("bateria", sc.nextLine());
        nuevoProducto.put("caracteristicas", caracteristicas);

        System.out.print("Inventario inicial: ");
        nuevoProducto.put("inventario", Long.parseLong(sc.nextLine()));

        productos.add(nuevoProducto);

        System.out.println("✅ Producto agregado correctamente.");
    }

    // Guardar JSON con formato bonito
    public static void guardarJSON(JSONObject raiz) {
        try (FileWriter file = new FileWriter("src/main/java/org/example/tienda.JSON")) {
            String jsonString = raiz.toJSONString();
            String formatted = prettyPrintJSON(jsonString);
            file.write(formatted);
            file.flush();
            System.out.println("Archivo JSON actualizado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Pretty print
    public static String prettyPrintJSON(String json) {
        StringBuilder pretty = new StringBuilder();
        int indentLevel = 0;
        boolean inQuotes = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            switch (c) {
                case '"':
                    pretty.append(c);
                    if (i > 0 && json.charAt(i - 1) != '\\') {
                        inQuotes = !inQuotes;
                    }
                    break;
                case '{':
                case '[':
                    pretty.append(c);
                    if (!inQuotes) {
                        pretty.append("\n");
                        indentLevel++;
                        pretty.append("    ".repeat(indentLevel));
                    }
                    break;
                case '}':
                case ']':
                    if (!inQuotes) {
                        pretty.append("\n");
                        indentLevel--;
                        pretty.append("    ".repeat(indentLevel));
                    }
                    pretty.append(c);
                    break;
                case ',':
                    pretty.append(c);
                    if (!inQuotes) {
                        pretty.append("\n");
                        pretty.append("    ".repeat(indentLevel));
                    }
                    break;
                case ':':
                    pretty.append(": ");
                    break;
                default:
                    pretty.append(c);
            }
        }
        return pretty.toString();
    }
}
