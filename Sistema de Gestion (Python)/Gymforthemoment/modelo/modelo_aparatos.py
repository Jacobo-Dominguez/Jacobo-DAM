from modelo.aparato import Aparato
from modelo.base_datos import BaseDatos

class ModeloAparatos:
    def __init__(self):
        self.db = BaseDatos()
        self._crear_tabla()

    def _crear_tabla(self):
        query = """
        CREATE TABLE IF NOT EXISTS aparatos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            tipo TEXT NOT NULL,
            estado TEXT DEFAULT 'Disponible'
        )
        """
        self.db.ejecutar(query)

    def insertar(self, aparato: Aparato):
        query = """
        INSERT INTO aparatos (nombre, tipo, estado)
        VALUES (?, ?, ?)
        """
        self.db.ejecutar(query, (aparato.nombre, aparato.tipo, aparato.estado))

    def actualizar(self, aparato: Aparato):
        query = """
        UPDATE aparatos
        SET nombre=?, tipo=?, estado=?
        WHERE id=?
        """
        self.db.ejecutar(query, (aparato.nombre, aparato.tipo, aparato.estado, aparato.id))

    def eliminar(self, aparato_id):
        self.db.ejecutar("DELETE FROM aparatos WHERE id=?", (aparato_id,))

    def obtener_todos(self, filtro=""):
        query = "SELECT * FROM aparatos"
        params = ()
        if filtro:
            query += " WHERE nombre LIKE ? OR tipo LIKE ? OR estado LIKE ?"
            params = (f"%{filtro}%", f"%{filtro}%", f"%{filtro}%")

        filas = self.db.consultar(query, params)
        return [Aparato(**f) for f in filas]

    def obtener_por_id(self, aparato_id):
        fila = self.db.consultar_uno("SELECT * FROM aparatos WHERE id=?", (aparato_id,))
        return Aparato(**fila) if fila else None
