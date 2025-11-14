import sqlite3
import os

class BaseDatos:
    def __init__(self, nombre_db="gym.db"):
        self.nombre_db = nombre_db
        # Crear la base de datos si no existe
        if not os.path.exists(self.nombre_db):
            self._crear_db()

    def _crear_db(self):
        conn = sqlite3.connect(self.nombre_db)
        conn.close()

    def ejecutar(self, query, params=()):
        """
        Ejecuta INSERT, UPDATE, DELETE u otras sentencias SQL.
        """
        conn = sqlite3.connect(self.nombre_db)
        conn.row_factory = sqlite3.Row
        cursor = conn.cursor()
        try:
            cursor.execute(query, params)
            conn.commit()
        except Exception as e:
            conn.rollback()
            raise e
        finally:
            conn.close()

    def consultar(self, query, params=()):
        """
        Ejecuta un SELECT que puede devolver varias filas.
        Devuelve lista de diccionarios.
        """
        conn = sqlite3.connect(self.nombre_db)
        conn.row_factory = sqlite3.Row
        cursor = conn.cursor()
        try:
            cursor.execute(query, params)
            filas = cursor.fetchall()
            return [dict(f) for f in filas]
        finally:
            conn.close()

    def consultar_uno(self, query, params=()):
        """
        Ejecuta un SELECT que devuelve solo una fila.
        Devuelve diccionario o None.
        """
        conn = sqlite3.connect(self.nombre_db)
        conn.row_factory = sqlite3.Row
        cursor = conn.cursor()
        try:
            cursor.execute(query, params)
            fila = cursor.fetchone()
            return dict(fila) if fila else None
        finally:
            conn.close()
