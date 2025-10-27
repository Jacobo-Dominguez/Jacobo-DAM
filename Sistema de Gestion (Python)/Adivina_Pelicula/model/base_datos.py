import sqlite3

class BaseDatos:
    def __init__(self, nombre_bd="data/peliculas.db"):
        self.nombre_bd = nombre_bd
        self.crear_tablas()

    def conectar(self):
        return sqlite3.connect(self.nombre_bd)

    def crear_tablas(self):
        with self.conectar() as conn:
            conn.execute("""
            CREATE TABLE IF NOT EXISTS puntuaciones(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            jugador TEXT NOT NULL,
            puntuacion INTEGER NOT NULL
            )
            """)

            conn.execute("""
            CREATE TABLE IF NOT EXISTS peliculas(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            titulo TEXT NOT NULL,
            pista1 TEXT,
            pista2 TEXT,
            pista3 TEXT,
            pista4 TEXT,
            pista5 TEXT
            )
            """)

    def guardar_puntuacion(self, jugador, puntuacion):
        with self.conectar() as conn:
            conn.execute("INSERT INTO puntuaciones (jugador, puntuacion) VALUES (?, ?)",
                         (jugador, puntuacion))

    def obtener_historico(self):
        with self.conectar() as conn:
            conn.execute("SELECT jugador, puntuacion FROM puntuaciones ORDER BY id DESC").fetchall()