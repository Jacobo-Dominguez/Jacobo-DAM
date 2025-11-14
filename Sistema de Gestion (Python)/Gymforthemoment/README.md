# 📘 Sistema de Gestión para Gimnasio

Este proyecto es una aplicación desarrollada en **Python** con **Tkinter** y **SQLite**, diseñada para proporcionar un sistema completo de gestión para gimnasios. Permite administrar clientes, recibos, aparatos y reservas mediante una interfaz gráfica sencilla e intuitiva.

---

# 🏋️‍♂️ ¿En qué consiste el proyecto?

El sistema permite gestionar:

* **Clientes** → Alta, edición, búsqueda, eliminación, morosidad
* **Aparatos** → Estado, tipo, disponibilidad
* **Reservas** → Conexión cliente–aparato, fechas válidas, horas válidas
* **Recibos** → Control de cuotas mensuales, selección por cliente

---

# 📂 Estructura del Proyecto

```
Gymforthemoment/
│
├── modelo/
│   ├── base_datos.py
│   ├── cliente.py
│   ├── aparato.py
│   ├── reserva.py
│   ├── recibo.py
│
├── controlador/
│   ├── controlador_clientes.py
│   ├── controlador_aparatos.py
│   ├── controlador_reservas.py
│   ├── controlador_recibos.py
│
├── vista/
│   ├── interfaz.py
│   ├── frame_clientes.py
│   ├── frame_aparatos.py
│   ├── frame_reservas.py
│   ├── frame_recibos.py
│   ├── formulario_cliente.py
│   ├── formulario_aparato.py
│   ├── formulario_reserva.py
│   ├── formulario_recibo.py
│
├── datos_prueba.py
├── main.py
└── README.md
```

---

# 🧱 Arquitectura y organización

El programa sigue un patrón **MVC simplificado**:

* **Modelo** → Entidades + Base de datos
* **Controlador** → Lógica intermedia, validaciones y operaciones CRUD
* **Vista** → Interfaz Tkinter

Esto permite separar responsabilidades y mantener un código claro y escalable.

---

# 📦 MÓDULO MODELO

## base_datos.py

Encapsula todas las operaciones con SQLite.
Funciones principales:

* Ejecutar queries
* Consultar uno
* Consultar varios
* Manejo automático de conexión

## cliente.py

Representa un cliente del gimnasio.
Atributos:

* `id`
* `nombre`
* `apellido`
* `email`
* `telefono`
* `moroso` (booleano)

## aparato.py

Representa un aparato del gimnasio.
Atributos:

* `id`
* `nombre`
* `tipo`
* `estado` (disponible, mantenimiento, ocupado)

## reserva.py

Representa una reserva de un cliente para un aparato.
Atributos:

* `id`
* `id_cliente`
* `id_aparato`
* `fecha` (YYYY-MM-DD)
* `hora` (HH:MM)

Incluye validaciones en la vista/controlador para evitar:

* Reservas en fecha pasada
* Reservas en hora pasada cuando es el día actual

## recibo.py

Representa un pago mensual.
Atributos:

* `id`
* `id_cliente`
* `mes` (int)
* `anio` (int)
* `pagado` (booleano)

Validación incluida:

* No se puede registrar un recibo de un mes anterior al actual.

---

# 🧠 CONTROLADORES

Los controladores conectan el modelo con la vista.
Realizan:

* Validaciones
* Consultas a la BD
* Formateo de datos
* Operaciones CRUD

## controlador_clientes.py

Funciones clave:

* Agregar
* Editar
* Eliminar
* Listar
* Obtener por ID
* Marcar moroso

## controlador_aparatos.py

Funciones clave:

* Agregar
* Editar
* Eliminar
* Cambiar estado
* Listar
* Obtener por ID

## controlador_reservas.py

Funciones clave:

* Agregar reservas evitando fechas pasadas
* Editar reservas respetando restricciones
* Eliminar reservas
* Listar con relaciones (cliente–aparato)

## controlador_recibos.py

Funciones clave:

* Añadir recibos a la lista
* Editar recibos existentes
* Listar con nombres de cliente

---

# 🖼 VISTA (INTERFAZ TKINTER)

Toda la interfaz está organizada por módulos:

## interfaz.py

Contiene el menú principal y carga los distintos frames.

## frame_clientes.py

✔ Tabla de clientes
✔ Buscador
✔ Botones CRUD
✔ Formulario modal para añadir o editar

## frame_aparatos.py

✔ Muestra aparatos
✔ Filtro
✔ Alta, edición y borrado

## frame_reservas.py

✔ Selección de cliente mediante combobox
✔ Selección de aparato mediante combobox
✔ Control de fechas válidas
✔ Control de horas válidas
✔ Tabla mostrando:

* ID Reserva
* Cliente (ID + nombre)
* Aparato (ID + nombre)
* Fecha
* Hora

## frame_recibos.py

✔ Tabla con:

* ID
* Cliente
* Mes
* Año
* Pagado

✔ Alta con validación automática de mes/año correcto

## Formularios

Cada entidad tiene su propio formulario modal para añadir/editar datos.

---

# 🧪 Datos de prueba

Incluidos en:

```
datos_prueba.py
```

Generan clientes, aparatos, reservas y recibos válidos.

---