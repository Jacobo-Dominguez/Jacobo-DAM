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
            // Leer JSON
            JSONParser parser = new JSONParser();
            JSONObject raiz = (JSONObject) parser.parse(new FileReader("src/main/java/org/example/tienda.JSON"));
            JSONObject tienda = (JSONObject) raiz.get("tienda");
            JSONArray usuarios = (JSONArray) tienda.get("usuarios");
            JSONArray categorias = (JSONArray) tienda.get("categorias");

            JSONObject usuario = null;
            JSONObject categoria = null;

            int opcion;
            do {
                System.out.println("\n==== MENÚ TIENDA ====");
                System.out.println("1. Ver datos del usuario");
                System.out.println("2. Ver productos de una categoría");
                System.out.println("3. Ver historial de compras de un usuario");
                System.out.println("4. Hacer una compra");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        usuario = seleccionarUsuario(usuarios, sc);
                        mostrarUsuario(usuario);
                        break;

                    case 2:
                        categoria = seleccionarCategoria(categorias, sc);
                        mostrarProductos(categoria);
                        break;

                    case 3:
                        usuario = seleccionarUsuario(usuarios, sc);
                        mostrarHistorial(usuario);
                        break;

                    case 4:
                        usuario = seleccionarUsuario(usuarios, sc);
                        categoria = seleccionarCategoria(categorias, sc);
                        JSONArray productos = (JSONArray) categoria.get("productos");
                        JSONObject producto = seleccionarProducto(productos, sc);
                        hacerCompra(usuario, producto);
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

    //  SELECCIÓN
    public static JSONObject seleccionarUsuario(JSONArray usuarios, Scanner sc) {
        System.out.println("\n--- USUARIOS ---");
        for (int i = 0; i < usuarios.size(); i++) {
            JSONObject u = (JSONObject) usuarios.get(i);
            System.out.println((i + 1) + ". " + u.get("nombre"));
        }
        System.out.print("Seleccione usuario: ");
        int opcion = Integer.parseInt(sc.nextLine());
        return (JSONObject) usuarios.get(opcion - 1);
    }

    public static JSONObject seleccionarCategoria(JSONArray categorias, Scanner sc) {
        System.out.println("\n--- CATEGORÍAS ---");
        for (int i = 0; i < categorias.size(); i++) {
            JSONObject c = (JSONObject) categorias.get(i);
            System.out.println((i + 1) + ". " + c.get("nombre"));
        }
        System.out.print("Seleccione categoría: ");
        int opcion = Integer.parseInt(sc.nextLine());
        return (JSONObject) categorias.get(opcion - 1);
    }

    public static JSONObject seleccionarProducto(JSONArray productos, Scanner sc) {
        System.out.println("\n--- PRODUCTOS ---");
        for (int i = 0; i < productos.size(); i++) {
            JSONObject p = (JSONObject) productos.get(i);
            System.out.println((i + 1) + ". " + p.get("nombre") + " - " + p.get("precio") + "€ (" + p.get("inventario") + " disponibles)");
        }
        System.out.print("Seleccione producto: ");
        int opcion = Integer.parseInt(sc.nextLine());
        return (JSONObject) productos.get(opcion - 1);
    }

    //  MOSTRAR
    public static void mostrarUsuario(JSONObject usuario) {
        System.out.println("\n--- DATOS DEL USUARIO ---");
        System.out.println("Nombre: " + usuario.get("nombre"));
        System.out.println("Email: " + usuario.get("email"));
        JSONObject direccion = (JSONObject) usuario.get("direccion");
        System.out.println("Dirección: " + direccion.get("calle") + " " + direccion.get("numero") + ", "
                + direccion.get("ciudad") + " (" + direccion.get("pais") + ")");
    }

    public static void mostrarProductos(JSONObject categoria) {
        System.out.println("\n--- PRODUCTOS EN " + categoria.get("nombre") + " ---");
        JSONArray productos = (JSONArray) categoria.get("productos");
        for (Object p : productos) {
            JSONObject prod = (JSONObject) p;
            System.out.println("ID: " + prod.get("id") + " | " + prod.get("nombre") +
                    " - " + prod.get("precio") + "€ (" + prod.get("inventario") + " disponibles)");
        }
    }

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

    //  COMPRAS
    public static void hacerCompra(JSONObject usuario, JSONObject producto) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Cantidad a comprar: ");
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

    //  GUARDAR JSON
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

    // FORMATEAR JSON (Pedido a ChatGPT porque al ejecutar el programa y añadir
    // y guardar algo en el JSON perdía el formato y se escribía en una línea)
    public static String prettyPrintJSON(String json) {
        StringBuilder pretty = new StringBuilder();
        int indentLevel = 0;
        boolean inQuotes = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            switch (c) {
                case '"':
                    pretty.append(c);
                    if (i > 0 && json.charAt(i - 1) != '\\') inQuotes = !inQuotes;
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
