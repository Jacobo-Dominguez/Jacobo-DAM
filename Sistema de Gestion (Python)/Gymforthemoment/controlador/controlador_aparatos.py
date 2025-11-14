from modelo.aparato import Aparato
from modelo.base_datos import BaseDatos

class ControladorAparatos:
    def __init__(self):
        self.db = BaseDatos()
        self._crear_tabla()

    def _crear_tabla(self):
        query = """
        CREATE TABLE IF NOT EXISTS aparatos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            tipo TEXT,
            estado TEXT DEFAULT 'Disponible'
        )
        """
        self.db.ejecutar(query)

    def agregar(self, datos):
        aparato = Aparato(**datos)
        query = "INSERT INTO aparatos (nombre, tipo, estado) VALUES (?, ?, ?)"
        self.db.ejecutar(query, (aparato.nombre, aparato.tipo, aparato.estado))

    def editar(self, id_aparato, datos):
        aparato = self.obtener_por_id(id_aparato)
        if aparato:
            for k, v in datos.items():
                setattr(aparato, k, v)
            query = "UPDATE aparatos SET nombre=?, tipo=?, estado=? WHERE id=?"
            self.db.ejecutar(query, (aparato.nombre, aparato.tipo, aparato.estado, aparato.id))

    def eliminar(self, id_aparato):
        self.db.ejecutar("DELETE FROM aparatos WHERE id=?", (id_aparato,))

    def listar(self, filtro=""):
        query = "SELECT * FROM aparatos"
        params = ()
        if filtro:
            query += " WHERE nombre LIKE ? OR tipo LIKE ? OR estado LIKE ?"
            params = (f"%{filtro}%", f"%{filtro}%", f"%{filtro}%")
        filas = self.db.consultar(query, params)
        return [Aparato(**f) for f in filas]

    def obtener_por_id(self, id_aparato):
        fila = self.db.consultar_uno("SELECT * FROM aparatos WHERE id=?", (id_aparato,))
        return Aparato(**fila) if fila else None

    def cambiar_estado(self, id_aparato, nuevo_estado):
        aparato = self.obtener_por_id(id_aparato)
        if aparato:
            aparato.estado = nuevo_estado
            query = "UPDATE aparatos SET estado=? WHERE id=?"
            self.db.ejecutar(query, (aparato.estado, aparato.id))
