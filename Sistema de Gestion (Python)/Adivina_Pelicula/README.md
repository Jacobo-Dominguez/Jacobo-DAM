# 🎬 Adivina la Película o Serie

## Descripción

`Adivina la Película o Serie` es un juego educativo y entretenido desarrollado en **Python** con **interfaz gráfica** (`Tkinter`) y **SQLite** como base de datos.  

El jugador debe adivinar el título de películas o series a partir de **pistas**. Cada pista resta puntos, pero la primera pista es gratuita. El juego mantiene un **histórico de puntuaciones** por jugador y permite jugar varias rondas consecutivas.  

Se sigue el patrón **MVC (Modelo-Vista-Controlador)** para separar la lógica del juego, la gestión de datos y la interfaz gráfica.

---

## 📂 Estructura del Proyecto

Adivina_Pelicula/
│
├─ controller/
│   └─ controlador_juego.py      # Lógica del juego y conexión entre modelo y vista
│
├─ model/
│   ├─ base_datos.py             # Clase para gestionar SQLite
│   ├─ jugador.py                # Clase Jugador
│   └─ pelicula.py               # Clase Pelicula
│
├─ view/
│   └─ vista_juego.py            # Interfaz gráfica del juego con estilo cinematográfico
│
├─ data/
│   └─ peliculas.db              # Base de datos SQLite con tablas de películas y puntuaciones
│
├─ main.py                       # Punto de entrada del juego
└─ README.md                     # Documentación del proyecto



---

## 🔧 Dependencias

- Python 3.10 o superior  
- Tkinter (integrado en Python)  
- Pillow (`pip install pillow`) → para trabajar con imágenes en la interfaz  

---

## 🏗️ Funcionamiento

### 1️⃣ Iniciar el juego
- El jugador introduce su nombre y pulsa **“Iniciar Juego”**.
- Se muestra una **pista inicial gratuita**.

### 2️⃣ Pedir pistas
- Cada vez que el jugador pide una pista, se le resta **puntos** según la pista (3, 5, 7, 9, 11).  
- Las pistas incluyen información de:
  - Año
  - Director
  - Actor principal
  - Género
  - Pista específica de la película

### 3️⃣ Responder
- El jugador escribe el título de la película y pulsa **“Comprobar”**.
- Si acierta:
  - Se guarda la puntuación en la base de datos.  
  - Se habilita el botón **“Siguiente Película”**.

### 4️⃣ Siguiente película
- Al pulsar **“Siguiente Película”**, se carga otra película aleatoria y se reinicia la primera pista gratuita.
- Se puede repetir tantas veces como quiera el jugador.

---

## 🎨 Interfaz gráfica

- Fondo oscuro tipo cine (`#222831`)  
- Pistas y mensajes resaltados en colores vivos (amarillo, azul, verde)  
- Botones claros y diferenciados según función  
- Layout organizado con **frames** para nombre, pista, respuesta y navegación  
- Botón **Siguiente Película** habilitado solo al acertar  

---

## 🗄️ Base de datos

**SQLite** para persistencia de datos.  

### Tablas:

1. `peliculas`
```sql
id INTEGER PRIMARY KEY AUTOINCREMENT,
titulo TEXT NOT NULL,
pista1 TEXT,
pista2 TEXT,
pista3 TEXT,
pista4 TEXT,
pista5 TEXT
```

2. `puntuaciones`
```sql
id INTEGER PRIMARY KEY AUTOINCREMENT,
jugador TEXT NOT NULL,
puntuacion INTEGER NOT NULL
