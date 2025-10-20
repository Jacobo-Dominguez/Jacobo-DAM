package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        try{
            // Leer el archivo JSON
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("src/main/java/org/example/tienda.JSON"));
            JSONObject raiz = (JSONObject) obj;
            JSONObject tienda = (JSONObject) raiz.get("tienda");

            // Mostramos la tienda
            System.out.println("Tienda: " + tienda.get("nombre"));

            // Mostramos al primer usuario
            JSONArray usuarios =  (JSONArray) tienda.get("usuarios");
            JSONObject usuario = (JSONObject) usuarios.get(0);
            System.out.println("Usuario: " + usuario.get("nombre"));
            System.out.println("Email: " + usuario.get("email"));

            JSONObject direccion =  (JSONObject) usuario.get("direccion");
            System.out.println("Direccion: " + direccion.get("calle") + " " + direccion.get("numero") + ", "
                    + direccion.get("ciudad") + " (" + direccion.get("pais") + ")");

            // Mostrar el producto de una categoria
            JSONArray categorias = (JSONArray) tienda.get("categorias");
            JSONObject categoria = (JSONObject) categorias.get(0);
            JSONArray productos = (JSONArray) categoria.get("productos");
            JSONObject producto = (JSONObject) productos.get(0);

            System.out.println("Producto: " + producto.get("nombre"));
            System.out.println("Precio: " + producto.get("precio") + "€");

            // Mostrar el historial de compras
            JSONArray historial =  (JSONArray) usuario.get("historialCompras");
            System.out.println("\n Historial de compras:");

            if (historial != null && !historial.isEmpty()) {
                for (Object h : historial) {
                    JSONObject compra = (JSONObject) h;
                    System.out.println("- Producto ID: " + compra.get("productoId"));
                    System.out.println("  Cantidad: " + compra.get("cantidad"));
                    System.out.println("  Fecha: " + compra.get("fecha"));
                }
            } else {
                System.out.println("  (sin compras registradas)");
            }

            // Simulación de una compra
            JSONObject nuevaCompra = new JSONObject();
            nuevaCompra.put("productoId", producto.get("id"));
            nuevaCompra.put("cantidad", 1);
            nuevaCompra.put("fecha", "2025-10-20");

            JSONArray historialCompras = (JSONArray) usuario.get("historialCompras");
            historialCompras.add(nuevaCompra);

            // Guardar el archivo actualizado
            try (FileWriter file = new FileWriter("src/main/java/org/example/tienda.JSON")) {
                String jsonString = raiz.toJSONString();
                String formatted = prettyPrintJSON(jsonString);
                file.write(formatted);
                file.flush();
            }

            System.out.println("\n Compra añadida correctamente al historial.");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para dar formato bonito al JSON
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