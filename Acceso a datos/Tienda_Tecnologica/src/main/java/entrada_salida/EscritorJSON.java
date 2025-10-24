package entrada_salida;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;

public class EscritorJSON {

    public static void guardarTienda(Tienda tienda) {
        try {
            JSONObject raiz = new JSONObject();
            JSONObject tiendaJson = new JSONObject();
            tiendaJson.put("nombre", tienda.getNombre());

            // Usuarios
            JSONArray usuariosJson = new JSONArray();
            for (Usuario u : tienda.getUsuarios()) {
                JSONObject uJson = new JSONObject();
                uJson.put("id", u.getId());
                uJson.put("nombre", u.getNombre());
                uJson.put("email", u.getEmail());

                JSONObject dirJson = new JSONObject();
                dirJson.put("calle", u.getDireccion().getCalle());
                dirJson.put("numero", u.getDireccion().getNumero());
                dirJson.put("ciudad", u.getDireccion().getCiudad());
                dirJson.put("pais", u.getDireccion().getPais());
                uJson.put("direccion", dirJson);

                JSONArray historialJson = new JSONArray();
                for (Compra c : u.getHistorialCompras()) {
                    JSONObject cJson = new JSONObject();
                    cJson.put("productoId", c.getProductoId());
                    cJson.put("cantidad", c.getCantidad());
                    cJson.put("fecha", c.getFecha());
                    historialJson.add(cJson);
                }
                uJson.put("historialCompras", historialJson);

                usuariosJson.add(uJson);
            }
            tiendaJson.put("usuarios", usuariosJson);

            // Categorías y productos
            JSONArray categoriasJson = new JSONArray();
            for (Categoria c : tienda.getCategorias()) {
                JSONObject cJson = new JSONObject();
                cJson.put("id", c.getId());
                cJson.put("nombre", c.getNombre());

                JSONArray productosJson = new JSONArray();
                for (Producto p : c.getProductos()) {
                    JSONObject pJson = new JSONObject();
                    pJson.put("id", p.getId());
                    pJson.put("nombre", p.getNombre());
                    pJson.put("precio", p.getPrecio());
                    pJson.put("descripcion", p.getDescripcion());
                    pJson.put("inventario", p.getInventario());

                    JSONObject carJson = new JSONObject();
                    carJson.put("pantalla", p.getCaracteristicas().getPantalla());
                    carJson.put("camara", p.getCaracteristicas().getCamara());
                    carJson.put("bateria", p.getCaracteristicas().getBateria());
                    pJson.put("caracteristicas", carJson);

                    productosJson.add(pJson);
                }
                cJson.put("productos", productosJson);
                categoriasJson.add(cJson);
            }
            tiendaJson.put("categorias", categoriasJson);
            raiz.put("tienda", tiendaJson);

            try (FileWriter file = new FileWriter("src/main/resources/tienda.json")) {
                file.write(prettyPrintJSON(raiz.toJSONString()));
                file.flush();
            }

            System.out.println("✅ JSON guardado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Formato bonito
    private static String prettyPrintJSON(String json) {
        StringBuilder pretty = new StringBuilder();
        int indent = 0;
        boolean inQuotes = false;
        for (char c : json.toCharArray()) {
            switch (c) {
                case '"':
                    pretty.append(c);
                    inQuotes = !inQuotes;
                    break;
                case '{':
                case '[':
                    pretty.append(c);
                    if (!inQuotes) {
                        pretty.append("\n");
                        indent++;
                        pretty.append("    ".repeat(indent));
                    }
                    break;
                case '}':
                case ']':
                    if (!inQuotes) {
                        pretty.append("\n");
                        indent--;
                        pretty.append("    ".repeat(indent));
                    }
                    pretty.append(c);
                    break;
                case ',':
                    pretty.append(c);
                    if (!inQuotes) {
                        pretty.append("\n");
                        pretty.append("    ".repeat(indent));
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
