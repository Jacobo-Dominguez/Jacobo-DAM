# 🛍️ Tienda MVVM WPF

*Autor:* Jacobo Luis Domínguez Morales  
*Asignatura:* Desarrollo de Interfaces  
*Fase 2 – Aplicativo MVVM con SQL Server*

---

## 📘 Descripción

Aplicación de escritorio desarrollada en *WPF (C#)* siguiendo el patrón *MVVM, que simula una tienda online con conexión a **SQL Server*.  
Permite gestionar productos y usuarios mediante operaciones *CRUD, realizar búsquedas con **LINQ* y registrar compras desde un carrito.

---

## 🧩 Estructura del Proyecto
```plaintext
MVVC_Tienda_DominguezJacobo/
├── Models/
│   ├── Producto.cs
|   ├── Categoria.cs
│   └── Usuario.cs
├── Data/
│   └── GestorBD.cs
├── Database/
│   └── SQLQuery.sql
├── Resources/
│   └── producto.jpg
|   └── product1.jpg
|   └── producto2.jpg
|   └── product3.jpg
|   └── producto4.jpg
|   └── producto5.jpg
|   └── producto6.jpg
├── ViewModels/
│   └── ProductosViewModel.cs
├── Views/
│   ├── MainWindow.xaml / MainWindow.xaml.cs
│   └── LoginWindow.xaml / LoginWindow.xaml.cs
└── App.xaml / App.xaml.cs
```
---

## ⚙️ Funcionalidades Principales

- *Gestión de Productos:* CRUD completo sobre la tabla Productos.  
- *Gestión de Usuarios:* Alta, modificación, eliminación y consulta.  
- *Buscador con LINQ:* Filtrado dinámico por nombre.  
- *Carrito de Compras:* Registro de pedidos en la base de datos.  
- *Persistencia en SQL Server:* Conexión mediante SqlConnection y SqlCommand.
  
---

## 🧠 Clases Destacadas

### GestorBD.cs
Maneja la conexión con SQL Server y las operaciones CRUD.  
Principales métodos:
- ObtenerProductos()
- RegistrarCompra()
- InsertarUsuario()
- ObtenerUsuarios()
- ActualizarUsuario()
- EliminarUsuario()

### ProductosViewModel.cs
Carga los productos desde la base de datos y aplica filtrado con *LINQ*:

csharp
var filtrados = TodosLosProductos
    .Where(p => p.Nombre.ToLower().Contains(termino.ToLower()))
    .ToList();

## 🧾 Base de Datos

**Tablas principales:**

- `Productos (Id, Nombre, Categoria, Precio, Stock, Imagen, Descripcion)`
- `Usuarios (Id, Nombre, Apellidos, Email, Contrasena, Rol)`
- `Pedidos (Id, Producto, FechaCompra)`

---

## 🧩 Tecnologías

- *C# / .NET*
- *WPF (XAML)*
- *MVVM*
- *LINQ*
- *SQL Server / T-SQL*

---
