# 🛒 Tienda Tecnológica — Domínguez Jacobo

📚 **Proyecto:** Gestión de tienda en formato JSON  
👨‍💻 **Autor:** Jacobo Luis Domínguez Morales  
🏫 **Asignatura:** Desarrollo de Interfaces  
🧰 **Entorno:** Intellij + Java

---

## 📖 Descripción del Proyecto

**TiendaTecnologica_DominguezJacobo** es una aplicación de consola desarrollada en **Java**, que permite gestionar una tienda tecnológica simulada utilizando un archivo **JSON** como base de datos.  
A través de un menú interactivo, el usuario puede:

1. 📄 Ver los datos de un usuario.  
2. 🛍️ Consultar productos por categoría.  
3. 🧾 Revisar el historial de compras de un usuario.  
4. 💳 Realizar una nueva compra y actualizar automáticamente el inventario.

El sistema carga la información desde un fichero JSON, permite modificarla en memoria y guarda los cambios con formato legible al archivo original.

---

## ⚙️ Tecnologías Utilizadas

| Tecnología | Uso principal |
|-------------|----------------|
| ☕ **Java 17+** | Lógica principal del programa |
| 🧩 **JSON.simple** | Lectura y escritura de archivos JSON |
| 💻 **Intellij IDE** | Entorno de desarrollo |
| 📂 **E/S de ficheros** | Manipulación de datos persistentes |
| 🧠 **POO** | Estructuración modular del código |


---

## 🧩 Descripción del Archivo JSON

El archivo `tienda.JSON` contiene la información estructurada de la tienda.  
Incluye **categorías**, **productos** y **usuarios**, cada uno con sus atributos y relaciones.

### 🏬 Estructura General

```json
{
  "tienda": {
    "nombre": "Mi Tienda Online",
    "categorias": [ ... ],
    "usuarios": [ ... ]
  }
}


