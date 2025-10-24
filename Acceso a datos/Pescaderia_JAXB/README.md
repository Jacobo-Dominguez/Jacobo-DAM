# 🐟 Proyecto: Gestión de Pescadería con JAXB

## 📋 Descripción General

Este proyecto consiste en una **aplicación de gestión para una pescadería**, desarrollada en **Java** utilizando la tecnología **JAXB (Java Architecture for XML Binding)**.  
El objetivo principal es **gestionar los productos, clientes y ventas**, permitiendo almacenar y recuperar los datos en formato **XML**, garantizando una persistencia ligera y fácilmente manipulable.

---

## 🎯 Objetivos del Proyecto

- Implementar un sistema de gestión de datos utilizando **Java y JAXB**.
- Representar la información en formato **XML**, facilitando la lectura, modificación y almacenamiento.
- Aplicar **principios de programación orientada a objetos (POO)** en la estructura del código.
- Desarrollar una interfaz o sistema modular que facilite la **gestión de productos** y operaciones básicas de CRUD.

---

## 🧰 Tecnologías Utilizadas

| Tipo | Herramienta / Tecnología |
|------|---------------------------|
| 💻 Lenguaje de programación | Java |
| 🧱 Biblioteca | JAXB (Java Architecture for XML Binding) |
| 🧩 IDE de desarrollo | NetBeans |
| 🗂️ Almacenamiento | Ficheros XML |
| 🧮 Paradigma | Programación Orientada a Objetos |

---

## 🗂️ Estructura del Proyecto

Pescaderia_JAXB/
├── src/
│ ├── model/
│ │ ├── Producto.java
│ │ ├── Cliente.java
│ │ └── Venta.java
│ ├── xml/
│ │ ├── Pescaderia.java
│ │ └── JAXBManager.java
│ └── main/
│ └── Main.java
├── data/
│ └── pescaderia.xml
└── README.md

---

## ⚙️ Funcionamiento del Proyecto

1. Al iniciar la aplicación, se carga la información de la pescadería desde un fichero `XML`.
2. Los datos son convertidos a objetos Java mediante **JAXB (Unmarshalling)**.
3. El usuario puede:
   - Añadir, eliminar o modificar productos.
   - Registrar ventas.
   - Consultar clientes o productos.
4. Los cambios se guardan nuevamente en el archivo XML mediante **Marshalling**.

---

## 🖼️ Capturas de Pantalla

> 📸 *Aquí puedes incluir imágenes de la interfaz o ejemplos de salida:*

- **Menú principal**
  - ![Captura 1](./images/menu_principal.png)
- **Gestión de productos**
  - ![Captura 2](./images/gestion_productos.png)

---

## 📦 Ejecución del Proyecto

1. Abrir el proyecto en **NetBeans**.
2. Asegurarse de tener **Java 8+** instalado.
3. Ejecutar la clase principal `Main.java`.
4. Comprobar que el archivo `pescaderia.xml` se encuentra en el directorio `data/`.

---

## 👨‍💻 Autor

**Jacobo Luis Domínguez Morales**  
📚 2º DAM — Curso 2025 / 2026  
💬 Proyecto: *Acceso a Datos — JAXB y XML en Java*  

---

## 🏁 Estado del Proyecto

✅ Finalizado / en mantenimiento menor  
📅 Última actualización: *(añade la fecha si lo deseas)*
