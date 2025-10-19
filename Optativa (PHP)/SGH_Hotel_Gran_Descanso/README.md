# 🛎️ **Proyecto: Sistema de Gestión Hotelera (SGH) - PHP**

---

## 🌐 Descripción general

El Hotel **“El Gran Descanso”** necesita un sistema que permita **gestionar sus reservas**, controlar el **estado de las habitaciones** y coordinar las **tareas de mantenimiento y limpieza**.  
El objetivo principal es **crear una interfaz funcional y atractiva**, desarrollada con **PHP, HTML, CSS, JavaScript y SQL**, que facilite el trabajo del personal administrativo.

---

## 🧱 Estructura del sistema

### 🛏️ 1. Habitaciones
Cada habitación cuenta con:
- 🔢 **Número único**  
- 🏷️ **Tipo** (Sencilla, Doble, Suite)  
- 💰 **Precio base por noche**  
- 🧽 **Estado de limpieza** (Limpia, Sucia, En limpieza)

---

### 👤 2. Huéspedes
Los huéspedes se registran con:
- 🧾 **Nombre completo**
- ✉️ **Email (único)**
- 🪪 **Documento de identidad**

---

### 📅 3. Reservas
Cada reserva vincula un huésped a una habitación durante un rango de fechas:
- 📆 **Fecha de llegada y salida**
- 💸 **Precio total**
- ⚙️ **Estado:** Pendiente, Confirmada o Cancelada  
- 🕒 **Fecha de creación de la reserva**

> ⚠️ Una habitación no puede ser reservada si ya existe una reserva confirmada en las mismas fechas.

---

### 🧰 4. Mantenimiento y Limpieza
Permite registrar y supervisar las tareas de mantenimiento:
- 🧹 Estado de limpieza: *Limpia / En limpieza / Sucia*  
- 🧰 Datos de mantenimiento: *fecha de inicio, fecha fin y descripción de la tarea*

---

## 🖼️ Vista previa del proyecto

<p align="center">
  <img width="80%" alt="Vista previa del sistema" src="/Optativa (PHP)/SGH_Hotel_Gran_Descanso/imagenes/Captura de pantalla 2025-10-19 214820.png" />
</p>

> Interfaz moderna inspirada en paneles administrativos: simple, funcional y visualmente limpia.

---

## ⚙️ Tecnologías utilizadas

| 🧩 Tecnología | 💡 Uso principal |
|---------------|------------------|
| **HTML / CSS** | Diseño visual de la interfaz, maquetación y estilos. |
| **PHP** | Lógica de negocio, gestión de peticiones y conexión con la base de datos. |
| **JavaScript** | Interactividad dinámica en el cliente. |
| **SQL (MySQL)** | Almacenamiento de datos: habitaciones, huéspedes, reservas y mantenimiento. |
| **PDO (PHP Data Objects)** | Conexión segura entre PHP y la base de datos. |
| **VS Code** | Entorno de desarrollo utilizado para el proyecto. |

---
