# 📘 Sistema de Gestión para Gimnasio - GymForTheMoment

Este proyecto es una aplicación desarrollada en **Python** con **CustomTkinter**, **Tkinter**, **tkcalendar** y **SQLite**, diseñada para proporcionar un sistema completo de gestión para gimnasios. Permite administrar clientes, recibos, aparatos y reservas mediante una interfaz gráfica moderna, intuitiva y visualmente atractiva.

---

## 🏋️‍♂️ ¿En qué consiste el proyecto?

El sistema permite gestionar:

* **Clientes** → Alta, edición, búsqueda, eliminación, control de morosidad
* **Aparatos** → Estado, tipo, disponibilidad
* **Reservas** → Conexión cliente–aparato, validación de horarios, prevención de solapamientos
* **Recibos** → Control de cuotas mensuales, generación masiva, seguimiento de pagos
* **Reportes** → Disponibilidad de aparatos por día, visualización de ocupación

---

## ✨ Características Principales

### 🎯 Cumplimiento Total de Requisitos
- ✅ **Horario 24h lunes a viernes** - Validación automática de días laborables
- ✅ **Sesiones de 30 minutos** - Selector de horas con intervalos predefinidos
- ✅ **Prevención de solapamientos** - No permite reservas duplicadas
- ✅ **Reportes de disponibilidad** - Vista detallada por aparato y día
- ✅ **Generación masiva de recibos** - Crea recibos para todos los clientes automáticamente
- ✅ **Control de morosidad** - Filtrado rápido de clientes morosos

---

## 📂 Estructura del Proyecto

```
Gymforthemoment/
│
├── modelo/
│   ├── base_datos.py
│   ├── cliente.py
│   ├── aparato.py
│   ├── reserva.py
│   └── recibo.py
│
├── controlador/
│   ├── controlador_clientes.py
│   ├── controlador_aparatos.py
│   ├── controlador_reservas.py
│   ├── controlador_recibos.py
│   └── controlador_reportes.py          
│
├── vista/
│   ├── interfaz.py
│   ├── frame_clientes.py
│   ├── frame_aparatos.py
│   ├── frame_reservas.py
│   ├── frame_recibos.py
│   ├── frame_reportes.py                
│   ├── formulario_cliente.py
│   ├── formulario_aparato.py
│   ├── formulario_reserva.py
│   ├── formulario_recibo.py
│   └── utilidades.py
│
├── datos_prueba.py
├── main.py
├── gym.db
└── README.md
```

---

## 🧱 Arquitectura y Organización

El programa sigue un patrón **MVC simplificado**:

* **Modelo** → Entidades + Base de datos SQLite
* **Controlador** → Lógica intermedia, validaciones y operaciones CRUD
* **Vista** → Interfaz CustomTkinter/Tkinter con diseño moderno

Esto permite separar responsabilidades y mantener un código claro y escalable.

---

## 📦 MÓDULO MODELO

### base_datos.py
Encapsula todas las operaciones con SQLite.
- Ejecutar queries
- Consultar uno/varios registros
- Manejo automático de conexión

### cliente.py
Representa un cliente del gimnasio.
- `id`, `nombre`, `apellido`, `email`, `telefono`
- `moroso` (booleano) - Control de pagos

### aparato.py
Representa un aparato del gimnasio.
- `id`, `nombre`, `tipo`
- `estado` (disponible, mantenimiento, ocupado)

### reserva.py
Representa una reserva de un cliente para un aparato.
- `id`, `id_cliente`, `id_aparato`
- `fecha` (YYYY-MM-DD), `hora` (HH:MM)

### recibo.py
Representa un pago mensual.
- `id`, `id_cliente`
- `mes` (nombre del mes), `anio` (int)
- `pagado` (booleano)

---

## 🧠 CONTROLADORES

### controlador_clientes.py
- Agregar, editar, eliminar, listar
- Obtener por ID
- Marcar moroso
- **Listar morosos** 

### controlador_aparatos.py
- Agregar, editar, eliminar, listar
- Cambiar estado
- Obtener por ID

### controlador_reservas.py
- Agregar/editar con validaciones de horario
- Eliminar reservas
- Listar con relaciones (cliente–aparato)
- **Verificar solapamiento** 
- **Obtener reservas por aparato y fecha** 

### controlador_recibos.py
- Añadir, editar, eliminar, listar
- Marcar como pagado
- **Generar recibos masivos** 
- **Verificar duplicados** 

### controlador_reportes.py 
- **Generar disponibilidad por día** - Reporte completo de ocupación
- Obtener resumen de reservas

---

## 🖼 VISTA (INTERFAZ)

### interfaz.py
Menú principal con diseño moderno:
- 🔴 Gestión de Clientes (Rojo)
- 🔵 Gestión de Aparatos (Azul)
- 🟢 Gestión de Reservas (Verde)
- 🟠 Gestión de Recibos (Naranja)
- 🟣 Reportes (Morado)

### frame_clientes.py
- Tabla de clientes con búsqueda
- Botones CRUD con colores distintivos
- **Botón "Ver Morosos"** 
- **Botón "Ver Todos"** 
- Formulario modal para añadir/editar

### frame_aparatos.py
- Tabla de aparatos con filtro
- Botones CRUD con emojis
- Alta, edición y borrado

### frame_reservas.py
- Selección de cliente y aparato (combobox)
- **Calendario visual para fechas** 
- **Selector de horas (30 min)** 
- Validación de días laborables
- Prevención de solapamientos
- Tabla con información completa

### frame_recibos.py
- Tabla con cliente, mes, año y estado
- **Diálogo unificado mes/año** 
- **Generación masiva de recibos** 
- Nombres de meses en lugar de números
- Alta con validación automática

### frame_reportes.py 
- **Calendario para seleccionar fecha**
- **Pestañas por aparato**
- **Tabla de disponibilidad hora por hora**
- **Código de colores** (verde=libre, rojo=ocupado)
- **Información del cliente** en horas ocupadas
- Resumen de reservas del día

### Formularios
Cada entidad tiene su formulario modal:
- **formulario_reserva.py** - Con calendario y selector de horas
- **formulario_recibo.py** - Con nombres de meses
- **formulario_cliente.py** - Con control de morosidad
- **formulario_aparato.py** - Con selector de estado

---

## 🎨 Diseño Visual

### Paleta de Colores
- **Azul Eléctrico** (#00d4ff) - Acciones principales
- **Rojo** (#e74c3c) - Eliminar
- **Verde** (#2ecc71) - Confirmar/Generar
- **Naranja** (#f39c12) - Advertencias
- **Gris** (#7f8c8d) - Navegación
- **Fondo Oscuro** (#1a1a2e, #16213e) - Tema principal

### Emojis Utilizados
- 🔍 Buscar, 🔎 Filtrar
- ➕ Agregar, ✏️ Editar, 🗑️ Eliminar
- 📅 Calendario, ⏰ Hora
- 💰 Recibos, 📊 Reportes
- ⚠️ Morosos, 👁️ Ver todos
- 🏠 Volver al menú

---

## 🔧 Validaciones Implementadas

### Reservas
- ✅ Solo lunes a viernes
- ✅ No fechas pasadas
- ✅ No horas pasadas (mismo día)
- ✅ Solo intervalos de 30 minutos (XX:00, XX:30)
- ✅ Sin solapamientos en mismo aparato/hora

### Recibos
- ✅ Nombres de meses (Enero, Febrero, etc.)
- ✅ Prevención de duplicados
- ✅ Generación masiva con confirmación

### Clientes
- ✅ Control de morosidad
- ✅ Filtrado rápido de morosos

---

## 📊 Funcionalidades Destacadas

### 1. Reportes de Disponibilidad
- Selección de fecha con calendario
- Vista por pestañas (una por aparato)
- Tabla con 48 slots de 30 minutos (00:00 - 23:30)
- Código de colores para ocupación
- Nombre del cliente en horas ocupadas
- Contador de reservas por aparato

### 2. Generación Masiva de Recibos
- Diálogo unificado con selectores
- Nombres de meses en dropdown
- Generación automática para todos los clientes
- Prevención de duplicados
- Mensaje con cantidad de recibos creados

### 3. Control de Morosidad
- Campo booleano en clientes
- Botón "Ver Morosos" para filtrado rápido
- Botón "Ver Todos" para restaurar vista
- Indicador visual en tabla

### 4. Calendario y Selectores
- Widget de calendario (tkcalendar)
- Selector de horas con opciones válidas
- Eliminación de errores de formato
- Validaciones automáticas integradas

---

## 🚀 Instalación y Uso

### Requisitos
```bash
pip install customtkinter
pip install tkcalendar
```

### Ejecutar
```bash
python main.py
```

### Datos de Prueba
```bash
python datos_prueba.py
```

---

## 📝 Notas Técnicas

- **Base de datos:** SQLite (gym.db)
- **Framework UI:** CustomTkinter + Tkinter
- **Calendario:** tkcalendar
- **Patrón:** MVC simplificado
- **Resolución:** 1280x720
- **Tema:** Oscuro con acentos de color
